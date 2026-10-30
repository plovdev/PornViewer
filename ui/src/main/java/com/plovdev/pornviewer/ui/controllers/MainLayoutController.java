package com.plovdev.pornviewer.ui.controllers;

import com.plovdev.pornviewer.core.events.GlobalEventManager;
import com.plovdev.pornviewer.core.utils.Globals;
import com.plovdev.pornviewer.ui.bridge.UICoreCardsBridge;
import com.plovdev.pornviewer.ui.components.PornTabPane;
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
        Globals.addShutdownHook(() -> bridge.close());
    }

    public void handlePluginSettings() {

    }
}