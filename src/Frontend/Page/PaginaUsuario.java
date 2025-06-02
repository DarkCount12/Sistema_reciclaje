package Frontend.Page;

import Backend.Modelos.Recompensa;
import Backend.Servicios.RecompensaServicio;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;
import Frontend.Components.RotatedLabel;
import Frontend.Home;
import Frontend.PopUp.AccederDonaciones;
import Frontend.PopUp.AccederPerfil;
import java.awt.*;
import java.util.List;
import javax.swing.*;

public class PaginaUsuario {

    private JFrame frame;
    private RecompensaServicio recompensaServicio = new RecompensaServicio();
    private int currentPage = 1;
    private static final int REWARDS_PER_PAGE = 12;
    private static final int TOTAL_REWARDS = 21;

    public static void main(String[] args) {
        new PaginaUsuario();
    }
    public PaginaUsuario() {
        frame = new JFrame("Sistema de Reciclaje - Usuario");
        inicializar();
    }

    private void inicializar() {
        frame.setSize(1000, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.LIGHT_GRAY);
        frame.setLayout(new BorderLayout());

        // panel superior
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

        JPanel panelAmarillo = Estilos.crearPanelAmarillo();
        panelAmarillo.setLayout(null);

        JButton usuarioActivoBtn = Estilos.crearBotonConImagen(55, 55, "imagenes\\usuario_activo.png");
        JButton cerrarSesionBtn = Estilos.crearBotonConImagen(55, 55, "imagenes\\cerrar_sesion.png");

        usuarioActivoBtn.setBounds(845, 13, usuarioActivoBtn.getPreferredSize().width, usuarioActivoBtn.getPreferredSize().height);
        cerrarSesionBtn.setBounds(905, 13, cerrarSesionBtn.getPreferredSize().width, cerrarSesionBtn.getPreferredSize().height);

        usuarioActivoBtn.addActionListener(e -> {
            int x = frame.getX() + frame.getWidth() - 250;
            int y = frame.getY() + (frame.getHeight() - 375) / 2;

            VisualizadorPanel.mostrarPanel(new AccederPerfil(), x, y);
        });

        cerrarSesionBtn.addActionListener(e -> {
            UsuarioServicio.desactivarEstado();
            frame.dispose();
            java.awt.EventQueue.invokeLater(() -> {
                Home.main(new String[0]);
            });
        });

        panelAmarillo.add(usuarioActivoBtn);
        panelAmarillo.add(cerrarSesionBtn);
        lineaAzulSuperior.add(panelAmarillo);

        
        // pestañass
        JTabbedPane tabbedPane = new JTabbedPane();
        JPanel perfilPanel = createPerfilPanel();
        tabbedPane.addTab("Perfil", perfilPanel);

        JPanel rewardsPanel = createRewardsPanel();
        tabbedPane.addTab("Recompensas", rewardsPanel);
        JPanel donacionPanel = createDonacionesPanel(); 
        tabbedPane.addTab("donaciones", donacionPanel);
        tabbedPane.addChangeListener(e -> {
            int selectedIndex = tabbedPane.getSelectedIndex();
            String title = tabbedPane.getTitleAt(selectedIndex);

    
        });
        
        frame.add(panelVertical, BorderLayout.NORTH);
        frame.add(tabbedPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private JPanel createPerfilPanel() {
        JPanel contenido = new JPanel();
        contenido.setBackground(new Color(255, 255, 255, 128));
        contenido.setOpaque(false);
        contenido.setLayout(null);
        contenido.setPreferredSize(new Dimension(1000, 480));

        JLabel titulo = new JLabel("¡Bienvenido a una nueva era del reciclaje!");
        Estilos.aplicarEstiloEtiqueta(titulo);
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

        JButton cerrarSesionVisibleBtn = Estilos.crearBoton("#FF0000", "#FFFFFF", "Cerrar Sesión", 150, 40);
        cerrarSesionVisibleBtn.setBounds(425, 400, 150, 40);
        cerrarSesionVisibleBtn.addActionListener(e -> {
            UsuarioServicio.desactivarEstado();
            frame.dispose();
            java.awt.EventQueue.invokeLater(() -> {
                Home.main(new String[0]);
            });
        });

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
        contenido.add(cerrarSesionVisibleBtn);

        return contenido;
    }

    private JPanel createRewardsPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);

        JLabel title = new JLabel("Recompensas Disponibles");
        Estilos.aplicarEstiloEtiqueta(title);
        title.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 10, 1000, 30);
        panel.add(title, BorderLayout.NORTH);

