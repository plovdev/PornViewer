package com.plovdev.pornviewer.ui.exceptions;

import com.plovdev.pornviewer.core.exceptions.PornViewerException;

public class UIException extends PornViewerException {
    public UIException() {
    }

    public UIException(String message) {
        super(message);
    }

    public UIException(String message, Throwable cause) {
        super(message, cause);
    }

    public UIException(Throwable cause) {
        super(cause);
    }

    public UIException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}