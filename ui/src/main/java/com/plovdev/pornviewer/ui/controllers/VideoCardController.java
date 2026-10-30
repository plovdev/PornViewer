package com.plovdev.pornviewer.ui.controllers;

import com.plovdev.pornviewer.core.models.porn.ShortVideoInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.SVGPath;

public class VideoCardController implements ComponentController {
    private ShortVideoInfo videoInfo;

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView previewImage;
    @FXML
    private Hyperlink titleLink;
    @FXML
    private Label ratingLabel;
    @FXML
    private HBox infoOverlay;
    @FXML
    private SVGPath favPath;
    @FXML
    private ImageView downloadIcon;
    @FXML
    private Label viewsLabel;
    @FXML
    private Label durationLabel;

    public void setupUI() {
        previewImage.setImage(new Image(videoInfo.picture().toString()));
        titleLink.setText(videoInfo.title());

    }

    public void setData(ShortVideoInfo info) {
        this.videoInfo = info;
        setupUI();
    }

    @FXML
    private void onTitleClick() {
    }

    @FXML
    private void onFavoriteClick() {
    }

    private void updateFavVisual() {
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