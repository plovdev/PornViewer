package com.plovdev.pornviewer.ui.tools.cardsvisitors;

import javafx.scene.Node;

public interface TabCardsVisitor<T extends Node> {
    void addCard(T card);

    void removeCard(T card);

    void clear();
}