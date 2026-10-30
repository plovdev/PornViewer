package com.plovdev.pornviewer.ui.components;

import com.plovdev.pornviewer.core.events.GlobalEventManager;
import com.plovdev.pornviewer.core.models.adapter.PluginInfo;
import com.plovdev.pornviewer.core.models.app.AppSettings;
import com.plovdev.pornviewer.database.tables.PluginsProvider;
import com.plovdev.pornviewer.pvvasupport.PluginsImportUtils;
import com.plovdev.pornviewer.settings.SettingsManager;
import com.plovdev.pornviewer.ui.events.ParseCommand;
import com.plovdev.pornviewer.ui.events.PluginAdapterChanged;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;

import java.util.HashMap;
import java.util.Map;

public class PluginSelectorCombobox extends ComboBox<String> {
    private final Map<String, PluginInfo> plugins = new HashMap<>();
    private static final SettingsManager manager = SettingsManager.getInstance();

    public PluginSelectorCombobox() {
        super();
        getStyleClass().add("plugins-selector");
        setPromptText("Выберите плагин");
        setPrefWidth(170);

        setCellFactory(param -> new ListCell<>() {
            private final Separator separator = new Separator();

            @Override
            protected void updateItem(String s, boolean b) {
                super.updateItem(s, b);

                if (s == null || s.isBlank()) {
                    setText(null);
                    setGraphic(null);
                } else if (s.equals("---")) {
                    setText(null);
                    separator.setPrefWidth(150);
                    setGraphic(separator);
                    setDisable(true);
                } else {
                    setText(s);
                    setGraphic(null);
                    setDisable(false);
                }
            }
        });

        for (PluginInfo info : PluginsProvider.getAllAdapters()) {
            plugins.put(info.pluginId(), info);
            this.getItems().add(info.pluginId());
        }

        this.getItems().addAll("---", "Импортировать плагин");

        getSelectionModel().selectedItemProperty().addListener((p1, old, val) -> {
            if (val == null) {
                return;
            }

            if (val.equals("Импортировать плагин")) {
                //TODO: show file chooser
            } else {
                PluginInfo info = plugins.get(val);
                if (info != null) {
                    AppSettings settings = manager.getAppSettings();
                    settings.setAdapter(info.systemPluginId());
                    manager.setAppSettings(settings);
                    GlobalEventManager.broadcastEvent(new PluginAdapterChanged(info.systemPluginId()));
                    GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.HOME, 0));
                    GlobalEventManager.broadcastEvent(new ParseCommand(ParseCommand.TabType.MODELS, 0));
                }
            }
        });

        PluginInfo info = plugins.get(manager.getAppSettings().getAdapter());
        if (!PluginsImportUtils.isDefaultPlugin(info.systemPluginId())) {
            getSelectionModel().select(info.pluginId());
        }
    }
}