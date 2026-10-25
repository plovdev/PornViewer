package com.plovdev.pornviewer.security;

public enum RegisteredSecurityModule {
    DATABASE("database"), PVIF_SUPPORT("pvifsupport"), PVVF_SUPPORT("pvvfsupport"), SETTINGS("settings");

    private final String moduleId;

    RegisteredSecurityModule(String moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleId() {
        return moduleId;
    }
}