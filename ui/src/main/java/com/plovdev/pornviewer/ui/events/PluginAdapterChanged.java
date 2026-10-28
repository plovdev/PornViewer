package com.plovdev.pornviewer.ui.events;

import org.plovdev.eda.ChannelEvent;

public class PluginAdapterChanged extends ChannelEvent {
    public static final String CHANNEL = "pvva.event.changed";
    private final String pluginId;

    public PluginAdapterChanged(String pluginId) {
        super(CHANNEL);
        this.pluginId = pluginId;
    }

    public String getPluginId() {
        return pluginId;
    }
}