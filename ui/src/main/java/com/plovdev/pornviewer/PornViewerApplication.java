package com.plovdev.pornviewer;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.jspecify.annotations.NonNull;

public class PornViewerApplication extends Application {
    @Override
    public void start(@NonNull Stage primaryStage) {
        // Test scene
        Scene scene = new Scene(new StackPane(), 1000, 600);
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