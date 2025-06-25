package Frontend.PopUp;

import Backend.DAOs.PuntajeDAO;
import Backend.Utils.Colores;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ranking extends JPanel {

    private JComboBox<String> comboFiltro;
    private JComboBox<String> comboTop;
    private JTable tablaRanking;
    private JLabel labelPromedio;
    private PuntajeDAO puntajeDAO;

    public Ranking() {
        setLayout(new BorderLayout());
        setBackground(Color.decode(Colores.WHITE));

        puntajeDAO = new PuntajeDAO();

        // Título
        JLabel titulo = new JLabel("Ranking de Usuarios", JLabel.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(titulo, BorderLayout.NORTH);

        // Panel superior: Filtro + Tabla
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BorderLayout());
        panelSuperior.setBackground(Color.decode(Colores.WHITE));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        // ComboBox de filtros
        JPanel panelFiltro = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelFiltro.setBackground(Color.decode(Colores.WHITE));

        JLabel labelFiltro = new JLabel("Ordenar por:");
        comboFiltro = new JComboBox<>(new String[]{"Puntos Ganados", "Puntos Gastados", "Puntos Actuales"});
        comboFiltro.addActionListener(e -> actualizarTabla());

        JLabel labelTop = new JLabel("Top:");
        comboTop = new JComboBox<>(new String[]{"Top 10", "Top 100", "Todos"});
        comboTop.addActionListener(e -> actualizarTabla());

        panelFiltro.add(labelFiltro);
        panelFiltro.add(comboFiltro);
        panelFiltro.add(Box.createHorizontalStrut(20)); // Espacio entre filtros
        panelFiltro.add(labelTop);
        panelFiltro.add(comboTop);

        // Tabla con scroll
        tablaRanking = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tablaRanking);
        scrollTabla.setPreferredSize(new Dimension(500, 300));

        // Etiqueta para promedio
        labelPromedio = new JLabel("Promedio: ");
        labelPromedio.setFont(new Font("Arial", Font.PLAIN, 16));
        labelPromedio.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Estructura
        panelSuperior.add(panelFiltro, BorderLayout.NORTH);
        panelSuperior.add(scrollTabla, BorderLayout.CENTER);
        panelSuperior.add(labelPromedio, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.CENTER);

        // Cargar datos iniciales
        actualizarTabla();
    }

    private void actualizarTabla() {
        String filtroSeleccionado = comboFiltro.getSelectedItem().toString();
        String columnaFiltro = "";

        switch (filtroSeleccionado) {
            case "Puntos Ganados" -> columnaFiltro = "puntos_ganados";
            case "Puntos Gastados" -> columnaFiltro = "puntos_gastados";
            case "Puntos Actuales" -> columnaFiltro = "puntos_totales";
        }

        String topSeleccionado = comboTop.getSelectedItem().toString();
        int limite = switch (topSeleccionado) {
            case "Top 10" -> 10;
            case "Top 100" -> 100;
            default -> 0; // 0 significa "todos"
        };

        List<Object[]> datos = puntajeDAO.obtenerRankingPorFiltro(columnaFiltro, limite);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Usuario", "Puntos"}, 0);
        for (Object[] fila : datos) {
            model.addRow(fila);
        }

        tablaRanking.setModel(model);

        // Actualizar promedio
        int promedio = puntajeDAO.obtenerPromedioPuntos(columnaFiltro);
        labelPromedio.setText("Promedio de " + filtroSeleccionado + ": " + promedio);
    }
}
