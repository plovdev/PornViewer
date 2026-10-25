package com.plovdev.pornviewer.ui.tools;

import org.jspecify.annotations.NonNull;

import javax.swing.*;
import java.awt.*;

public class SplashScreenHandler {
    public static void handleSplashScreen() {
        SplashScreen splash = SplashScreen.getSplashScreen();
        if (splash != null) {
            SwingUtilities.invokeLater(() -> {
                updateSplashScreen(splash);
                splash.close();
            });
        }
    }

    public static void updateSplashScreen(@NonNull SplashScreen splash) {
        Rectangle bounds = splash.getBounds();
        Graphics2D g2d = splash.createGraphics();
        if (g2d == null) return;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setColor(new Color(255, 83, 0));

        int mult = bounds.width / 100;
        for (int i = 0; i <= 93; i += 2) {
            g2d.fillRoundRect(32, 300, i * mult, 6, 4, 4);
            splash.update();
        }

        g2d.dispose();
    }
}