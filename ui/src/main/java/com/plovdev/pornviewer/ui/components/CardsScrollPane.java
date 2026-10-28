package com.plovdev.pornviewer.ui.components;

import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import java.util.Objects;

public class CardsScrollPane extends ScrollPane {
    private double totalDeltaY = 0;
    private volatile boolean isPullOfEnable = true;
    private volatile float triggerThreshold = 300;

    private volatile Runnable onPulledOf = () -> {
    };

    public CardsScrollPane() {
        getStyleClass().add("porn-scroll");
        setFitToHeight(true);
        setFitToWidth(true);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setHbarPolicy(ScrollBarPolicy.NEVER);
    }

    public void initialize() {
        Node flowPane = getContent();

        addEventFilter(ScrollEvent.SCROLL, e -> {
            if (isPullOfEnable) {
                if (getVvalue() <= 0.01 && e.getDeltaY() > 0) {
                    if (e.isInertia()) {
                        totalDeltaY = 0;
                        flowPane.setTranslateY(0);
                        return;
                    }

                    totalDeltaY += e.getDeltaY();
                    flowPane.setTranslateY(calculateRubberPull(totalDeltaY));

                    if (totalDeltaY >= triggerThreshold) {
                        onPulledOf.run();
                        totalDeltaY = 0;
                        flowPane.setTranslateY(0);
                        e.consume();
                    }
                } else {
                    totalDeltaY = 0;
                    flowPane.setTranslateY(0);
                }
            }
        });
    }

    public boolean isPullOfEnable() {
        return isPullOfEnable;
    }

    public synchronized void setPullOfEnable(boolean pullOfEnable) {
        isPullOfEnable = pullOfEnable;
    }

    private double calculateRubberPull(double delta) {
        if (delta <= 0) return 0;
        double normalized = Math.min(delta / triggerThreshold, 2.0);
        if (normalized <= 1.0) {
            return triggerThreshold * 0.4 * Math.pow(normalized, 1.5);
        } else {
            double extra = (normalized - 1.0);
            return triggerThreshold * 0.4 + triggerThreshold * 0.3 * Math.log1p(extra);
        }
    }

    public synchronized void setOnPulledOf(Runnable onPulledOf) {
        this.onPulledOf = Objects.requireNonNull(onPulledOf);
    }

    public synchronized void setTriggerThreshold(float triggerThreshold) {
        this.triggerThreshold = triggerThreshold;
    }
}