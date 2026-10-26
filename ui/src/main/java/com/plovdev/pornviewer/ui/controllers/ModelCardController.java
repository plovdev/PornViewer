package com.plovdev.pornviewer.ui.controllers;

import com.plovdev.pornviewer.core.models.porn.ModelInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

public class ModelCardController implements ComponentController {
    private ModelInfo modelInfo;

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView previewImage;
    @FXML
    private Hyperlink nameLink;
    @FXML
    private HBox infoOverlay;

    @FXML
    public void initialize() {
        //TODO: initialize
    }

    public void setData(ModelInfo info) {
        this.modelInfo = info;

        //TODO: fill fields
    }

    @FXML
    private void onNameClick() {
    }

    @FXML
    private void onMouseEntered() {
        infoOverlay.setVisible(true);
    }

    @FXML
    private void onMouseExited() {
        infoOverlay.setVisible(false);
    }
}