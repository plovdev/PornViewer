package com.plovdev.pornviewer;

import com.plovdev.pornviewer.core.events.GlobalEventManager;
import com.plovdev.pornviewer.init.*;
import com.plovdev.pornviewer.ui.PornViewerApplication;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PornViewer {
    private static final Logger log = LoggerFactory.getLogger(PornViewer.class);

    static void main(String[] args) {
        log.info("Starting application");
        setupAppInitializator();

        Application.launch(PornViewerApplication.class, args);
    }

    private static void setupAppInitializator() {
        SplashScreenHandler handler = new SplashScreenHandler();
        handler.onNextInitStep(InitType.START, 0);
        GlobalEventManager.registerListener(handler);

        InitializatorManager manager = new InitializatorManager();
        manager.addAppInitializator(new FilesInitializator());
        manager.addAppInitializator(new SecurityInitializator());
        manager.addAppInitializator(new SettingsInitializator());
        manager.addAppInitializator(new DatabaseInitializator());
        manager.setListener(handler::onNextInitStep);
        manager.initializeApplication();
    }
}