package com.plovdev.pornviewer.ui.components.cards;

import com.plovdev.pornviewer.core.models.porn.ModelInfo;
import com.plovdev.pornviewer.ui.controllers.ModelCardController;
import com.plovdev.pornviewer.ui.tools.ComponentLoader;
import javafx.scene.layout.StackPane;

public class ModelCard extends StackPane {
    public ModelCard(ModelInfo modelInfo) {
        ModelCardController controller = new ModelCardController();
        ComponentLoader.loadComponent("ModelCard", this, controller);
        controller.setData(modelInfo);
    }
}