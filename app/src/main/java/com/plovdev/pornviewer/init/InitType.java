package com.plovdev.pornviewer.init;

public enum InitType {
    START("Launching PornViewer..."),
    INIT_FILES("Preparing files map..."),
    INIT_SECURITY("Initializing security..."),
    INIT_SETTINGS("Loading settings..."),
    INIT_DATABASE("Initializing databases..."),
    ERROR("Initializing error, check logs"),
    FINISH("Finish");

    private final String message;

    InitType(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}