package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.plovdev.pornviewer.services.json.JSONSerializer;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ListPluginRequest implements PluginRequestHandler {
    @Override
    public void handlePluginRequest(HttpExchange exchange, Map<String, Object> params) throws IOException {
        List<PluginInfo> infos = PluginsProvider.getAllAdapters();
        ServerSendsUtils.sendRawJson(exchange, JSONSerializer.serialize(infos));
    }
}