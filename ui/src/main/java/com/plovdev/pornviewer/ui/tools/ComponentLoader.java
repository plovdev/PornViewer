package com.plovdev.pornviewer.ui.tools;

import com.plovdev.pornviewer.ui.controllers.ComponentController;
import com.plovdev.pornviewer.ui.exceptions.FXMLLoadingException;
import javafx.fxml.FXMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ComponentLoader {
    private static final Logger log = LoggerFactory.getLogger(ComponentLoader.class);

    public static void loadComponent(String name, Object root, ComponentController controller) {
        FXMLLoader loader = new FXMLLoader(ComponentLoader.class.getResource("/com/plovdev/pornviewer/fxml/components/" + name + ".fxml"));
        loader.setRoot(root);
        loader.setController(controller);

        try {
            loader.load();
        } catch (IOException e) {
            log.error("Error loading {} component: ", name, e);
            throw new FXMLLoadingException(e.getMessage());
        }
    }
}