package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.core.models.app.VerifiedHash;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.database.tables.VerifiedHashes;
import com.plovdev.pornviewer.pvvasupport.loading.PVVALoaderManager;
import com.plovdev.pornviewer.security.DigestUtils;
import com.plovdev.pornviewer.server.utils.RequestUtils;
import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.plovdev.pornviewer.services.files.PVFileManager;
import com.sun.net.httpserver.HttpExchange;
import org.jspecify.annotations.NonNull;
import org.plovdev.pvva.models.PVVAHeader;
import org.plovdev.pvva.models.PVVAHost;
import org.plovdev.pvva.models.PluginJson;
import org.plovdev.pvva.read.DefaultPVVAReader;
import org.plovdev.pvva.read.PVVAReader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

public class LoadPluginRequest implements PluginRequestHandler {
    @Override
    public void handlePluginRequest(HttpExchange exchange, @NonNull Map<String, Object> params) throws IOException {
        String fileName = (String) params.get("file");
        File pluginFile = RequestUtils.checkFile(exchange, fileName);
        String absoluteFileName = pluginFile.getAbsolutePath();
        PluginsRequestsUtils.preparePlugin(absoluteFileName, pluginFile, exchange);

        String pluginName = pluginFile.getName();
        try (PVVAReader reader = new DefaultPVVAReader(Path.of(absoluteFileName))) {
            PVVAHost host = reader.readVideoAdapter();
            String systemPluginId = host.getSystemPluginId();
            synchronized (this) {
                boolean contains = PluginsProvider.containsAdapter(systemPluginId);
                if (!contains) {
                    VerifiedHashes.addVerifiedHash(new VerifiedHash(systemPluginId, VerifiedHash.HashedFileType.PLUGIN, PVFileManager.getPvPluginPath(systemPluginId), DigestUtils.sha256(reader.getReadData().array())));
                    PVVAHeader header = host.header();
                    PluginJson json = host.pluginJson();
                    PluginsProvider.addAdapter(new PluginInfo(header.getPluginId(), host.getSystemPluginId(), json.developerId(), json.title(), json.version(), json.description(), json.author(), json.authorPage().orElse(null), json.licenseUrl().orElse(null), json.homepage().orElse(null), null, header.getMinAppVersion(), header.getMaxAppVersion(), header.getBuildId()));
                    PVVALoaderManager.directlyLoadHost(host);
                    ServerSendsUtils.send200(exchange, "Plugin was loaded successfully");
                } else {
                    ServerSendsUtils.send200(exchange, "Plugin already has been loaded");
                }
            }
        }
    }
}