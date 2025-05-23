package Frontend.Page;
import Backend.DAOs.PuntoRecoleccionDAO;
import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.PuntoRecoleccion;
import Backend.Modelos.Usuario;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Frontend.PopUp.Recolectar;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class PaginaRecolector {

    public static JFrame ventanaRecolector;

public static void main(String[] args) {
    new PaginaRecolector();
}



    public PaginaRecolector() {
        ventanaRecolector = new JFrame("Panel del Recolector");
        inicializar();
    }

    private void inicializar() {
        ventanaRecolector.setSize(900, 550);
        ventanaRecolector.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventanaRecolector.setLocationRelativeTo(null);
        ventanaRecolector.setResizable(false);

        JPanel principal = new JPanel();
        principal.setLayout(new BorderLayout());

        // Encabezado azul
        JPanel encabezado = new JPanel();
        encabezado.setBackground(Color.decode(Colores.DARK_BLUE));
        encabezado.setPreferredSize(new Dimension(900, 70));
        encabezado.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        JLabel titulo = new JLabel("Panel del Recolector");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        encabezado.add(titulo);
        principal.add(encabezado, BorderLayout.NORTH);

        // Panel principal blanco con opciones
        JPanel cuerpo = new JPanel();
        cuerpo.setBackground(Color.WHITE);
        cuerpo.setLayout(null);

        JLabel buscarLabel = new JLabel("Buscar ID por correo:");
        buscarLabel.setBounds(50, 50, 200, 30);
        cuerpo.add(buscarLabel);

        JTextField campoCorreo = new JTextField();
        campoCorreo.setBounds(200, 50, 300, 30);
        cuerpo.add(campoCorreo);

        JButton botonBuscar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Buscar", 155, 30);
        botonBuscar.setBounds(520, 50, 100, 30); 
        cuerpo.add(botonBuscar);

        JButton botonrRecolectar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Nuevo Reciclaje", 85, 30);
        botonrRecolectar.setBounds(600, 200, 200, 30); 
        cuerpo.add(botonrRecolectar);


        JTextArea resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        resultadoArea.setBounds(200, 100, 420, 60);
        resultadoArea.setLineWrap(true);
        resultadoArea.setWrapStyleWord(true);
        cuerpo.add(resultadoArea);
        

        botonBuscar.addActionListener(e -> {
            String correo = campoCorreo.getText().trim();
            UsuarioDAO a =new UsuarioDAO();
            if (!correo.isEmpty()) {
                Usuario usuario = a.obtenerUsuarioPorCorreo(correo);

                if (usuario != null ) {
                    resultadoArea.setText("ID del recolector: " + usuario.getId() + "   Nombre: "+usuario.getNombre()+" "+usuario.getApellido());

                } else {
                    resultadoArea.setText("No se encontró ningún Usuario con ese correo.");
                }
            } else {
                resultadoArea.setText("Por favor, introduce un correo.");
            }
        });


       botonrRecolectar.addActionListener(e -> {
        new Recolectar();
        });


                JButton botonBuscarPuntos = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Ver Puntos de Recolección", 200, 30);
                botonBuscarPuntos.setBounds(150, 160, 300, 30);
                cuerpo.add(botonBuscarPuntos);

                // JTextArea dentro de JScrollPane
                JTextArea puntosArea = new JTextArea();
                puntosArea.setEditable(false);
                puntosArea.setLineWrap(true);
                puntosArea.setWrapStyleWord(true);

                JScrollPane scrollPuntos = new JScrollPane(puntosArea);
                scrollPuntos.setBounds(100, 200, 420, 169); // ajusta el tamaño según tu diseño
                scrollPuntos.setBorder(BorderFactory.createTitledBorder("Puntos de Recolección"));
                TitledBorder borde = BorderFactory.createTitledBorder("Puntos de Recolección");
                borde.setTitleJustification(TitledBorder.CENTER);  // Centra el título
                scrollPuntos.setBorder(borde);

                scrollPuntos.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

                cuerpo.add(scrollPuntos);

                // Acción del botón
                botonBuscarPuntos.addActionListener(e -> {
                    PuntoRecoleccionDAO prDAO = new PuntoRecoleccionDAO();
                    List<PuntoRecoleccion> puntos = prDAO.obtenerTodos();

                    if (puntos != null && !puntos.isEmpty()) {
                        StringBuilder sb = new StringBuilder();
                        for (PuntoRecoleccion punto : puntos) {
                            sb.append("ID: ").append(punto.getId())
                            .append(" - ").append(punto.getNombre())
                            .append(" (").append(punto.getDireccion()).append(")\n");
                        }
                        puntosArea.setText(sb.toString());
                    } else {
                        puntosArea.setText("No hay puntos de recolección disponibles.");
                    }
                });





        principal.add(cuerpo, BorderLayout.CENTER);

        // Footer opcional
        JPanel pie = new JPanel();
        pie.setBackground(Color.decode(Colores.LIGHT_BLUE));
        pie.setPreferredSize(new Dimension(900, 30));
        principal.add(pie, BorderLayout.SOUTH);

        ventanaRecolector.add(principal);
        ventanaRecolector.setVisible(true);
    }
}
