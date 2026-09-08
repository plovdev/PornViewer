package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.database.tables.VerifiedHashes;
import com.plovdev.pornviewer.pvvasupport.loading.PVVALoaderManager;
import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.sun.net.httpserver.HttpExchange;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

public class RemovePluginRequest implements PluginRequestHandler {
    @Override
    public synchronized void handlePluginRequest(HttpExchange exchange, @NonNull Map<String, Object> params) throws IOException {
        String pluginId = (String) params.get("plugin-id");
        boolean containsPlugin = PluginsProvider.containsAdapter(pluginId);
        if (containsPlugin) {
            Path pluginPath = VerifiedHashes.getVerifiedHash(pluginId).filePath();
            PVVALoaderManager.removeFromCache(pluginId);

            VerifiedHashes.removeVerifiedHash(pluginId);
            PluginsProvider.removeAdapter(pluginId);

            Files.deleteIfExists(pluginPath);
            ServerSendsUtils.send200(exchange, "Plugin deleted successfully");
        } else {
            ServerSendsUtils.send404(exchange, "Plugin not found");
        }
    }
}