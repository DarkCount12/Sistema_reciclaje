package Frontend.Page;

import Frontend.Page.Secciones.Reportes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

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
        pestañas.addTab("Funciones", crearPanelPlaceholder("Colocar funciones aqui "));
        pestañas.addTab("Usuarios", crearPanelPlaceholder("Gestión de usuarios"));
        pestañas.addTab("Estadísticas", crearPanelPlaceholder("Análisis y métricas"));
        pestañas.addTab("Reportes", Reportes.crearPanelReportes());
         
        pestañas.setSelectedIndex(3);
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
}
