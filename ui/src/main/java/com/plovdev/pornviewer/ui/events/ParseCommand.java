package com.plovdev.pornviewer.ui.events;

import org.plovdev.eda.ChannelEvent;

public class ParseCommand extends ChannelEvent {
    public static final String CHANNEL = "app.ui.plugins.parse";

    public enum TabType {
        HOME, MODELS, FAVORITES, DOWNLOADS
    }

    private TabType tabType;
    private String request;
    private int page;

    public ParseCommand() {
    }

    public ParseCommand(TabType tabType) {
        super(CHANNEL);
        this.tabType = tabType;
    }

    public ParseCommand(TabType type, String request, int page) {
        super(CHANNEL);
        this.tabType = type;
        this.request = request;
        this.page = page;
    }

    public ParseCommand(TabType type, String request) {
        super(CHANNEL);
        this.tabType = type;
        this.request = request;
    }

    public ParseCommand(TabType type, int page) {
        super(CHANNEL);
        this.tabType = type;
        this.page = page;
    }

    public TabType getTabType() {
        return tabType;
    }

    public void setTabType(TabType tabType) {
        this.tabType = tabType;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}