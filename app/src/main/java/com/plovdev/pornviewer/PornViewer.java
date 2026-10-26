package com.plovdev.pornviewer;

import com.plovdev.pornviewer.core.utils.Globals;
import com.plovdev.pornviewer.services.files.PVFileManager;
import com.plovdev.pornviewer.settings.SettingsManager;
import com.plovdev.pornviewer.ui.PornViewerApplication;
import com.plovdev.pornviewer.ui.tools.SplashScreenHandler;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Files;

public class PornViewer {
    private static final Logger log = LoggerFactory.getLogger(PornViewer.class);

    static void main(String[] args) {
        SplashScreenHandler.handleSplashScreen();

        log.info("Starting application");
        SettingsManager.getInstance().init();
        setupCleanup();
        Application.launch(PornViewerApplication.class, args);
    }

    private static void setupCleanup() {
        Globals.addShutdownHook(() -> {
            try {
                Files.deleteIfExists(PVFileManager.getPvDbShmPath());
                Files.deleteIfExists(PVFileManager.getPvDbWalPath());
            } catch (Exception e) {
                log.error("Error cleanup db trash: ", e);
            }
        });
    }
}