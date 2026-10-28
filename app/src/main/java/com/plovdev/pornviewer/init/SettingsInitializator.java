package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.settings.SettingsManager;

public class SettingsInitializator implements AppInitializator {
    @Override
    public InitType getInitType() {
        return InitType.INIT_SETTINGS;
    }

    @Override
    public void initialize() {
        SettingsManager.getInstance().init();
    }
}