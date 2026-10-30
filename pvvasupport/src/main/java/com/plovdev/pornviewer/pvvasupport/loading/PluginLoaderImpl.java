package com.plovdev.pornviewer.pvvasupport.loading;

import com.plovdev.pornviewer.core.models.adapter.PluginsListItem;
import com.plovdev.pornviewer.pvvasupport.PluginsImportUtils;
import com.plovdev.pornviewer.pvvasupport.exceptions.PluginLoadingException;
import com.plovdev.pornviewer.pvvasupport.verifiers.HashPluginVerifier;
import com.plovdev.pornviewer.pvvasupport.verifiers.SignaturePluginVerifier;
import org.jspecify.annotations.NonNull;
import org.plovdev.pvva.models.PVVAHost;
import org.plovdev.pvva.models.PluginJson;
import org.plovdev.pvva.read.ByteArrayPVVAReader;
import org.plovdev.pvva.read.PVVAReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PluginLoaderImpl implements PluginLoader {
    /**
     * {@inheritDoc}
     */
    @Override
    public PVVAHost loadFromServer(@NonNull PluginsListItem pluginItem) {
        try {
            String pluginId = pluginItem.systemPluginId();
            byte[] pluginData = PluginDownloader.downloadPlugin(pluginId, pluginItem.downloadUrl());

            try (PVVAReader reader = new ByteArrayPVVAReader(pluginData)) {
                PVVAHost host = reader.readVideoAdapter();
                PluginJson pluginJson = host.pluginJson();
                PluginsImportUtils.checkLoadedHost(new SignaturePluginVerifier(pluginJson.developerId()), host, pluginData);

                PluginsImportUtils.saveDownloadedPlugin(pluginId, host, pluginData);
                return host;
            }
        } catch (IOException e) {
            throw new PluginLoadingException("Error to load pvva plugin from server", e);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public PVVAHost loadFromDisk(String pluginId, Path path) {
        try {
            byte[] pluginData = Files.readAllBytes(path);
            try (PVVAReader reader = new ByteArrayPVVAReader(pluginData)) {
                PVVAHost host = reader.readVideoAdapter();
                return PluginsImportUtils.checkLoadedHost(new HashPluginVerifier(host.getSystemPluginId()), host, pluginData);
            }
        } catch (IOException e) {
            throw new PluginLoadingException("Error to load pvva plugin", e);
        }
    }
}