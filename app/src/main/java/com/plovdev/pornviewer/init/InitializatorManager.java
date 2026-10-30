package com.plovdev.pornviewer.init;

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
            log.warn("App initializators are empty");
            return;
        }
        if (listener == null) {
            log.warn("Listener is null");
            listener = (result, value) -> {
            };
        }

        int totalValue = 0;
        try {
            int percent = 100 / initializators.size(); // деление без floa point для того чтобы в конце finish мог закончить status bar
            for (AppInitializator initializator : initializators) {
                InitType result = initializator.getInitType();
                listener.onInit(result, totalValue);
                initializator.initialize();
                totalValue += percent;
            }

            listener.onInit(InitType.FINISH, 100); // Обновляем до finish в любом случае, чтобы выглядело что что то еще инициализируется.
        } catch (Exception e) {
            log.error("Error initialize app: ", e);
            listener.onInit(InitType.ERROR, totalValue);
            throw e; // drop app if error occured while initializing
        }
    }

    public InitializatorListener getListener() {
        return listener;
    }

    public void setListener(InitializatorListener listener) {
        this.listener = listener;
    }
}