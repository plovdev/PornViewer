package com.plovdev.pornviewer.settings;

import com.plovdev.pornviewer.core.models.app.AppSettings;
import com.plovdev.pornviewer.services.exceptions.SettingsLoadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

public class SettingsManager {
    private static final Logger log = LoggerFactory.getLogger(SettingsManager.class);
    private final AtomicBoolean isInited = new AtomicBoolean(false);
    private final AtomicReference<AppSettings> settingsRef = new AtomicReference<>();

    private static class SettingsManagerHolder {
        private static final SettingsManager SETTINGS_MANAGER = new SettingsManager();
    }

    public static SettingsManager getInstance() {
        return SettingsManagerHolder.SETTINGS_MANAGER;
    }

    private SettingsManager() {
    }

    public synchronized void init() {
        if (!isInited.getAndSet(true)) {
            AppSettings settings;
            try {
                settings = SettingsLoader.loadSettings();
            } catch (SettingsLoadingException e) {
                log.error("Error load setting, use default.");
                settings = new AppSettings();
            }
            settingsRef.set(settings);
        }
    }

    public synchronized AppSettings getAppSettings() {
        if (isInited.get()) {
            return settingsRef.get();
        } else {
            throw new IllegalStateException("Settings not initiliazed yet");
        }
    }

    public synchronized void setAppSettings(AppSettings newSettings) {
        if (isInited.get()) {
            settingsRef.set(newSettings);
        } else {
            throw new IllegalStateException("Settings not initiliazed yet");
        }
    }
}