package Frontend.PopUp;

import Backend.DAOs.PuntajeDAO;
import Backend.DAOs.TipoMaterial2DAO;
import Backend.Utils.Colores;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class OtrosReportes extends JPanel {


    private JPanel contenido;

    public OtrosReportes() {
        setLayout(new BorderLayout());
        setBackground(Color.decode(Colores.WHITE));

        // Panel superior (título + botón)
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.decode(Colores.WHITE));

        JLabel titulo = new JLabel("Reporte de Puntos de Reciclaje por Tipo de Material", JLabel.LEFT);
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        titulo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.addActionListener(e -> recargarReportes()); // Acción de actualizar

        headerPanel.add(titulo, BorderLayout.WEST);
        headerPanel.add(btnActualizar, BorderLayout.EAST);

        // Panel que contendrá los reportes con scroll
        contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setBackground(Color.decode(Colores.WHITE));

        // Cargar los reportes iniciales
        cargarReportes();

        JScrollPane scrollPane = new JScrollPane(contenido);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());

        add(headerPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    

    private void cargarReportes() {
        // Agregar reportes al contenido
        JPanel reporte1 = crearPanelReporteFiltrable("Puntos generados por tipo de material reciclado");
        contenido.add(reporte1);

        JPanel reporte2 = crearPanelReporte("Materiales con mayor asignación de puntos", 2);
        contenido.add(reporte2);

        JPanel reporte3 = crearPanelReporteCanjes("Reporte de Canjes y Beneficios");
        contenido.add(reporte3);

        JPanel reporte4 = crearPanelReporteNuevosRegistros("Reporte de Nuevos Registros de Reciclaje");
        contenido.add(reporte4);

        // Refrescar visualmente el panel
        contenido.revalidate();
        contenido.repaint();
    }

    private void recargarReportes() {
        contenido.removeAll(); // Eliminar todos los reportes actuales
        cargarReportes();      // Volver a cargar todos los reportes
    }











    // Panel con filtro (combo)
    private JPanel crearPanelReporteFiltrable(String tituloReporte) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(tituloReporte));
        panel.setBackground(Color.decode(Colores.WHITE));

        JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filtroPanel.setBackground(Color.decode(Colores.WHITE));
        JLabel lblFiltro = new JLabel("Seleccione el material: ");
        JComboBox<String> comboMateriales = new JComboBox<>();

        JTable tabla = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tabla);

        // Cargar materiales en el combo
        TipoMaterial2DAO dao = new TipoMaterial2DAO();
        List<String> materiales = dao.obtenerNombresTipoMaterial();

        for (String material : materiales) {
            comboMateriales.addItem(material);
        }

        // Al cambiar selección, actualizar la tabla
        comboMateriales.addActionListener(e -> {
            String materialSeleccionado = (String) comboMateriales.getSelectedItem();
            cargarPuntosPorMaterial(tabla, materialSeleccionado);
        });

        // Cargar tabla con el primer material al iniciar
        if (!materiales.isEmpty()) {
            cargarPuntosPorMaterial(tabla, materiales.get(0));
        }

        filtroPanel.add(lblFiltro);
        filtroPanel.add(comboMateriales);

        panel.add(filtroPanel, BorderLayout.NORTH);
        panel.add(scrollTabla, BorderLayout.CENTER);

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 350));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }



    // Panel simple
    private JPanel crearPanelReporte(String tituloReporte, int tipoReporte) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(tituloReporte));
        panel.setBackground(Color.decode(Colores.WHITE));

        JTable tabla = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tabla);
        panel.add(scrollTabla, BorderLayout.CENTER);

        if (tipoReporte == 2) {
            cargarMaterialesConMayorAsignacion(tabla);
        }

        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }



    private void cargarPuntosPorMaterial(JTable tabla, String material) {
        PuntajeDAO dao = new PuntajeDAO();
        List<Object[]> datos = dao.obtenerPuntosPorMaterial(material);

        DefaultTableModel model = new DefaultTableModel(new String[]{"Usuario", "Puntos Ganados"}, 0);
        for (Object[] fila : datos) {
            model.addRow(fila);
        }
        tabla.setModel(model);
    }



    private void cargarMaterialesConMayorAsignacion(JTable tabla) {
        PuntajeDAO dao = new PuntajeDAO();
        List<Object[]> datos = dao.obtenerMaterialesConMayorAsignacion();

        DefaultTableModel model = new DefaultTableModel(new String[]{"Material", "Total Kg"}, 0);
        for (Object[] fila : datos) {
            model.addRow(fila);
        }
        tabla.setModel(model);
    }

    

    private JPanel crearPanelReporteCanjes(String tituloReporte) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(tituloReporte));
        panel.setBackground(Color.decode(Colores.WHITE));

        JTable tabla = new JTable();
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setPreferredSize(new Dimension(500, 250));

        cargarReporteCanjes(tabla);

        panel.add(scrollTabla, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);

        return panel;
    }

        private void cargarReporteCanjes(JTable tabla) {
            PuntajeDAO dao = new PuntajeDAO(); // Si tienes otro DAO para Recompensas, usa ese.
            List<Object[]> datos = dao.obtenerReporteCanjes();

            DefaultTableModel model = new DefaultTableModel(new String[]{"Tipo de Recompensa",  "Cantidad Canjeos","Puntos Canjeados"}, 0);
            for (Object[] fila : datos) {
                model.addRow(fila);
            }
            tabla.setModel(model);
        }




