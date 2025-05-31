package Frontend.Page;

import Frontend.Maps.CrudPanel;
import Frontend.Maps.RecompensaFormMapper;
import Frontend.Page.Secciones.Reportes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Frontend.Maps.*;
import Backend.DAOs.CrudDAO;

import Backend.DAOs.Recompensa2DAO;
import Backend.Modelos.Recompensa;

public class PaginaAdministrador {
    public static JFrame ventanaAdmin;
    private JTabbedPane pestañas;

    public static void main(String[] args) {
        new PaginaAdministrador();
    }

    public PaginaAdministrador() {
        ventanaAdmin = new JFrame("Panel del Administrador");
        inicializar();
    }

    public void inicializar() {
        // Estilo de la ventana
        ventanaAdmin.setSize(1000, 600);
        ventanaAdmin.setLocationRelativeTo(null);
        ventanaAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaAdmin.setLayout(new BorderLayout());

        // Panel principal con color de fondo
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(Color.DARK_GRAY);

        // Crear las pestañas
        pestañas = new JTabbedPane();
        pestañas.setBackground(Color.BLACK);
        pestañas.setForeground(Color.WHITE);
        pestañas.setFont(new Font("SansSerif", Font.BOLD, 14));

        // Añadir pestañas vacías listas para usar
        pestañas.addTab("Funciones", crearPanelFunciones());
        pestañas.addTab("xxxxxxxx", crearPanelPlaceholder("Gestión de usuarios"));
        pestañas.addTab("xxxxxxxx", crearPanelPlaceholder("Análisis y métricas"));
        pestañas.addTab("Reportes", Reportes.crearPanelReportes());
         
        // pestañas.setSelectedIndex(3);
        // Agregar pestañas al panel principal
        panelPrincipal.add(pestañas, BorderLayout.CENTER);

        // Agregar al JFrame
        ventanaAdmin.add(panelPrincipal);

        ventanaAdmin.setVisible(true);
    }

    private JPanel crearPanelPlaceholder(String mensaje) {
        JPanel panel = new JPanel();
        JLabel label = new JLabel(mensaje);
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("SansSerif", Font.PLAIN, 16));
        panel.add(label);
        return panel;
    }


public JPanel crearPanelFunciones() {
    JPanel panel = new JPanel(new BorderLayout());

    JComboBox<String> selectorTabla = new JComboBox<>(new String[] {
        "Recompensa", "Puntos de Reciclaje", "Descuento", "Tipo Material"
    });

    JPanel panelCRUD = new JPanel(new BorderLayout());

    selectorTabla.addActionListener(e -> {
        String seleccion = (String) selectorTabla.getSelectedItem();
        panelCRUD.removeAll();

        switch (seleccion) {
            case "Recompensa":
                panelCRUD.add(new CrudPanel<Recompensa>(new Recompensa2DAO(), new RecompensaFormMapper()), BorderLayout.CENTER);

                break;
            // Agregar casos para las otras tablas
        }

        panelCRUD.revalidate();
        panelCRUD.repaint();
    });

    panel.add(selectorTabla, BorderLayout.NORTH);
    panel.add(panelCRUD, BorderLayout.CENTER);
    return panel;
}


}
