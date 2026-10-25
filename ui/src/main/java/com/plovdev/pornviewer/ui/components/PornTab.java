package com.plovdev.pornviewer.ui.components;

import javafx.scene.control.Tab;

public class PornTab extends Tab {
    public PornTab() {
        init();
    }

    public PornTab(String name) {
        super(name);
        init();
    }

    private void init() {
        this.setClosable(false);
        this.getStyleClass().add("porn-tab");
    }
}