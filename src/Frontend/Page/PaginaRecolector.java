package Frontend.Page;

import Backend.DAOs.PuntajeDAO;
import Backend.DAOs.PuntoRecoleccionDAO;
import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.Puntaje;
import Backend.Modelos.PuntoRecoleccion;
import Backend.Modelos.Usuario;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Frontend.Home;
import Frontend.PopUp.Recolectar;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class PaginaRecolector {

    public static JFrame ventanaRecolector;
    private int idpunto = 1;
    private int idUsuario=0;
    private JLabel puntoActual;

    public static void main(String[] args) {
        new PaginaRecolector();
    }

   public PaginaRecolector() {
    ventanaRecolector = new JFrame("Panel del Recolector");
    ventanaRecolector.setSize(900, 550);
    ventanaRecolector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    ventanaRecolector.setLocationRelativeTo(null);
    ventanaRecolector.setResizable(false);

    JPanel principal = new JPanel(new BorderLayout());

    // Encabezado
    JPanel encabezado = new JPanel(new BorderLayout());
    encabezado.setBackground(Color.decode(Colores.DARK_BLUE));
    encabezado.setPreferredSize(new Dimension(900, 70));

    JLabel titulo = new JLabel("Panel del Recolector");
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setForeground(Color.WHITE);
    titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));

    //Cerrar Sesion
    JButton cerrarSesionBtn = Estilos.crearBotonConImagen(40, 40, "imagenes\\cerrar_sesion.png");
    cerrarSesionBtn.setBounds(100, 23, cerrarSesionBtn.getPreferredSize().width, cerrarSesionBtn.getPreferredSize().height);

       cerrarSesionBtn.addActionListener(e -> {
            UsuarioServicio.desactivarEstado();
            ventanaRecolector.dispose();
            java.awt.EventQueue.invokeLater(() -> {
                Home.main(new String[0]);
            });
        });

    // JLabel del punto actual
    puntoActual = new JLabel();
    puntoActual.setForeground(Color.WHITE);
    puntoActual.setFont(new Font("Arial", Font.PLAIN, 16));
    puntoActual.setHorizontalAlignment(SwingConstants.RIGHT);
    puntoActual.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 20));

    // Asignar automáticamente el primer punto de recolección si existe
    PuntoRecoleccionDAO dao = new PuntoRecoleccionDAO();
    List<PuntoRecoleccion> puntos = dao.obtenerTodos();

    if (puntos != null && !puntos.isEmpty()) {
        PuntoRecoleccion primerPunto = puntos.get(0); // Primer punto
        idpunto = primerPunto.getId();
        puntoActual.setText("Punto actual " + primerPunto.getId()+ " : " + primerPunto.getNombre() + " - Horario: " + primerPunto.getHorario());
    } else {
        puntoActual.setText("Punto actual: Ninguno");
    }



    // Botón Cambiar Punto (fuera de pestañas)
    JButton cambiarPunto = new JButton();
    cambiarPunto.setBackground(Color.BLACK);
    cambiarPunto.setPreferredSize(new Dimension(8, 10));
    
    cambiarPunto.addActionListener(e -> {
      

        if (puntos != null && !puntos.isEmpty()) {
            String[] opciones = puntos.stream()
                .map(p -> "ID: " + p.getId() + " - " + p.getNombre() + " (" + p.getDireccion() + ")")
                .toArray(String[]::new);

            String seleccion = (String) JOptionPane.showInputDialog(
                ventanaRecolector,
                "Selecciona un punto de recolección:",
                "Cambiar Punto",
                JOptionPane.PLAIN_MESSAGE,
                null,
                opciones,
                opciones[0]);

            if (seleccion != null) {
                int indexSeleccion = Arrays.asList(opciones).indexOf(seleccion);
                if (indexSeleccion >= 0) {
                    PuntoRecoleccion punto = puntos.get(indexSeleccion);
                    idpunto = punto.getId();
                    puntoActual.setText("Punto actual " + punto.getId()+ " : "  + punto.getNombre() + " - Horario: " + punto.getHorario());
                }
            }
        } else {
            JOptionPane.showMessageDialog(ventanaRecolector, "No hay puntos disponibles.");
        }
    });

    JPanel headerDerecha = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
    headerDerecha.setOpaque(false);
    headerDerecha.add(cambiarPunto);
    headerDerecha.add(puntoActual);
    headerDerecha.add(cerrarSesionBtn);

    encabezado.add(titulo, BorderLayout.WEST);
    encabezado.add(headerDerecha, BorderLayout.EAST);

    // Tabs
    JTabbedPane pestañas = new JTabbedPane();
    pestañas.addTab("Buscar Usuario", crearPanelRecolectarFusionado());
    pestañas.addTab("Materiales Permitidos", crearPanelMateriales());

    // Pie
    JPanel pie = new JPanel();
    pie.setBackground(Color.decode(Colores.LIGHT_BLUE));
    pie.setPreferredSize(new Dimension(900, 30));

    principal.add(encabezado, BorderLayout.NORTH);
    principal.add(pestañas, BorderLayout.CENTER);
    principal.add(pie, BorderLayout.SOUTH);

    ventanaRecolector.add(principal);
    ventanaRecolector.setVisible(true);
}

