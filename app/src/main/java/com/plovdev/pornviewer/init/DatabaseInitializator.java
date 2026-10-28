package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.database.SecureDB;

public class DatabaseInitializator implements AppInitializator {
    @Override
    public InitType getInitType() {
        return InitType.INIT_DATABASE;
    }

    @Override
    public void initialize() {
        SecureDB.initDB();
    }
}