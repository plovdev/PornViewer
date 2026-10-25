package com.plovdev.pornviewer.ui.controllers;

import com.plovdev.pornviewer.ui.components.PornTabPane;
import javafx.application.Platform;
import javafx.fxml.FXML;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainLayoutController {
    private static final Logger log = LoggerFactory.getLogger(MainLayoutController.class);
    @FXML
    private PornTabPane pornTabPane;

    @FXML
    public void initialize() {
        log.info("Главный каркас приложения успешно загружен.");
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
        log.info("Выход из приложения...");
        Platform.exit();
    }
}