private JPanel crearPanelRecolectarFusionado() {
    JPanel panel = new JPanel(null);
    panel.setBackground(Color.WHITE);

    JLabel buscarLabel = new JLabel("Buscar ID por correo:");
    buscarLabel.setBounds(50, 30, 200, 30);
    panel.add(buscarLabel);

    JTextField campoCorreo = new JTextField();
    campoCorreo.setBounds(200, 30, 300, 30);
    panel.add(campoCorreo);

    JButton botonBuscar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Buscar", 155, 30);
    botonBuscar.setBounds(520, 30, 100, 30);
    panel.add(botonBuscar);

    JTextArea resultadoArea = new JTextArea();
    resultadoArea.setEditable(false);
    resultadoArea.setLineWrap(true);
    resultadoArea.setWrapStyleWord(true);
    resultadoArea.setFont(new Font("Arial", Font.PLAIN, 16));
    resultadoArea.setBounds(200, 80, 450, 120);
    panel.add(resultadoArea);

    JButton botonRecolectar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Nuevo Reciclaje", 155, 30);
    botonRecolectar.setBounds(330, 200, 180, 40);
    botonRecolectar.setEnabled(false);  // Solo se activa si hay usuario
    panel.add(botonRecolectar);

    final Usuario[] usuarioActual = {null};  // Para acceder dentro del listener


    
    botonBuscar.addActionListener(e -> {
        String correo = campoCorreo.getText().trim();
        if (!correo.isEmpty()) {
            UsuarioDAO dao = new UsuarioDAO();
            Usuario usuario = dao.obtenerUsuarioPorCorreo(correo);
            PuntajeDAO dao1 = new PuntajeDAO();
            Puntaje k = dao1.obtenerPuntajePorCorreo(correo);

            if (usuario != null) {
                usuarioActual[0] = usuario;
                idUsuario=usuario.getId();
                botonRecolectar.setEnabled(true);    
                resultadoArea.setText("ID del Usuario: " + usuario.getId() + "\n" +
                        "Nombre: " + usuario.getNombre() + " " + usuario.getApellido() + "\n" +
                        "Teléfono: " + usuario.getTelefono() + "\n" +
                        "Puntaje Total: " + k.getPuntosTotales() + "\n" +
                        "Puntos Ganados: " + k.getPuntosGanados() + "\n" +
                        "Puntos Gastados: " + k.getPuntosGastados()

                        );

            } else {
                usuarioActual[0] = null;
                botonRecolectar.setEnabled(false);
                resultadoArea.setText("No se encontró ningún usuario con ese correo.");
            }
        } else {
            resultadoArea.setText("Por favor, introduce un correo.");
        }
    });

    botonRecolectar.addActionListener(e -> {
        if (usuarioActual[0] != null) {
            new Recolectar(idpunto,idUsuario);  // Aquí podrías pasar también el ID del usuario si lo necesitas
        }
    });

    return panel;
}




///////////////////Pestaña Mqateriales
private JPanel crearPanelMateriales() {
    JPanel panel = new JPanel(null);
    panel.setBackground(Color.WHITE);

    JLabel label = new JLabel("Ingrese ID del Punto:");
    label.setBounds(200, 10, 150, 25);
    panel.add(label);

    JTextField campoIdPunto = new JTextField(String.valueOf(idpunto));
    
    campoIdPunto.setBounds(350, 10, 150, 25);
    panel.add(campoIdPunto);

    JButton botonBuscarMateriales = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Ver Materiales Permitidos", 200, 30);
    botonBuscarMateriales.setBounds(280, 40, 300, 30);
    panel.add(botonBuscarMateriales);

    JTextArea materialesArea = new JTextArea();
    materialesArea.setEditable(false);
    materialesArea.setLineWrap(true);
    materialesArea.setWrapStyleWord(true);

    JScrollPane scrollMateriales = new JScrollPane(materialesArea);
    scrollMateriales.setBounds(200, 100, 500, 250);
    TitledBorder borde = BorderFactory.createTitledBorder("Materiales Permitidos en el Punto");
    borde.setTitleJustification(TitledBorder.CENTER);
    scrollMateriales.setBorder(borde);
    panel.add(scrollMateriales);

    botonBuscarMateriales.addActionListener(e -> {
        String textoId = campoIdPunto.getText().trim();
        if (!textoId.matches("\\d+")) {
            materialesArea.setText("Ingrese un ID de punto válido.");
            return;
        }

        int idPunto = Integer.parseInt(textoId);

        PuntoRecoleccionDAO prDAO = new PuntoRecoleccionDAO();
        List<String> materiales = prDAO.obtenerMaterialesPorPunto(idPunto);

        if (materiales != null && !materiales.isEmpty()) {
            StringBuilder sb = new StringBuilder("Materiales permitidos en el Punto ID " + idPunto + ":\n\n");
            for (String mat : materiales) {
                sb.append("- ").append(mat).append("\n");
            }
            materialesArea.setText(sb.toString());
        } else {
            materialesArea.setText("No se encontraron materiales permitidos para este punto.");
        }
    });

    return panel;
}

    
    
}
