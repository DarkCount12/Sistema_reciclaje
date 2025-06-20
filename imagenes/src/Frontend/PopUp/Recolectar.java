package Frontend.PopUp;

import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.swing.*;

public class Recolectar extends JFrame {

public static void main(String[] args) {
    new Recolectar(1,1);
}

    public Recolectar(int idpunto,int idUsua) {
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

        JTextField campoIdUsuario = new JTextField(String.valueOf(idUsua));
        campoIdUsuario.setEditable(false); // No editable
        campoIdUsuario.setBackground(Color.LIGHT_GRAY);
        campoIdUsuario.setBounds(140, 30, 200, 25);
        panel.add(campoIdUsuario);

        JLabel labelPunto = new JLabel("ID Punto Recolección:");
        labelPunto.setBounds(30, 70, 150, 25);
        panel.add(labelPunto);

        // Puedes cambiar esto a un JComboBox si quieres mostrar lista
        JTextField campoPunto = new JTextField(String.valueOf(idpunto));
        campoPunto.setBounds(180, 70, 160, 25);
        campoPunto.setEditable(false); // No editable
        campoPunto.setBackground(Color.LIGHT_GRAY);
        panel.add(campoPunto);

        JLabel labelFecha = new JLabel("Fecha actual:");
        labelFecha.setBounds(30, 110, 100, 25);
        panel.add(labelFecha);

        JTextField campoFecha = new JTextField();
        campoFecha.setBounds(140, 110, 200, 25);
        campoFecha.setEditable(false);
        campoFecha.setBackground(Color.LIGHT_GRAY);

        // Fecha actual
        LocalDateTime ahora = LocalDateTime.now();

        // Formatear (opcional, si solo quieres verlo como texto)
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd          HH:mm:ss");
        String fechaHoy = ahora.format(formato);
        campoFecha.setText(fechaHoy);
        panel.add(campoFecha);






        JButton btnGuardar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Guardar", 155, 30);
        btnGuardar.setBounds(140, 160, 100, 30);
        panel.add(btnGuardar);



        btnGuardar.addActionListener(e -> {
        String idUsuario =campoIdUsuario.getText();
        int idU = Integer.parseInt(idUsuario);
        int idP =idpunto;
       
        new ListadeMateriales(idU,idP);
        dispose();
         });

        add(panel);
        setVisible(true);
    }







}
