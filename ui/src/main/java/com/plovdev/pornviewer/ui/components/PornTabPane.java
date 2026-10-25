package com.plovdev.pornviewer.ui.components;

import com.plovdev.pornviewer.ui.exceptions.FXMLLoadingException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class PornTabPane extends TabPane {
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plovdev/pornviewer/fxml/components/PornTabPane.fxml"));
        loader.setRoot(this);
        loader.setController(this);

        try {
            loader.load();
        } catch (IOException e) {
            log.error("Error load PornTabPane component: ", e);
            throw new FXMLLoadingException(e.getMessage());
        }

        tabMinWidthProperty().bind(widthProperty().divide(4.5));
        tabMaxWidthProperty().bind(widthProperty().divide(4.5));

        //TODO: initiliaze content
    }
}