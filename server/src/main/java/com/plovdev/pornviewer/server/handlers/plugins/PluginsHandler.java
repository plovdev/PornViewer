package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.core.http.HttpMethod;
import com.plovdev.pornviewer.server.handlers.AbstractServerHandler;
import com.plovdev.pornviewer.server.utils.RequestUtils;
import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.sun.net.httpserver.HttpExchange;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Deprecated
public class PluginsHandler extends AbstractServerHandler {
    private final Map<String, PluginRequestHandler> pluginRequestHandlers;

    public PluginsHandler() {
        this.pluginRequestHandlers = new HashMap<>();
        pluginRequestHandlers.put("load", new LoadPluginRequest());
        pluginRequestHandlers.put("remove", new RemovePluginRequest());
        pluginRequestHandlers.put("get", new GetPluginRequest());
        pluginRequestHandlers.put("list", new ListPluginRequest());
    }

    @Override
    protected void onRequest(HttpExchange exchange, URI uri, HttpMethod method, Map<String, Object> params) throws Exception {
        String pluginPart = RequestUtils.getRequestedPath("plugins", uri);
        PluginRequestHandler handler = pluginRequestHandlers.get(pluginPart);
        if (handler != null) {
            handler.handlePluginRequest(exchange, params);
        } else {
            ServerSendsUtils.send404(exchange, "Plugin tool not found");
        }
    }

    @Override
    protected List<HttpMethod> getSupportedMethods() {
        return List.of(HttpMethod.GET, HttpMethod.POST);
    }
}