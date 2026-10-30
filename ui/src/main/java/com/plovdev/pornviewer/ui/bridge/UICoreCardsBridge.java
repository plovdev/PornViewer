package com.plovdev.pornviewer.ui.bridge;

import com.plovdev.pornviewer.http.PornClient;
import com.plovdev.pornviewer.http.PornClientImpl;
import com.plovdev.pornviewer.ui.components.PornTabPane;
import com.plovdev.pornviewer.ui.components.cards.ModelCard;
import com.plovdev.pornviewer.ui.components.cards.VideoCard;
import com.plovdev.pornviewer.ui.events.ParseCommand;
import com.plovdev.pornviewer.ui.events.PluginAdapterChanged;
import com.plovdev.pornviewer.ui.tools.cardsvisitors.TabCardsVisitor;
import javafx.scene.Node;
import org.plovdev.eda.reflect.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class UICoreCardsBridge implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(UICoreCardsBridge.class);
    private final ExecutorService loadingExecutor = Executors.newFixedThreadPool(4);
    private final PornTabPane tabPane;

    private final AtomicReference<PornClient> pornClientRef = new AtomicReference<>(new PornClientImpl());

    public UICoreCardsBridge(PornTabPane tabPane) {
        this.tabPane = tabPane;
    }

    @Subscribe(channel = PluginAdapterChanged.CHANNEL)
    void onPluginChanged(PluginAdapterChanged changed) {
        pornClientRef.set(new PornClientImpl()); // Update client plugin
    }

    @Subscribe(channel = ParseCommand.CHANNEL)
    void onParseCommand(ParseCommand command) {
        PornClient client = pornClientRef.get();
        if (client == null) {
            return;
        }

        loadingExecutor.execute(() -> {
            try {
                switch (command.getTabType()) {
                    case HOME -> {
                        TabCardsVisitor<Node> cardsVisitor = tabPane.homeCardsVisitor();
                        cardsVisitor.clear();
                        client.requestMainPage(command.getPage()).forEach(videoInfo -> cardsVisitor.addCard(new VideoCard(videoInfo)));
                    }
                    case MODELS -> {
                        TabCardsVisitor<Node> cardsVisitor = tabPane.modelsCardsVisitor();
                        cardsVisitor.clear();
                        client.requestModelsPage(command.getPage()).forEach(info -> cardsVisitor.addCard(new ModelCard(info)));
                    }
                }
            } catch (UnsupportedOperationException e) {
                log.info("Plugin don't supports method {}", command.getTabType().name());
            }
        });
    }

    @Override
    public void close() {
        loadingExecutor.shutdownNow();
        loadingExecutor.close();
    }
}