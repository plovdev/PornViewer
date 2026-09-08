package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.plovdev.pornviewer.services.json.JSONSerializer;
import com.sun.net.httpserver.HttpExchange;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.NoSuchElementException;

public class GetPluginRequest implements PluginRequestHandler {
    private static final Logger log = LoggerFactory.getLogger(GetPluginRequest.class);

    @Override
    public void handlePluginRequest(HttpExchange exchange, @NonNull Map<String, Object> params) throws IOException {
        String pluginId = (String) params.get("plugin-id");
        try {
            PluginInfo info = PluginsProvider.getAdapterById(pluginId);
            ServerSendsUtils.sendRawJson(exchange, JSONSerializer.serialize(info));
        } catch (NoSuchElementException e) {
            ServerSendsUtils.send404(exchange, "Plugin not found");
        }
    }
}