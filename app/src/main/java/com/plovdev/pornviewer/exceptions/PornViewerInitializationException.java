package com.plovdev.pornviewer.exceptions;

import com.plovdev.pornviewer.core.exceptions.PornViewerException;

public class PornViewerInitializationException extends PornViewerException {
    public PornViewerInitializationException() {
    }

    public PornViewerInitializationException(String message) {
        super(message);
    }

    public PornViewerInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PornViewerInitializationException(Throwable cause) {
        super(cause);
    }

    public PornViewerInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}