        JPanel rewardsContainer = new JPanel();
        rewardsContainer.setLayout(null);
        rewardsContainer.setBackground(Color.WHITE);
        rewardsContainer.setPreferredSize(new Dimension(950, 340)); // Aumentado de 300 a 340 para más espacio vertical
        updateRewardsDisplay(rewardsContainer);
        JScrollPane scrollPane = new JScrollPane(rewardsContainer);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel paginationPanel = new JPanel();
        paginationPanel.setLayout(null);
        paginationPanel.setPreferredSize(new Dimension(1000, 30)); // Reducido de 40 a 30 para aprovechar espacio
        paginationPanel.setBackground(Color.WHITE);

        JButton prevButton = Estilos.crearBoton("#007BFF", "#FFFFFF", "<< Página", 70, 15); // Reducido a 70x15 (más pequeño que el botón Canjear)
        prevButton.setBounds(375, 5, 70, 15);

        JLabel pageLabel = new JLabel("Página " + currentPage);
        Estilos.aplicarEstiloEtiqueta(pageLabel);
        pageLabel.setBounds(480, 5, 100, 15);

        JButton nextButton = Estilos.crearBoton("#007BFF", "#FFFFFF", "Página >>", 70, 15); // Reducido a 70x15
        nextButton.setBounds(525, 5, 70, 15);

        prevButton.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                pageLabel.setText("Página " + currentPage);
                updateRewardsDisplay(rewardsContainer);
            }
        });

        nextButton.addActionListener(e -> {
            if (currentPage < getTotalPages()) {
                currentPage++;
                pageLabel.setText("Página " + currentPage);
                updateRewardsDisplay(rewardsContainer);
            }
        });

        paginationPanel.add(prevButton);
        paginationPanel.add(pageLabel);
        paginationPanel.add(nextButton);
        panel.add(paginationPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void updateRewardsDisplay(JPanel container) {
        container.removeAll();
        List<Recompensa> recompensas = recompensaServicio.obtenerTodasLasRecompensas();
        int start = (currentPage - 1) * REWARDS_PER_PAGE;
        int end = Math.min(start + REWARDS_PER_PAGE, TOTAL_REWARDS);

        int rewardsToShow = end - start;

        for (int i = 0; i < rewardsToShow; i++) {
            int index = start + i;
            JPanel rewardCard = new JPanel();
            rewardCard.setLayout(null);
            rewardCard.setBackground(Color.YELLOW);
            rewardCard.setBorder(BorderFactory.createLineBorder(Color.GREEN));

            
            int cardWidth = 110;
            int cardHeight = 160; 
            int x = 20 + (i % 6) * 150; 
            int y = 10 + (i / 6) * 170; 
            rewardCard.setBounds(x, y, cardWidth, cardHeight);

            //imagen
            ImageIcon cuponIcon = new ImageIcon("imagenes\\cupon.png");
            Image img = cuponIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(img);
            JLabel imageLabel = new JLabel(scaledIcon);
            imageLabel.setBounds(30, 5, 50, 50);
            rewardCard.add(imageLabel);

            //id
            JLabel idLabel = new JLabel("ID: " + (index + 1));
            Estilos.aplicarEstiloEtiqueta(idLabel);
            idLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            idLabel.setBounds(5, 60, 100, 20);
            rewardCard.add(idLabel);

            //descripción
            JTextArea descArea = new JTextArea("Descripción: " + (recompensas.size() > index ? recompensas.get(index).descripcion : "Descripción"));
            descArea.setFont(new Font("Arial", Font.PLAIN, 10));
            descArea.setLineWrap(true);
            descArea.setWrapStyleWord(true);
            descArea.setOpaque(false);
            descArea.setEditable(false);
            descArea.setBounds(5, 80, 100, 40); 
            rewardCard.add(descArea);

            //puntos
            JLabel puntosLabel = new JLabel("Puntos: " + (recompensas.size() > index ? recompensas.get(index).puntos_necesarios : 100));
            Estilos.aplicarEstiloEtiqueta(puntosLabel);
            puntosLabel.setFont(new Font("Arial", Font.PLAIN, 10));
            puntosLabel.setBounds(5, 120, 100, 20); 
            rewardCard.add(puntosLabel);

            //Canjear
            JButton canjearBtn = Estilos.crearBoton("#008000", "#FFFFFF", "Canjear", 80, 20);
            canjearBtn.setFont(new Font("Arial", Font.PLAIN, 10)); 
            canjearBtn.setBounds(15, 135, 80, 20); 
            rewardCard.add(canjearBtn);

            container.add(rewardCard);
        }

        container.revalidate();
        container.repaint();
    }

    private int getTotalPages() {
        return (int) Math.ceil((double) TOTAL_REWARDS / REWARDS_PER_PAGE);
    }



    private JPanel createDonacionesPanel() {
    JPanel donacionPanel = new JPanel();
    donacionPanel.setLayout(new BorderLayout());
    // Añade aquí componentes o paneles que muestren las donaciones, por ejemplo:
    donacionPanel.add(new AccederDonaciones()); // si AccederDonaciones es un JPanel
    return donacionPanel;
}

}