private JPanel crearPanelReporteNuevosRegistros(String tituloReporte) {
    JPanel panel = new JPanel(new BorderLayout());
    panel.setBorder(BorderFactory.createTitledBorder(tituloReporte));
    panel.setBackground(Color.decode(Colores.WHITE));

    JPanel filtroPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    filtroPanel.setBackground(Color.decode(Colores.WHITE));

    JLabel lblDesde = new JLabel("Desde:");
    JSpinner inicio = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(inicio, "yyyy-MM-dd");
    inicio.setEditor(editorInicio);

    JLabel lblHasta = new JLabel("Hasta:");
    JSpinner fin = new JSpinner(new SpinnerDateModel());
    JSpinner.DateEditor editorFin = new JSpinner.DateEditor(fin, "yyyy-MM-dd");
    fin.setEditor(editorFin);

    JLabel lblRol = new JLabel("Rol:");
    JComboBox<String> comboRoles = new JComboBox<>(new String[]{"Todos", "Usuario", "Administrador", "Reciclador"});

    JButton btnBuscar = new JButton("Buscar");

    JTable tabla = new JTable();
    JScrollPane scrollTabla = new JScrollPane(tabla);

   
    JLabel contadorUsuarios = new JLabel("Registros encontrados: 0");
    JLabel contadorKg = new JLabel("Kg reciclados totales: 0.0");
    JLabel contadorPuntos = new JLabel("Puntos ganados totales: 0");

    btnBuscar.addActionListener(e -> {
        java.util.Date fechaInicio = (java.util.Date) inicio.getValue();
        java.util.Date fechaFin = (java.util.Date) fin.getValue();

        java.sql.Date sqlFechaInicio = new java.sql.Date(fechaInicio.getTime());
        java.sql.Date sqlFechaFin = new java.sql.Date(fechaFin.getTime());

        int idRol = switch (comboRoles.getSelectedIndex()) {
            case 1 -> 3; // Usuario
            case 2 -> 1; // Administrador
            case 3 -> 2; // Reciclador
            default -> 0; // Todos
        };

        cargarUsuariosRegistrados(tabla, sqlFechaInicio.toString(), sqlFechaFin.toString(), idRol, contadorUsuarios, contadorKg, contadorPuntos);
    });

    filtroPanel.add(lblDesde);
    filtroPanel.add(inicio);
    filtroPanel.add(lblHasta);
    filtroPanel.add(fin);
    filtroPanel.add(lblRol);
    filtroPanel.add(comboRoles);
    filtroPanel.add(btnBuscar);

    //  Panel para los contadores abajo
    JPanel contadoresPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    contadoresPanel.add(contadorUsuarios);
    contadoresPanel.add(new JLabel(" | ")); // Separador visual
    contadoresPanel.add(contadorKg);
    contadoresPanel.add(new JLabel(" | ")); // Separador visual
    contadoresPanel.add(contadorPuntos);

    panel.add(filtroPanel, BorderLayout.NORTH);
    panel.add(scrollTabla, BorderLayout.CENTER);
    panel.add(contadoresPanel, BorderLayout.SOUTH);

    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 400));
    panel.setAlignmentX(Component.CENTER_ALIGNMENT);

    return panel;
}


private void cargarUsuariosRegistrados(JTable tabla, String fechaDesde, String fechaHasta, int idRol, JLabel contadorUsuarios, JLabel contadorKg, JLabel contadorPuntos) {
    PuntajeDAO dao = new PuntajeDAO();
    List<Object[]> datos = dao.obtenerUsuariosRegistradosPorFechaYRol(fechaDesde, fechaHasta, idRol);

    DefaultTableModel model = new DefaultTableModel(new String[]{"Usuario", "Kg Reciclados", "Puntos Ganados"}, 0);

    double totalKg = 0.0;
    int totalPuntos = 0;

    for (Object[] fila : datos) {
        String usuario = (String) fila[0];
        double kg = (double) fila[1];
        int puntos = (int) fila[2];

        totalKg += kg;
        totalPuntos += puntos;

        model.addRow(new Object[]{usuario, kg, puntos});
    }

    tabla.setModel(model);

    contadorUsuarios.setText("Registros encontrados: " + datos.size());
    contadorKg.setText("Kg reciclados totales: " + totalKg);
    contadorPuntos.setText("Puntos ganados totales: " + totalPuntos);
}




}
