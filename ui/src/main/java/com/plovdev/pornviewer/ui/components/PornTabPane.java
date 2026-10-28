package com.plovdev.pornviewer.ui.components;

import com.plovdev.pornviewer.core.events.GlobalEventManager;
import com.plovdev.pornviewer.ui.controllers.ComponentController;
import com.plovdev.pornviewer.ui.events.ParseCommand;
import com.plovdev.pornviewer.ui.tools.ComponentLoader;
import com.plovdev.pornviewer.ui.tools.cardsvisitors.TabCardsVisitor;
import com.plovdev.pornviewer.ui.tools.cardsvisitors.TabCardsVisitorImpl;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PornTabPane extends TabPane implements ComponentController {
    private static final Logger log = LoggerFactory.getLogger(PornTabPane.class);
    @FXML
    private CardsFlowPane homeContainer;
    @FXML
    private CardsScrollPane homeScrollPane;
    @FXML
    private PornTab homeTab;

    @FXML
    private CardsFlowPane modelsContainer;
    @FXML
    private CardsScrollPane modelsScrollPane;
    @FXML
    private PornTab modelsTab;

    @FXML
    private CardsFlowPane favoritesContainer;
    @FXML
    private PornTab favoritesTab;

    @FXML
    private CardsFlowPane downloadsContainer;
    @FXML
    private PornTab downloadsTab;

    private boolean isModelsInited;
    private boolean isFavoritesInited;
    private boolean isDownloadsInited;

    public PornTabPane() {
        ComponentLoader.loadComponent("PornTabPane", this, this);
        tabMinWidthProperty().bind(widthProperty().divide(4.5));
        tabMaxWidthProperty().bind(widthProperty().divide(4.5));
    }

    @FXML
    public void initialize() {
        homeScrollPane.setOnPulledOf(() -> GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.HOME)));
        modelsScrollPane.setOnPulledOf(() -> GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.MODELS)));

        homeScrollPane.initialize();
        modelsScrollPane.initialize();
    }

    public void init() {
        GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.HOME, 0));

        modelsTab.setOnSelectionChanged(event -> {
            if (modelsTab.isSelected()) {
                if (!isModelsInited) {
                    GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.MODELS, 0));
                    isModelsInited = true;
                }
            }
        });
        favoritesTab.setOnSelectionChanged(event -> {
            if (favoritesTab.isSelected()) {
                if (!isFavoritesInited) {
                    GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.FAVORITES));
                    isFavoritesInited = true;
                }
            }
        });
        downloadsTab.setOnSelectionChanged(event -> {
            if (downloadsTab.isSelected()) {
                if (!isDownloadsInited) {
                    GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.DOWNLOADS));
                    isDownloadsInited = true;
                }
            }
        });
    }

    public TabCardsVisitor<Node> homeCardsVisitor() {
        return new TabCardsVisitorImpl<>(homeContainer);
    }

    public TabCardsVisitor<Node> modelsCardsVisitor() {
        return new TabCardsVisitorImpl<>(modelsContainer);
    }

    public TabCardsVisitor<Node> favoritesCardsVisitor() {
        return new TabCardsVisitorImpl<>(favoritesContainer);
    }

    public TabCardsVisitor<Node> downloadsCardsVisitor() {
        return new TabCardsVisitorImpl<>(downloadsContainer);
    }
}