package Frontend.PopUp;

import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Recolectar extends JFrame {

    public Recolectar() {
        setTitle("Nuevo Reciclaje");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // No cierra la app principal

        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        JLabel labelIdUsuario = new JLabel("ID Usuario:");
        labelIdUsuario.setBounds(30, 30, 100, 25);
        panel.add(labelIdUsuario);

        JTextField campoIdUsuario = new JTextField();
        campoIdUsuario.setBounds(140, 30, 200, 25);
        panel.add(campoIdUsuario);

        JLabel labelPunto = new JLabel("ID Punto Recolección:");
        labelPunto.setBounds(30, 70, 150, 25);
        panel.add(labelPunto);

        // Puedes cambiar esto a un JComboBox si quieres mostrar lista
        JTextField campoPunto = new JTextField();
        campoPunto.setBounds(180, 70, 160, 25);
        panel.add(campoPunto);

        JLabel labelFecha = new JLabel("Fecha actual:");
        labelFecha.setBounds(30, 110, 100, 25);
        panel.add(labelFecha);

        JTextField campoFecha = new JTextField();
        campoFecha.setBounds(140, 110, 200, 25);
        campoFecha.setEditable(false);
        campoFecha.setBackground(Color.LIGHT_GRAY);

        // Fecha actual
        String fechaHoy = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        campoFecha.setText(fechaHoy);
        panel.add(campoFecha);

        JButton btnGuardar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Guardar", 155, 30);
        btnGuardar.setBounds(140, 160, 100, 30);
        panel.add(btnGuardar);

        add(panel);
        setVisible(true);
    }
}
