package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.security.PVSecurityManager;

public class SecurityInitializator implements AppInitializator {
    @Override
    public InitType getInitType() {
        return InitType.INIT_SECURITY;
    }

    @Override
    public void initialize() {
        PVSecurityManager.initSecurity();
    }
}