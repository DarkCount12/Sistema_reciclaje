package Frontend.Page;

import Frontend.Maps.CrudPanel;
import Frontend.Maps.RecompensaFormMapper;
import Frontend.Page.Secciones.Reportes;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import Frontend.Home;
import Frontend.Maps.*;
import Backend.DAOs.CrudDAO;

import Backend.DAOs.Recompensa2DAO;
import Backend.Modelos.Recompensa;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Estilos;

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
    ventanaAdmin.setSize(1000, 600);
    ventanaAdmin.setLocationRelativeTo(null);
    ventanaAdmin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventanaAdmin.setLayout(new BorderLayout());

    JPanel panelPrincipal = new JPanel(new BorderLayout());
    panelPrincipal.setBackground(Color.DARK_GRAY);

    // PANEL SUPERIOR
    JPanel panelSuperior = new JPanel(new BorderLayout());
    panelSuperior.setBackground(Color.DARK_GRAY);
    panelSuperior.setPreferredSize(new java.awt.Dimension(1000, 50));

    // Título a la izquierda
    JLabel titulo = new JLabel("PANEL DEL ADMINISTRADOR");
    titulo.setForeground(Color.WHITE);
    titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
    titulo.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 15, 10, 0)); // Padding
    panelSuperior.add(titulo, BorderLayout.WEST);

    // Botón cerrar sesión a la derecha
    JButton cerrarSesionBtn = Estilos.crearBotonConImagen(40, 40, "imagenes\\cerrar_sesion.png");
    cerrarSesionBtn.setOpaque(false);
    cerrarSesionBtn.setContentAreaFilled(false);
    cerrarSesionBtn.setBorderPainted(false);
    cerrarSesionBtn.setFocusPainted(false);

    cerrarSesionBtn.addActionListener(e -> {
        UsuarioServicio.desactivarEstado();
        ventanaAdmin.dispose();
        java.awt.EventQueue.invokeLater(() -> {
            Home.main(new String[0]);
        });
    });

    JPanel panelCerrar = new JPanel();
    panelCerrar.setBackground(Color.DARK_GRAY);
    panelCerrar.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 0, 5, 15));
    panelCerrar.add(cerrarSesionBtn);
    panelSuperior.add(panelCerrar, BorderLayout.EAST);

    // Pestañas
    pestañas = new JTabbedPane();
    pestañas.setBackground(Color.BLACK);
    pestañas.setForeground(Color.WHITE);
    pestañas.setFont(new Font("SansSerif", Font.BOLD, 14));

    pestañas.addTab("Funciones", crearPanelFunciones());
    pestañas.addTab("xxxxxxxx", crearPanelPlaceholder("Gestión de usuarios"));
    pestañas.addTab("xxxxxxxx", crearPanelPlaceholder("Análisis y métricas"));
    pestañas.addTab("Reportes", Reportes.crearPanelReportes());

    // Agregar al panel principal
    panelPrincipal.add(panelSuperior, BorderLayout.NORTH);
    panelPrincipal.add(pestañas, BorderLayout.CENTER);

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
