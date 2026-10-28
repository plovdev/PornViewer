package com.plovdev.pornviewer;

import com.plovdev.pornviewer.init.InitType;
import org.jspecify.annotations.NonNull;
import org.plovdev.eda.ChannelEvent;
import org.plovdev.eda.reflect.Subscribe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

public class SplashScreenHandler {
    private static final Logger log = LoggerFactory.getLogger(SplashScreenHandler.class);
    private final SplashScreen splashScreen;
    private static final Color ORANGE = new Color(255, 83, 0);

    private Rectangle splashBounds;
    private Graphics2D splashGraphics;

    public SplashScreenHandler() {
        splashScreen = SplashScreen.getSplashScreen();
        setupGraphics();
    }

    private void setupGraphics() {
        if (splashScreen != null) {
            splashBounds = splashScreen.getBounds();
            splashGraphics = splashScreen.createGraphics();
            if (splashGraphics == null) return;

            splashGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            splashGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            splashGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            splashGraphics.setColor(ORANGE);
        }
    }

    public void onNextInitStep(@NonNull InitType result, int value) {
        if (splashScreen != null && splashGraphics != null) {
            int maxProgressBarWidth = (int) (splashBounds.width * 0.9);
            int currentWidth = (maxProgressBarWidth * value) / 100;
            splashGraphics.fillRoundRect(32, 300, currentWidth, 6, 4, 4);

            String text = result.getMessage();
            FontMetrics fm = splashGraphics.getFontMetrics();
            int textHeight = fm.getHeight();
            int textWidth = fm.stringWidth(text);
            int textX = (splashBounds.width - textWidth) / 2;
            int textY = 325;
            int clearY = textY - fm.getAscent();

            splashGraphics.setColor(Color.BLACK);
            splashGraphics.fillRect(10, clearY, splashBounds.width - 20, textHeight);
            splashGraphics.setColor(ORANGE);
            splashGraphics.drawString(text, textX, textY);

            splashScreen.update();
        }
    }

    @Subscribe(channel = "app.initializing.finish")
    void onInitFinished(ChannelEvent e) {
        if (splashScreen != null && splashGraphics != null) {
            splashGraphics.dispose();
            splashScreen.close();
        }
    }
}