package com.plovdev.pornviewer.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jspecify.annotations.NonNull;

import java.util.Objects;

public class PornViewerApplication extends Application {
    @Override
    public void start(@NonNull Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/plovdev/pornviewer/fxml/screens/main-layout.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add(Objects.requireNonNull(PornViewerApplication.class.getResource("/com/plovdev/pornviewer/css/white.css")).toExternalForm());

        primaryStage.setTitle("PornViewer 2");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        //TODO: cleanup
    }
}