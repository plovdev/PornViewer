package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.services.files.ConfigReader;
import com.plovdev.pornviewer.settings.SettingsManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SettingsInitializator implements AppInitializator {
    private static final Logger log = LoggerFactory.getLogger("CLEAR");

    @Override
    public InitType getInitType() {
        return InitType.INIT_SETTINGS;
    }

    @Override
    public void initialize() {
        log.info(ConfigReader.loadAppInfo().toString());
        SettingsManager.getInstance().init();
    }
}