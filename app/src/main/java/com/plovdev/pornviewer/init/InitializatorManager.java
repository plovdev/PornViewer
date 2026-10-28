package com.plovdev.pornviewer.init;

import com.plovdev.pornviewer.core.exceptions.PornViewerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Application initializator manager.
 * Not thread safe
 */
public class InitializatorManager {
    private static final Logger log = LoggerFactory.getLogger(InitializatorManager.class);
    private final List<AppInitializator> initializators;
    private InitializatorListener listener;

    public InitializatorManager() {
        initializators = new ArrayList<>();
    }

    public void addAppInitializator(AppInitializator init) {
        initializators.add(init);
    }

    public void removeAppInitializator(AppInitializator init) {
        initializators.remove(init);
    }

    public void initializeApplication() {
        if (initializators.isEmpty()) {
            log.warn("App initializators is empty");
            return;
        }
        if (listener == null) {
            return;
        }

        try {
            int totalValue = 0;
            int percent = 100 / initializators.size();
            for (AppInitializator initializator : initializators) {
                InitType result = initializator.getInitType();
                listener.onInit(result, totalValue);
                initializator.initialize();
                totalValue += percent;
            }

            listener.onInit(InitType.FINISH, totalValue);
        } catch (PornViewerException e) {
            log.error("Error initialize app: ", e);
            listener.onInit(InitType.ERROR, 100);
            throw e;
        }
    }

    public InitializatorListener getListener() {
        return listener;
    }

    public void setListener(InitializatorListener listener) {
        this.listener = listener;
    }
}