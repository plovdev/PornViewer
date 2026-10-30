package com.plovdev.pornviewer.pvvasupport;

import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.core.models.app.VerifiedHash;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.database.tables.VerifiedHashes;
import com.plovdev.pornviewer.pvvasupport.exceptions.PluginImportException;
import com.plovdev.pornviewer.pvvasupport.exceptions.PluginNotVerifiedException;
import com.plovdev.pornviewer.pvvasupport.exceptions.PluginSavingException;
import com.plovdev.pornviewer.pvvasupport.loading.validator.PluginValidator;
import com.plovdev.pornviewer.pvvasupport.verifiers.PluginVerifier;
import com.plovdev.pornviewer.pvvasupport.verifiers.SignaturePluginVerifier;
import com.plovdev.pornviewer.security.DigestUtils;
import com.plovdev.pornviewer.services.files.PVFileManager;
import org.jetbrains.annotations.Contract;
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

    public static boolean isDefaultPlugin(String pluginId) {
        return "default".equals(pluginId);
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

    public static @NonNull PVVAHost importPlugin(Path pluginPath) {
        Objects.requireNonNull(pluginPath);
        if (Files.notExists(pluginPath)) {
            throw new NoSuchElementException("No plugin by specified path: " + pluginPath);
        }

        try {
            byte[] pluginData = Files.readAllBytes(pluginPath);
            try (PVVAReader reader = new ByteArrayPVVAReader(pluginData)) {
                PVVAHost host = reader.readVideoAdapter();
                checkLoadedHost(new SignaturePluginVerifier(host.pluginJson().developerId()), host, pluginData);
                saveDownloadedPlugin(host.getSystemPluginId(), host, pluginData);
                return host;
            }
        } catch (IOException e) {
            log.error("Error import plugin: ", e);
            throw new PluginImportException("Error import plugin to system", e);
        }
    }

    @Contract("_, _, _ -> param2")
    public static PVVAHost checkLoadedHost(@NonNull PluginVerifier verifier, PVVAHost host, byte[] pluginData) {
        if (verifier.checkPluginIfNeed(pluginData)) {
            PluginValidator.validatePlugin(host);
            return host;
        } else {
            throw new PluginNotVerifiedException("Plugin " + host.getSystemPluginId() + " is not verified.");
        }
    }
}