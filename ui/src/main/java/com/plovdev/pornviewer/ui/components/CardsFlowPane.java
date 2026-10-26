package com.plovdev.pornviewer.ui.components;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;

public class CardsFlowPane extends FlowPane {
    public CardsFlowPane() {
        super(50, 50);
        setAlignment(Pos.TOP_LEFT);
        getStyleClass().add("main-pane-content");
        setPadding(new Insets(10, 10, 50, 10));
    }
}