package com.plovdev.pornviewer.pvvasupport.loading.validator;

import com.plovdev.pornviewer.core.models.app.AppInfo;
import com.plovdev.pornviewer.pvvasupport.exceptions.UnsupportedPluginException;
import com.plovdev.pornviewer.services.files.ConfigReader;
import org.jspecify.annotations.NonNull;
import org.plovdev.pvva.models.PVVAHeader;
import org.plovdev.pvva.models.PVVAHost;
import org.plovdev.pvva.utils.VersionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class PluginValidator {
    private static final Logger log = LoggerFactory.getLogger(PluginValidator.class);

    private PluginValidator() {
    }

    public static void validatePlugin(@NonNull PVVAHost host) {
        AppInfo info = ConfigReader.loadAppInfo();
        int appVersion = VersionUtils.versionToInt(info.version());

        PVVAHeader header = host.header();
        int minAppVersion = header.getMinAppVersion();
        int maxAppVersion = header.getMaxAppVersion();

        if (!VersionUtils.isCompatibleWithAppVersion(minAppVersion, maxAppVersion, appVersion)) {
            log.warn("App version: {}, min app version: {}, max app version: {}", appVersion, minAppVersion, maxAppVersion);
            throw new UnsupportedPluginException("Plugin " + host.header().getPluginId() + " not support this app version " + appVersion);
        }
    }
}