package Frontend.PopUp;

import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;
import Frontend.PopUp.EdicionUsuario;
import Frontend.PopUp.InformacionUsuario;

import javax.swing.*;
import java.awt.*;

public class AccederPerfil extends JPanel {

    public AccederPerfil() {
        setLayout(new GridLayout(2, 1, 10, 10));
        setBackground(Color.decode(Colores.WHITE));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JButton infoBtn = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Ver información", 155, 30);
        JButton editarBtn = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Editar perfil", 155, 30);

        infoBtn.addActionListener(e -> {
            VisualizadorPanel.cerrarDialogo();
            VisualizadorPanel.mostrarPanel(new InformacionUsuario(), 0, 0);
        });

        editarBtn.addActionListener(e -> {
            VisualizadorPanel.cerrarDialogo();
            VisualizadorPanel.mostrarPanel(new EdicionUsuario(), 0, 0);
        });

        add(infoBtn);
        add(editarBtn);
    }
}
