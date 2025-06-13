package Frontend.Page;

import Backend.DAOs.Descuento2DAO;
import Backend.DAOs.PuntoReciclaje2DAO;
import Backend.DAOs.Recompensa2DAO;
import Backend.DAOs.TipoMaterial2DAO;
import Backend.DAOs.Usuario2DAO;
import Backend.Modelos.Descuento;
import Backend.Modelos.PuntoReciclaje;
import Backend.Modelos.Recompensa;
import Backend.Modelos.TipoMaterial;
import Backend.Modelos.Usuario;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Estilos;
import Frontend.Home;
import Frontend.Maps.*;
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
    ventanaAdmin.setSize(1000, 705);
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
    pestañas.addTab("Usuarios", new UsuarioCrudPanel());
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

    // Subtítulo
    JLabel lblSeleccionTabla = new JLabel("Seleccione una tabla:");
    lblSeleccionTabla.setFont(new Font("SansSerif", Font.BOLD, 14));
    lblSeleccionTabla.setForeground(Color.DARK_GRAY);
    lblSeleccionTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 0, 10));

    // ComboBox
    JComboBox<String> selectorTabla = new JComboBox<>(new String[]{
        "Recompensa", "Puntos de Reciclaje", "Descuento", "Tipo Material"
    });
    selectorTabla.setFont(new Font("SansSerif", Font.PLAIN, 14));
    selectorTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(2, 10, 2, 10));

    // Título dinámico
    JLabel tituloTabla = new JLabel("Seleccione una opción");
    tituloTabla.setFont(new Font("SansSerif", Font.BOLD, 18));
    tituloTabla.setForeground(new Color(34, 40, 49));
    tituloTabla.setHorizontalAlignment(JLabel.CENTER);
    tituloTabla.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 10, 10, 10));

    // Agrupar título + selector + comboBox en un solo panel superior
    JPanel panelSuperior = new JPanel(new BorderLayout());
    JPanel panelSelector = new JPanel(new BorderLayout());
    panelSelector.add(lblSeleccionTabla, BorderLayout.NORTH);
    panelSelector.add(selectorTabla, BorderLayout.CENTER);
    panelSuperior.add(panelSelector, BorderLayout.NORTH);
    panelSuperior.add(tituloTabla, BorderLayout.SOUTH);  // Mucho más compacto

    // Panel CRUD
    JPanel panelCRUD = new JPanel(new BorderLayout());

    // Lógica del selector
    selectorTabla.addActionListener(e -> {
        String seleccion = (String) selectorTabla.getSelectedItem();
        panelCRUD.removeAll();

        switch (seleccion) {
            case "Recompensa":
                panelCRUD.add(new CrudPanel<Recompensa>(new Recompensa2DAO(), new RecompensaFormMapper()), BorderLayout.CENTER);
                tituloTabla.setText("Tabla de Recompensas");
                break;
            case "Puntos de Reciclaje":
                panelCRUD.add(new CrudPanel<PuntoReciclaje>(new PuntoReciclaje2DAO(), new PuntoReciclajeFormMapper()), BorderLayout.CENTER);
                tituloTabla.setText("Tabla de Puntos de Reciclaje");
                break;
            case "Tipo Material":
                panelCRUD.add(new CrudPanel<TipoMaterial>(new TipoMaterial2DAO(), new TipoMaterialFormMapper()), BorderLayout.CENTER);
                tituloTabla.setText("Tabla de Tipos de Material");
                break;
            case "Descuento":
                panelCRUD.add(new CrudPanel<Descuento>(new Descuento2DAO(), new DescuentoFormMapper()), BorderLayout.CENTER);
                tituloTabla.setText("Tabla de Descuentos");
                break;
        }

        panelCRUD.revalidate();
        panelCRUD.repaint();
    });

    panel.add(panelSuperior, BorderLayout.NORTH);
    panel.add(panelCRUD, BorderLayout.CENTER);

    return panel;
}



}
