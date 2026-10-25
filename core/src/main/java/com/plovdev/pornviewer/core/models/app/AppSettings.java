package com.plovdev.pornviewer.core.models.app;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.Locale;
import java.util.Objects;

public final class AppSettings {
    public static final String ID = "app-settings";

    private Locale locale;
    private String adapter;
    private AppUITheme theme;
    private String appPath;
    private boolean loadTrailers;

    public enum AppUITheme {
        BLACK, WHITE, SYSTEM
    }

    public AppSettings() {
        this(new Locale.Builder().setLanguage("ru").setRegion("RU").build(), "default", AppUITheme.SYSTEM, null, false);
    }

    public AppSettings(@NonNull Locale locale, @NonNull String adapter, @NonNull AppUITheme theme, @Nullable String appPath, boolean loadTrailers) {
        Objects.requireNonNull(locale);
        Objects.requireNonNull(adapter);
        Objects.requireNonNull(theme);
        this.locale = locale;
        this.adapter = adapter;
        this.theme = theme;
        this.appPath = appPath;
        this.loadTrailers = loadTrailers;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public @NonNull String getAdapter() {
        return adapter;
    }

    public void setAdapter(@NonNull String adapter) {
        this.adapter = adapter;
    }

    public @NonNull AppUITheme getTheme() {
        return theme;
    }

    public void setTheme(@NonNull AppUITheme theme) {
        this.theme = theme;
    }

    public @Nullable String getAppPath() {
        return appPath;
    }

    public void setAppPath(@Nullable String appPath) {
        this.appPath = appPath;
    }

    public boolean isLoadTrailers() {
        return loadTrailers;
    }

    public void setLoadTrailers(boolean loadTrailers) {
        this.loadTrailers = loadTrailers;
    }

    @Override
    public String toString() {
        return "AppSettings{" +
                "locale=" + locale +
                ", adapter='" + adapter + '\'' +
                ", theme=" + theme +
                ", appPath='" + appPath + '\'' +
                ", loadTrailers=" + loadTrailers +
                '}';
    }
}