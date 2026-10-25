package com.plovdev.pornviewer;

import com.plovdev.pornviewer.settings.SettingsManager;
import com.plovdev.pornviewer.ui.PornViewerApplication;
import com.plovdev.pornviewer.ui.tools.SplashScreenHandler;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PornViewer {
    private static final Logger log = LoggerFactory.getLogger(PornViewer.class);

    static void main(String[] args) {
        SplashScreenHandler.handleSplashScreen();

        log.info("Starting application");
        SettingsManager.getInstance().init();
        Application.launch(PornViewerApplication.class, args);
    }
}