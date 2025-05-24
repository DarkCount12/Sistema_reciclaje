package Frontend.PopUp;

import Backend.DAOs.PuntajeDAO;
import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.Puntaje;
import Backend.Modelos.Usuario;
import Backend.Servicios.Cache;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;

import javax.swing.*;
import java.awt.*;

public class InformacionUsuario extends JPanel {

    public InformacionUsuario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(Color.decode(Colores.WHITE));

        JLabel titulo = new JLabel("Información del Usuario");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 15)));

        String cache = Cache.leerCache();
        if (cache == null) {
            JOptionPane.showMessageDialog(this, "No se ha encontrado un usuario activo.");
            return;
        }
        String correoUsuario = cache.split(":")[1];

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(correoUsuario);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario.");
            return;
        }

        PuntajeDAO puntajeDAO = new PuntajeDAO();
        Puntaje puntaje = puntajeDAO.obtenerPuntajePorCorreo(correoUsuario);

        String puntajeTexto = (puntaje != null && puntaje.getPuntosTotales() != null)
                ? puntaje.getPuntosTotales().toString()
                : "0";

        add(crearCampoLabel("Puntaje Recaudado:", puntajeTexto));
        add(crearCampoLabel("Nombre:", usuario.nombre));
        add(crearCampoLabel("Apellido:", usuario.apellido));
        add(crearCampoLabel("Correo:", usuario.correo));
        add(crearCampoLabel("Contraseña:", usuario.contrasena));
        add(crearCampoLabel("Teléfono:", usuario.telefono));

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnHola = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Ver Impacto Ambiental", 355, 30);
        btnHola.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnHola.addActionListener(e -> VisualizadorPanel.mostrarPanel(new InfoImpactoAmbiental(), 0, 0));
        add(btnHola);
    }

    private JPanel crearCampoLabel(String etiqueta, String valor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel labelEtiqueta = new JLabel(etiqueta);
        labelEtiqueta.setFont(new Font("Arial", Font.BOLD, 14));
        labelEtiqueta.setPreferredSize(new Dimension(170, 30));
        JLabel labelValor = new JLabel(valor);
        labelValor.setFont(new Font("Arial", Font.PLAIN, 14));
        panel.add(labelEtiqueta, BorderLayout.WEST);
        panel.add(labelValor, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
}
