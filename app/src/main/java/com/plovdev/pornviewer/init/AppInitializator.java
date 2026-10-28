package com.plovdev.pornviewer.init;

public interface AppInitializator {
    InitType getInitType();
    void initialize();
}