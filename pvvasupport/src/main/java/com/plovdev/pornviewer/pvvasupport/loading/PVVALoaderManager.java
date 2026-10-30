package com.plovdev.pornviewer.pvvasupport.loading;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.core.models.adapter.PluginsListItem;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.pvvasupport.PluginsImportUtils;
import com.plovdev.pornviewer.services.files.PVFileManager;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.plovdev.pvva.models.PVVAHost;
import org.plovdev.pvva.utils.MockUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;

public final class PVVALoaderManager {
    private static final String MAX_SIZE_KEY = "pornviewer.memory.cache-control.max-adapters-loaded.size";
    private static final Cache<String, PVVAHost> CACHED_ADAPTERS = Caffeine.newBuilder()
            .maximumSize(Integer.getInteger(MAX_SIZE_KEY, 10))
            .build();
    private static final PluginLoader PLUGIN_LOADER = new PluginLoaderImpl();
    private static final PVVAHost EMPTY_HOST = MockUtils.mockPVVAHost();
    private static final Logger log = LoggerFactory.getLogger(PVVALoaderManager.class);

    static {
        CACHED_ADAPTERS.put("default", EMPTY_HOST);
    }

    private PVVALoaderManager() {
        throw new UnsupportedOperationException();
    }

    public static @NonNull CompletableFuture<PVVAHost> downloadPlugin(PluginsListItem pluginToLoad) {
        return PluginDownloaderHelper.startDownloading(pluginToLoad, PLUGIN_LOADER, CACHED_ADAPTERS);
    }

    public static @Nullable PVVAHost loadPvvaById(String pluginId) {
        return CACHED_ADAPTERS.get(pluginId, PVVALoaderManager::directlyLoadHost);
    }

    private static PVVAHost directlyLoadHost(String pluginId) {
        PluginInfo info = PluginsProvider.getAdapterById(pluginId);
        return PLUGIN_LOADER.loadFromDisk(pluginId, PVFileManager.getPvPluginPath(info.systemPluginId()));
    }

    public static void directlyLoadHost(PVVAHost host) {
        CACHED_ADAPTERS.put(host.getSystemPluginId(), host);
    }

    public static @NonNull PVVAHost importPlugin(Path pluginPath) {
        PVVAHost host = PluginsImportUtils.importPlugin(pluginPath);
        directlyLoadHost(host);
        return host;
    }

    public static PVVAHost forceAdapter(String pluginId) {
        CACHED_ADAPTERS.invalidate(pluginId);
        PVVAHost freshHost = directlyLoadHost(pluginId);
        CACHED_ADAPTERS.put(pluginId, freshHost);
        return freshHost;
    }

    public static boolean isCached(String pluginId) {
        return CACHED_ADAPTERS.getIfPresent(pluginId) != null;
    }

    public static void clearCache() {
        CACHED_ADAPTERS.invalidateAll();
    }

    public static void removeFromCache(String pluginId) {
        CACHED_ADAPTERS.invalidate(pluginId);
    }

    public static long getCacheSize() {
        return CACHED_ADAPTERS.estimatedSize();
    }
}