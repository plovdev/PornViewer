package com.plovdev.pornviewer.ui.controllers;

import com.plovdev.pornviewer.core.events.GlobalEventManager;
import com.plovdev.pornviewer.ui.bridge.UICoreCardsBridge;
import com.plovdev.pornviewer.ui.components.PornTabPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLayoutController {
    private static final Logger log = LoggerFactory.getLogger(MainLayoutController.class);
    private UICoreCardsBridge bridge;

    public MainLayoutController() {
    }

    @FXML
    private PornTabPane pornTabPane;

    @FXML
    public void initialize() {
        bridge = new UICoreCardsBridge(pornTabPane);
        GlobalEventManager.registerListener(bridge);
        pornTabPane.init();
        log.info("Main app layout has been loaded successfully");
    }

    @FXML
    private void handleOpenSettings() {
        log.info("Клик по кнопке Настройки в MenuBar");
    }

    @FXML
    private void handleAbout() {
        log.info("Клик по кнопке О программе в MenuBar");
    }

    @FXML
    private void handleExit() {
        log.info("Exiting from app...");
        bridge.close();
        Platform.exit();
    }
}