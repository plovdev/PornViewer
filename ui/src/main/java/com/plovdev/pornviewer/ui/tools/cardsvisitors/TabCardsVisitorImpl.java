package com.plovdev.pornviewer.ui.tools.cardsvisitors;

import com.plovdev.pornviewer.ui.components.CardsFlowPane;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import org.jspecify.annotations.NonNull;

public class TabCardsVisitorImpl<T extends Node> implements TabCardsVisitor<T> {
    private final ObservableList<Node> children;

    public TabCardsVisitorImpl(@NonNull CardsFlowPane flowPane) {
        this.children = flowPane.getChildren();
    }

    @Override
    public void addCard(T card) {
        Platform.runLater(() -> children.add(card));
    }

    @Override
    public void removeCard(T card) {
        Platform.runLater(() -> children.remove(card));
    }

    @Override
    public void clear() {
        Platform.runLater(children::clear);
    }
}