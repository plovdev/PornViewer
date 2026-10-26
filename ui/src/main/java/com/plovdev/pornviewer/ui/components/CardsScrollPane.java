package com.plovdev.pornviewer.ui.components;

import javafx.scene.control.ScrollPane;
import javafx.scene.input.ScrollEvent;

import java.util.Objects;

public class CardsScrollPane extends ScrollPane {
    private double totalDeltaY = 0;
    private volatile float triggerThreshold;

    private volatile Runnable onPulledOf = () -> {
    };

    public CardsScrollPane(CardsFlowPane pane) {
        getStyleClass().add("porn-scroll");
        setFitToHeight(true);
        setFitToWidth(true);

        addEventFilter(ScrollEvent.SCROLL, e -> {
            if (getVvalue() <= 0.01 && e.getDeltaY() > 0) {
                if (e.isInertia()) {
                    totalDeltaY = 0;
                    pane.setTranslateY(0);
                    return;
                }

                totalDeltaY += e.getDeltaY();
                pane.setTranslateY(calculateRubberPull(totalDeltaY));

                if (totalDeltaY >= triggerThreshold) {
                    onPulledOf.run();
                    totalDeltaY = 0;
                    pane.setTranslateY(0);
                    e.consume();
                }
            } else {
                totalDeltaY = 0;
                pane.setTranslateY(0);
            }
        });
    }

    private double calculateRubberPull(double delta) {
        if (delta <= 0) return 0;
        double normalized = Math.min(delta / triggerThreshold, 2.0);
        if (normalized <= 1.0) {
            return triggerThreshold * 0.4 * Math.pow(normalized, 1.5);
        } else {
            float extra = (float) (normalized - 1.0);
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