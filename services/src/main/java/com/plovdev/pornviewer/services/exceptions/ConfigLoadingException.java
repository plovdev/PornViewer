package com.plovdev.pornviewer.services.exceptions;

public class ConfigLoadingException extends SettingsLoadingException {
    public ConfigLoadingException() {
    }

    public ConfigLoadingException(String message) {
        super(message);
    }

    public ConfigLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigLoadingException(Throwable cause) {
        super(cause);
    }

    public ConfigLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}