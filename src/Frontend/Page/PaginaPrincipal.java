package Frontend.Page;

import Backend.Servicios.Cache;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Colores;
import Frontend.Components.RotatedLabel;
import Frontend.Home;
import Frontend.PopUp.AccesoUsuario;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;
import Frontend.PopUp.EdicionUsuario;

import java.awt.*;
import javax.swing.*;

public class PaginaPrincipal {

    public static JFrame principal;

    public PaginaPrincipal() {
        principal = new JFrame("Sistema de Reciclaje");
        inicializar();
    }

    private void inicializar() {
        String lineaCache = Cache.leerCache();

        if (lineaCache == null) {
            UsuarioServicio.desactivarEstado();
        } else {
            String[] cache = lineaCache.split(":");
            if (cache[1].equals("null")) {
                UsuarioServicio.desactivarEstado();
            } else {
                UsuarioServicio.activarEstado(cache[1]);
            }
        }

        principal.setSize(1000, 600);
        principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        principal.setLocationRelativeTo(null);
        principal.setResizable(false);
        principal.getContentPane().setBackground(Color.LIGHT_GRAY);

        JPanel panelVertical = new JPanel();
        panelVertical.setLayout(new BoxLayout(panelVertical, BoxLayout.Y_AXIS));

        JPanel lineaAzulSuperior = new JPanel();
        lineaAzulSuperior.setBackground(Color.decode(Colores.DARK_BLUE));
        lineaAzulSuperior.setPreferredSize(new Dimension(1000, 85));
        lineaAzulSuperior.setMaximumSize(new Dimension(Integer.MAX_VALUE, 85));
        lineaAzulSuperior.setLayout(new BoxLayout(lineaAzulSuperior, BoxLayout.X_AXIS));
        panelVertical.add(lineaAzulSuperior);

        JPanel lineaBlanca = new JPanel();
        lineaBlanca.setBackground(Color.decode(Colores.LIGHT_BLUE));
        lineaBlanca.setPreferredSize(new Dimension(1000, 5));
        lineaBlanca.setMaximumSize(new Dimension(Integer.MAX_VALUE, 5));
        panelVertical.add(lineaBlanca);

        JPanel lineaAzulInferior = new JPanel();
        lineaAzulInferior.setBackground(Color.decode(Colores.DARK_BLUE));
        lineaAzulInferior.setPreferredSize(new Dimension(1000, 35));
        lineaAzulInferior.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35)); 
        panelVertical.add(lineaAzulInferior);

        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(255, 255, 255, 128));
        contenido.setOpaque(false);
        contenido.setPreferredSize(new Dimension(1000, 480));
        contenido.setLayout(null);
        panelVertical.add(contenido);

        JPanel panelAmarillo = Estilos.crearPanelAmarillo();
        panelAmarillo.setLayout(null);

        JButton usuarioInactivoBtn = Estilos.crearBotonConImagen(55, 55, "imagenes\\usuario_inactivo.png");
        JButton usuarioActivoBtn = Estilos.crearBotonConImagen(55, 55, "imagenes\\usuario_activo.png");
        JButton cerrarSesionBtn = Estilos.crearBotonConImagen(55, 55, "imagenes\\cerrar_sesion.png");

        usuarioInactivoBtn.setBounds(905, 13, usuarioInactivoBtn.getPreferredSize().width, usuarioInactivoBtn.getPreferredSize().height);
        usuarioActivoBtn.setBounds(845, 13, usuarioInactivoBtn.getPreferredSize().width, usuarioInactivoBtn.getPreferredSize().height);
        cerrarSesionBtn.setBounds(905, 13, usuarioInactivoBtn.getPreferredSize().width, usuarioInactivoBtn.getPreferredSize().height);

        usuarioInactivoBtn.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new AccesoUsuario(), 1270, 340);
        });

        usuarioActivoBtn.addActionListener(e -> {
            VisualizadorPanel.mostrarPanel(new EdicionUsuario(), 0, 0);
        });

        cerrarSesionBtn.addActionListener(e -> {
            UsuarioServicio.desactivarEstado();
            principal.dispose();

            java.awt.EventQueue.invokeLater(() -> {
                Home.main(new String[0]);
            });
        });

        if (UsuarioServicio.getEstado().equals("inactivo")) {
            panelAmarillo.add(usuarioInactivoBtn);
        } else {
            panelAmarillo.add(usuarioActivoBtn);
            panelAmarillo.add(cerrarSesionBtn);
        }

        lineaAzulSuperior.add(panelAmarillo);
        principal.add(panelVertical);

        JLabel titulo = new JLabel("¡Bienvenido a una nueva era del reciclaje!");
        titulo.setFont(new Font("Comic Sans MS", Font.BOLD, 22));
        titulo.setForeground(Color.decode(Colores.BLACK));
        titulo.setBounds(280, -10, 1000, 100);

        JTextArea descripcion = new JTextArea(
                "           Descubre una forma innovadora y sencilla de cuidar el planeta.\n"
                        + "Recicla, gana recompensas y forma parte del cambio que nuestro mundo necesita.\n"
                        + "                   ¡Porque reciclar nunca fue tan fácil, útil y motivador!"
        );
        descripcion.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        descripcion.setForeground(Color.decode(Colores.BLACK));
        descripcion.setBackground(new Color(0, 0, 0, 0));
        descripcion.setOpaque(false);
        descripcion.setAlignmentX(Component.CENTER_ALIGNMENT);
        descripcion.setBounds(250, 65, 1000, 100);

        ImageIcon imgPaginaPrincipal = new ImageIcon("imagenes\\representacion_reciclaje.png");
        Image img = imgPaginaPrincipal.getImage().getScaledInstance(600, 350, Image.SCALE_SMOOTH);
        ImageIcon imagenRedimensionada = new ImageIcon(img);
        JLabel imgReciclaje = new JLabel(imagenRedimensionada);
        imgReciclaje.setBounds(-15, 100, 1000, 300);

        RotatedLabel punto_1 = new RotatedLabel("Gana recompensas por reciclar", 12);
        punto_1.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        punto_1.setForeground(Color.decode(Colores.BLACK));
        punto_1.setBounds(-360, 130, 1000, 100);

        ImageIcon imgIcon_1 = new ImageIcon("imagenes\\punto_1.png");
        Image img_1 = imgIcon_1.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH); 
        ImageIcon imagenRedimensionada_1 = new ImageIcon(img_1);
        JLabel img_punto_1 = new JLabel(imagenRedimensionada_1);
        img_punto_1.setBounds(130, 100, 65, 65);

        RotatedLabel punto_2 = new RotatedLabel("Contribuye a un planeta más limpio", -12); 
        punto_2.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        punto_2.setForeground(Color.decode(Colores.BLACK));
        punto_2.setBounds(-360, 280, 1000, 100);

        ImageIcon imgIcon_2 = new ImageIcon("imagenes\\punto_2.png");
        Image img_2 = imgIcon_2.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH); 
        ImageIcon imagenRedimensionada_2 = new ImageIcon(img_2);
        JLabel img_punto_2 = new JLabel(imagenRedimensionada_2);
        img_punto_2.setBounds(90, 245, 65, 65);

        RotatedLabel punto_3 = new RotatedLabel("Reciclaje fácil y programado", -12);
        punto_3.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        punto_3.setForeground(Color.decode(Colores.BLACK));
        punto_3.setBounds(345, 135, 1000, 100);

        ImageIcon imgIcon_3 = new ImageIcon("imagenes\\punto_3.png");
        Image img_3 = imgIcon_3.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH); 
        ImageIcon imagenRedimensionada_3 = new ImageIcon(img_3);
        JLabel img_punto_3 = new JLabel(imagenRedimensionada_3);
        img_punto_3.setBounds(795, 100, 65, 65);

        RotatedLabel punto_4 = new RotatedLabel("Monitorea tu impacto ambiental", 12);
        punto_4.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        punto_4.setForeground(Color.decode(Colores.BLACK));
        punto_4.setBounds(355, 285, 1000, 100);

        ImageIcon imgIcon_4 = new ImageIcon("imagenes\\punto_4.png");
        Image img_4 = imgIcon_4.getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH); 
        ImageIcon imagenRedimensionada_4 = new ImageIcon(img_4);
        JLabel img_punto_4 = new JLabel(imagenRedimensionada_4);
        img_punto_4.setBounds(820, 250, 65, 65);

        contenido.add(titulo);
        contenido.add(descripcion);
        contenido.add(imgReciclaje);
        contenido.add(punto_1);
        contenido.add(img_punto_1);
        contenido.add(punto_2);
        contenido.add(img_punto_2);
        contenido.add(punto_3);
        contenido.add(img_punto_3);
        contenido.add(punto_4);
        contenido.add(img_punto_4);

        principal.setVisible(true);
    }
}
