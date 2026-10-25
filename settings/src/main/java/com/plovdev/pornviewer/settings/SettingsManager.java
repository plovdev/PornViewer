package com.plovdev.pornviewer.settings;

import com.plovdev.pornviewer.core.models.app.AppSettings;
import com.plovdev.pornviewer.services.exceptions.SettingsLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsManager {
    private static final Logger log = LoggerFactory.getLogger(SettingsManager.class);
    private volatile boolean isInited = false;
    private volatile AppSettings settings = null;

    private static class SettingsManagerHolder {
        private static final SettingsManager SETTINGS_MANAGER = new SettingsManager();
    }

    public static SettingsManager getInstance() {
        return SettingsManagerHolder.SETTINGS_MANAGER;
    }

    private SettingsManager() {
    }

    public synchronized void init() {
        if (!isInited) {
            try {
                settings = SettingsLoader.loadSettings();
            } catch (SettingsLoadingException e) {
                log.error("Error load setting, use default.");
                settings = new AppSettings();
            }
            isInited = true;
        }
    }

    private void checkInit() {
        if (!isInited) {
            throw new IllegalStateException("Settings not initiliazed yet");
        }
    }

    public synchronized AppSettings getAppSettings() {
        checkInit();
        return settings;
    }

    public synchronized void setAppSettings(AppSettings newSettings) {
        checkInit();
        settings = newSettings;
    }

    public synchronized void saveAppSettings() {
        checkInit();
        SettingsLoader.saveSettings(settings);
    }
}