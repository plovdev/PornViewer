package com.plovdev.pornviewer.pvvasupport;

import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.core.models.app.VerifiedHash;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.database.tables.VerifiedHashes;
import com.plovdev.pornviewer.pvvasupport.exceptions.PluginSavingException;
import com.plovdev.pornviewer.security.DigestUtils;
import com.plovdev.pornviewer.services.files.PVFileManager;
import org.jspecify.annotations.NonNull;
import org.plovdev.pvva.models.PVVAHeader;
import org.plovdev.pvva.models.PVVAHost;
import org.plovdev.pvva.models.PluginJson;
import org.plovdev.pvva.read.ByteArrayPVVAReader;
import org.plovdev.pvva.read.PVVAReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.NoSuchElementException;
import java.util.Objects;

public final class PluginsImportUtils {
    private static final Logger log = LoggerFactory.getLogger(PluginsImportUtils.class);

    private PluginsImportUtils() {
    }

    public static void saveDownloadedPlugin(String pluginId, PVVAHost host, byte @NonNull [] pluginData) {
        if (pluginData.length < 64) {
            throw new IllegalArgumentException("Plugin data too short: " + pluginData.length);
        }

        try {
            Path pluginPath = PVFileManager.getPvPluginPath(pluginId);
            Files.write(pluginPath, pluginData);
            VerifiedHashes.addVerifiedHash(new VerifiedHash(pluginId, VerifiedHash.HashedFileType.PLUGIN, pluginPath, DigestUtils.sha256(pluginData)));

            PVVAHeader header = host.header();
            PluginJson json = host.pluginJson();
            PluginsProvider.addAdapter(new PluginInfo(header.getPluginId(), host.getSystemPluginId(), json.developerId(), json.title(), json.version(), json.description(), json.author(), json.authorPage().orElse(null), json.licenseUrl().orElse(null), json.homepage().orElse(null), null, header.getMinAppVersion(), header.getMaxAppVersion(), header.getBuildId()));
        } catch (IOException e) {
            throw new PluginSavingException(e);
        }
    }

    public static void importPlugin(Path pluginPath) {
        Objects.requireNonNull(pluginPath);
        if (Files.notExists(pluginPath)) {
            throw new NoSuchElementException("No plugin by specefied path: " + pluginPath);
        }

        try {
            byte[] pluginData = Files.readAllBytes(pluginPath);
            try (PVVAReader reader = new ByteArrayPVVAReader(pluginData)) {
                PVVAHost host = reader.readVideoAdapter();
                saveDownloadedPlugin(host.getSystemPluginId(), host, pluginData);
            }
        } catch (IOException e) {
            log.error("Error import plugin: ", e);
        }
    }
}