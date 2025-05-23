package Frontend.PopUp.SolicitudRespuesta;

import Backend.Utils.VisualizadorPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

public class ConfirmacionPersonalizada {

    public interface ConfirmacionListener {
        void onConfirmar();
        void onCancelar();
    }

    public static void mostrar(String mensaje, ConfirmacionListener listener) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel(mensaje);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel botones = new JPanel();
        botones.setBackground(Color.WHITE);
        botones.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton botonSi = new JButton("Sí");
        JButton botonNo = new JButton("No");

        botonSi.addActionListener((ActionEvent e) -> {
            VisualizadorPanel.cerrarDialogo();
            if (listener != null) listener.onConfirmar();
        });

        botonNo.addActionListener((ActionEvent e) -> {
            VisualizadorPanel.cerrarDialogo();
            if (listener != null) listener.onCancelar();
        });

        botones.add(botonSi);
        botones.add(botonNo);

        panel.add(label, BorderLayout.NORTH);
        panel.add(botones, BorderLayout.SOUTH);

        VisualizadorPanel.mostrarPanel(panel, 0, 0);
    }
}