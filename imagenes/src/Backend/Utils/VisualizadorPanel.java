package Backend.Utils;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class VisualizadorPanel {

    private static JDialog dialogo;

    public static void mostrarPanel(JPanel panel, int posicionEjeX , int posicionEjeY) {
        if (dialogo != null && dialogo.isShowing()) {
            dialogo.dispose();
            dialogo = null;
        }

        dialogo = new JDialog();
        dialogo.setUndecorated(true);

        dialogo.getContentPane().removeAll();
        dialogo.getContentPane().add(panel, BorderLayout.CENTER);

        dialogo.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentResized(java.awt.event.ComponentEvent e) {
                dialogo.setShape(new RoundRectangle2D.Double(0, 0, dialogo.getWidth(), dialogo.getHeight(), 20, 20));
            }
        });

        dialogo.pack();

        if (posicionEjeX == 0 && posicionEjeY == 0) {
            dialogo.setLocationRelativeTo(null);
        } else {
            dialogo.setLocation(posicionEjeX, posicionEjeY);
        }

        dialogo.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                dialogo.dispose();
                dialogo = null;
            }
        });

        dialogo.setVisible(true);
    }

    public static void cerrarDialogo() {
        if (dialogo != null && dialogo.isShowing()) {
            dialogo.dispose();
            dialogo = null;
        }
    }
}
