package com.plovdev.pornviewer.ui.components.cards;

import com.plovdev.pornviewer.core.models.porn.ShortVideoInfo;
import com.plovdev.pornviewer.ui.controllers.VideoCardController;
import com.plovdev.pornviewer.ui.tools.ComponentLoader;
import javafx.scene.layout.StackPane;

public class VideoCard extends StackPane {
    public VideoCard(ShortVideoInfo videoInfo) {
        VideoCardController controller = new VideoCardController();
        ComponentLoader.loadComponent("VideoCard", this, controller);
        controller.setData(videoInfo);
    }
}