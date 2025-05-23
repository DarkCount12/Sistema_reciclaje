package Frontend.PopUp;

import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;
import java.awt.*;
import javax.swing.*;

public class SeleccionRol extends JPanel {

    public JButton btnUsuario;
    public JButton btnRecolector;
    public int rol;

    public SeleccionRol() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        setBackground(Color.decode("#FCFCFC"));

        JLabel titulo = new JLabel("Elegir: Tipo de Usuario");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setForeground(Color.decode(Colores.BLUE));

        btnUsuario = Estilos.crearBoton(Colores.LIGHT_GRAY, Colores.BLACK,"Registrar Usuario", 220, 30);
        btnRecolector =Estilos.crearBoton(Colores.LIGHT_GRAY, Colores.BLACK,"Registrar Recolector", 220, 30);
        
        btnUsuario.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new RegistroUsuario(3), 0, 0);
            
        });

        btnRecolector.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new RegistroUsuario(2), 0, 0);
            
        });
         
        



        btnUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRecolector.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(btnUsuario);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(btnRecolector);
    }


    public void rol(int rol){
    




    }




    





}
