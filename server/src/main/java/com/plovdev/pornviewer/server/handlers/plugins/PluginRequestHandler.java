package com.plovdev.pornviewer.server.handlers.plugins;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public interface PluginRequestHandler {
    void handlePluginRequest(HttpExchange exchange, Map<String, Object> params) throws IOException;
}