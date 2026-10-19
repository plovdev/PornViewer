package com.plovdev.pornviewer;

import com.plovdev.pornviewer.tools.SplashScreenHandler;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PornViewer {
    private static final Logger log = LoggerFactory.getLogger(PornViewer.class);

    static void main(String[] args) {
        //SplashScreenHandler.handleSplashScreen();

        log.info("Starting application");
        Application.launch(PornViewerApplication.class, args);
    }
}