package com.plovdev.pornviewer.services.exceptions;

import com.plovdev.pornviewer.core.exceptions.PornViewerException;

public class SettingsLoadingException extends PornViewerException {
    public SettingsLoadingException() {
    }

    public SettingsLoadingException(String message) {
        super(message);
    }

    public SettingsLoadingException(String message, Throwable cause) {
        super(message, cause);
    }

    public SettingsLoadingException(Throwable cause) {
        super(cause);
    }

    public SettingsLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}