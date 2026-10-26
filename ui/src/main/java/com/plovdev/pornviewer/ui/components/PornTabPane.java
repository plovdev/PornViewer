package com.plovdev.pornviewer.ui.components;

import com.plovdev.pornviewer.ui.controllers.ComponentController;
import com.plovdev.pornviewer.ui.tools.ComponentLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PornTabPane extends TabPane implements ComponentController {
    private static final Logger log = LoggerFactory.getLogger(PornTabPane.class);
    @FXML
    private PornTab homeTab;

    @FXML
    private PornTab modelsTab;

    @FXML
    private PornTab favoritesTab;

    @FXML
    private PornTab downloadsTab;

    public PornTabPane() {
        ComponentLoader.loadComponent("PornTabPane", this, this);
        tabMinWidthProperty().bind(widthProperty().divide(4.5));
        tabMaxWidthProperty().bind(widthProperty().divide(4.5));

        //TODO: initiliaze content
    }
}