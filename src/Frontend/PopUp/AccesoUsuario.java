package Frontend.PopUp;

import Backend.Utils.Estilos;
import Backend.Utils.Colores;
import Backend.Utils.VisualizadorPanel;
import javax.swing.*;
import java.awt.*;

public class AccesoUsuario extends JPanel {

    private JButton btnIniciarSesion;
    private JButton btnRegistrarse;

    public AccesoUsuario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.decode("#FCFCFC"));

        btnIniciarSesion = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Iniciar Sesion", 155, 30);
        btnRegistrarse =Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Registrarse", 155, 30);

        btnIniciarSesion.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new InicioSesionUsuario(), 0, 0);
        });

        btnRegistrarse.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new SeleccionRol(), 0, 0);
        });

        add(btnIniciarSesion);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(btnRegistrarse);
    }

}
