package com.plovdev.pornviewer.server.handlers.plugins;

import com.plovdev.pornviewer.server.utils.ServerSendsUtils;
import com.plovdev.pornviewer.services.files.PVFileManager;
import com.sun.net.httpserver.HttpExchange;
import org.jspecify.annotations.NonNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class PluginsRequestsUtils {
    private PluginsRequestsUtils() {
    }

    public static void preparePlugin(@NonNull String fileName, File pluginFile, HttpExchange exchange) throws IOException {
        if (!fileName.endsWith(".pvva")) {
            ServerSendsUtils.send400(exchange, "File is not pvva plugin");
            throw new IllegalArgumentException("File is not pvva plugin");
        }

        if (pluginFile.isDirectory()) {
            ServerSendsUtils.send400(exchange, "File is directory");
            throw new IllegalArgumentException("File is directory");
        }

        File parentDirectory = pluginFile.getParentFile();
        if (parentDirectory != null) {
            Path sourcePath = pluginFile.toPath();
            Path targetPath = PVFileManager.getPvPluginsPath().resolve(pluginFile.getName());

            if (!Files.isSameFile(parentDirectory.toPath(), PVFileManager.getPvPluginsPath())) {
                Files.copy(sourcePath, targetPath);
            }
        } else {
            ServerSendsUtils.send500(exchange);
        }
    }
}