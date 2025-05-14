package Frontend.PopUp;

import Backend.Utils.Estilos;
import Backend.Utils.Colores;
import Backend.Utils.VisualizadorPanel;

import javax.swing.*;
import java.awt.*;

public class SeleccionRol extends JPanel {

    public JButton btnUsuario;
    public JButton btnAdministrador;

    public SeleccionRol() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setBackground(Color.decode("#FCFCFC"));

        JLabel titulo = new JLabel("Elegir: Tipo de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.decode(Colores.BLUE));

        btnUsuario = Estilos.crearBoton(Colores.LIGHT_GRAY, Colores.BLACK,"Registrar Usuario", 220, 30);
        btnAdministrador =Estilos.crearBoton(Colores.LIGHT_GRAY, Colores.BLACK,"Registrar Administrador", 220, 30);

        btnUsuario.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new RegistroUsuario(), 0, 0);
        });

        btnUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdministrador.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnUsuario);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(btnAdministrador);
    }
}
