package Frontend.PopUp;

import Backend.ConexionBD;
import Backend.DAOs.ImpactoAmbientalDAO;
import Backend.DAOs.PuntoReciclajeDAO;
import Backend.Modelos.ImpactoAmbiental;
import Backend.Modelos.PuntoReciclaje;
import Backend.Servicios.Cache;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Backend.Utils.VisualizadorPanel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import javax.swing.*;

public class InfoImpactoAmbiental extends JPanel {

    public InfoImpactoAmbiental() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        setBackground(Color.decode(Colores.WHITE));

        JLabel titulo = new JLabel("Impacto Ambiental");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 15)));

        String correoUsuario = Cache.obtenerCorreo();
        if (correoUsuario == null) {
            JOptionPane.showMessageDialog(this, "No se ha encontrado un usuario activo.");
            return;
        }

        try {
            ImpactoAmbientalDAO impactoDAO = new ImpactoAmbientalDAO(ConexionBD.obtenerConexion());
            ImpactoAmbiental impacto = impactoDAO.obtenerPorCorreo(correoUsuario);

            PuntoReciclajeDAO puntoDAO = new PuntoReciclajeDAO();
            List<PuntoReciclaje> puntos = puntoDAO.obtenerPuntosPorCorreo(correoUsuario);

            if (impacto != null && !puntos.isEmpty()) {
                PuntoReciclaje punto = puntos.get(0);

                add(crearCampoLabel("Nombre del Punto:", punto.getNombre()));
                add(crearCampoLabel("Ubicación:", punto.getUbicacion()));
                add(crearCampoLabel("Horario:", punto.getHorario()));
                add(crearCampoLabel("Kg Reciclados:", impacto.getKgReciclados().toString()));
                add(crearCampoLabel("CO2 Reducido:", impacto.getCo2Reducidos().toString()));
            } else {
                add(crearCampoLabel("Nombre del Punto:", "Sin datos"));
                add(crearCampoLabel("Ubicación:", "Sin datos"));
                add(crearCampoLabel("Horario:", "Sin datos"));
                add(crearCampoLabel("Kg Reciclados:", "0"));
                add(crearCampoLabel("CO2 Reducido:", "0"));
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al obtener datos del impacto ambiental.");
            add(crearCampoLabel("Error:", "No se pudieron cargar los datos"));
        }

        add(Box.createRigidArea(new Dimension(0, 10)));

        JButton btnVolver = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Volver", 355, 30);
        btnVolver.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnVolver.addActionListener(e -> VisualizadorPanel.mostrarPanel(new InformacionUsuario(), 0, 0));
        add(btnVolver);
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
