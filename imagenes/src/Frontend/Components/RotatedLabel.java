package Frontend.Components;

import javax.swing.*;
import java.awt.*;

public class RotatedLabel extends JComponent {
    private final String text;
    private final double angle;

    public RotatedLabel(String text, double angle) {
        this.text = text;
        this.angle = Math.toRadians(angle);
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setFont(getFont());
        g2d.setColor(getForeground());

        int x = getWidth() / 2;
        int y = getHeight() / 2;

        g2d.translate(x, y);
        g2d.rotate(angle);
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textHeight = fm.getHeight();

        g2d.drawString(text, -textWidth / 2, textHeight / 4);
        g2d.dispose();
    }
}
