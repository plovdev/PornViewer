package com.plovdev.pornviewer.pvvasupport.exceptions;

import com.plovdev.pornviewer.core.exceptions.PornViewerException;

public class PluginImportException extends PornViewerException {
    public PluginImportException() {
    }

    public PluginImportException(String message) {
        super(message);
    }

    public PluginImportException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginImportException(Throwable cause) {
        super(cause);
    }

    public PluginImportException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}