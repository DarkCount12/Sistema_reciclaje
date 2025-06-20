package Frontend.Page.Secciones;

import Backend.Servicios.ReportesServicio;
import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Reportes {
    private static JLabel lblValorTotalIzquierda;
    private static JLabel lblValorTotalDerecha;
    private static JLabel lblValorTotalCO2;
    private static JLabel lblValorTotalKgUsuario; 
    private static JLabel lblValorTotalPuntosUsuario; 
    private static JLabel lblValorTotalPuntosCanjeados; 
    private static ReportesServicio servicioReportes;

    private static JCheckBox chkMaterialReciclado;
    private static JCheckBox chkPuntosGenerados;
    private static JComboBox<String> cmbTipoMaterial1;
    private static JComboBox<String> cmbTipoMaterial2;
    private static JTable tablaMaterialReciclado;
    private static JTable tablaPuntosGenerados;
    private static JScrollPane scrollMaterial;
    private static JScrollPane scrollPuntos;

    private static JCheckBox chkImpactoAmbiental;
    private static JComboBox<String> cmbTipoMaterial3;
    private static JTable tablaCO2Reducido;
    private static JScrollPane scrollCO2;

    private static JCheckBox chkActividadUsuarios;
    private static JComboBox<String> cmbUsuarios;
    private static JTable tablaActividadUsuarios;
    private static JScrollPane scrollUsuarios;

    private static JCheckBox chkRecompensasCanjeadas;
    private static JComboBox<String> cmbTipoRecompensa;
    private static JTable tablaRecompensas;
    private static JScrollPane scrollRecompensas;

    public static JPanel crearPanelReportes() {
        servicioReportes = new ReportesServicio();
        JPanel mainPanel = new JPanel(new BorderLayout());

        JLabel lblTitulo = new JLabel("Reportes Administrativos", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        mainPanel.add(lblTitulo, BorderLayout.NORTH);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        contentPanel.add(crearSeccionActividadReciclaje());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionImpactoAmbiental());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionActividadUsuarios());
        contentPanel.add(Box.createVerticalStrut(30));
        contentPanel.add(crearSeccionRecompensasCanjeadas());

        JScrollPane mainScrollPane = new JScrollPane(contentPanel);
        mainScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(mainScrollPane, BorderLayout.CENTER);

        cargarDatosIniciales();

        return mainPanel;
    }

    private static JPanel crearSeccionActividadReciclaje() {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel lblSubtitulo = new JLabel("Reporte de Actividad de Reciclaje");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        JPanel opcionesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel izquierdaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkMaterialReciclado = new JCheckBox("Material Reciclado por Tipo");
        cmbTipoMaterial1 = new JComboBox<>();
        cmbTipoMaterial1.setEnabled(false);
        izquierdaPanel.add(chkMaterialReciclado);
        izquierdaPanel.add(cmbTipoMaterial1);

        JPanel derechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkPuntosGenerados = new JCheckBox("Puntos Generados por Tipo");
        cmbTipoMaterial2 = new JComboBox<>();
        cmbTipoMaterial2.setEnabled(false);
        derechaPanel.add(chkPuntosGenerados);
        derechaPanel.add(cmbTipoMaterial2);
        opcionesPanel.add(izquierdaPanel);
        opcionesPanel.add(derechaPanel);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        JPanel centralPanel = new JPanel(new BorderLayout());
        JPanel tablasPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        tablaMaterialReciclado = new JTable();
        scrollMaterial = new JScrollPane(tablaMaterialReciclado);
        scrollMaterial.setPreferredSize(new Dimension(400, 200)); // Cambiado de 500 a 400
        scrollMaterial.setVisible(false);
        tablaPuntosGenerados = new JTable();
        scrollPuntos = new JScrollPane(tablaPuntosGenerados);
        scrollPuntos.setPreferredSize(new Dimension(400, 200)); // Cambiado de 500 a 400
        scrollPuntos.setVisible(false);
        tablasPanel.add(scrollMaterial);
        tablasPanel.add(scrollPuntos);
        centralPanel.add(tablasPanel, BorderLayout.CENTER);

        JPanel totalesPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalIzquierdaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalIzquierda = new JLabel("Total Izquierda:");
        lblTotalIzquierda.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalIzquierda = new JLabel("0 kg");
        lblValorTotalIzquierda.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalIzquierda.setForeground(Color.BLACK);
        totalIzquierdaPanel.add(lblTotalIzquierda);
        totalIzquierdaPanel.add(lblValorTotalIzquierda);

        JPanel totalDerechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalDerecha = new JLabel("Total Derecha:");
        lblTotalDerecha.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalDerecha = new JLabel("0 pts");
        lblValorTotalDerecha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalDerecha.setForeground(Color.BLACK);
        totalDerechaPanel.add(lblTotalDerecha);
        totalDerechaPanel.add(lblValorTotalDerecha);
        totalesPanel.add(totalIzquierdaPanel);
        totalesPanel.add(totalDerechaPanel);
        centralPanel.add(totalesPanel, BorderLayout.SOUTH);
        panel.add(centralPanel, BorderLayout.SOUTH);

        chkMaterialReciclado.addActionListener(e -> {
            boolean selected = chkMaterialReciclado.isSelected();
            cmbTipoMaterial1.setEnabled(selected);
            scrollMaterial.setVisible(selected);
            if (selected && cmbTipoMaterial1.getSelectedItem() != null) {
                cargarReporteMaterialReciclado();
                actualizarTotalIzquierda();
            } else {
                lblValorTotalIzquierda.setText("0 kg");
            }
            panel.revalidate();
            panel.repaint();
        });

        chkPuntosGenerados.addActionListener(e -> {
            boolean selected = chkPuntosGenerados.isSelected();
            cmbTipoMaterial2.setEnabled(selected);
            scrollPuntos.setVisible(selected);
            if (selected && cmbTipoMaterial2.getSelectedItem() != null) {
                cargarReportePuntosGenerados();
                actualizarTotalDerecha();
            } else {
                lblValorTotalDerecha.setText("0 pts");
            }
            panel.revalidate();
            panel.repaint();
        });

        cmbTipoMaterial1.addActionListener(e -> {
            if (chkMaterialReciclado.isSelected()) {
                cargarReporteMaterialReciclado();
                actualizarTotalIzquierda();
            }
        });

        cmbTipoMaterial2.addActionListener(e -> {
            if (chkPuntosGenerados.isSelected()) {
                cargarReportePuntosGenerados();
                actualizarTotalDerecha();
            }
        });

        return panel;
    }

    private static JPanel crearSeccionImpactoAmbiental() {
        JPanel panel = new JPanel(new BorderLayout());

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Reporte de Impacto Ambiental");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        // Panel de opciones
        JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        chkImpactoAmbiental = new JCheckBox("CO2 Reducido por Tipo de Material");
        cmbTipoMaterial3 = new JComboBox<>();
        cmbTipoMaterial3.setEnabled(false);

        opcionesPanel.add(chkImpactoAmbiental);
        opcionesPanel.add(cmbTipoMaterial3);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        // Panel central que contendrá la tabla y el total
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Tabla CO2
        tablaCO2Reducido = new JTable();
        scrollCO2 = new JScrollPane(tablaCO2Reducido);
        scrollCO2.setPreferredSize(new Dimension(1000, 200));
        scrollCO2.setVisible(false);
        centralPanel.add(scrollCO2, BorderLayout.CENTER);

        // Panel de total CO2
        JPanel totalCO2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalCO2Panel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel lblTotalCO2 = new JLabel("Total CO2 Reducido:");
        lblTotalCO2.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalCO2 = new JLabel("0 kg");
        lblValorTotalCO2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalCO2.setForeground(Color.BLACK);
        totalCO2Panel.add(lblTotalCO2);
        totalCO2Panel.add(lblValorTotalCO2);
        centralPanel.add(totalCO2Panel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        // Eventos
        chkImpactoAmbiental.addActionListener(e -> {
            boolean selected = chkImpactoAmbiental.isSelected();
            cmbTipoMaterial3.setEnabled(selected);
            scrollCO2.setVisible(selected);
            if (selected && cmbTipoMaterial3.getSelectedItem() != null) {
                cargarReporteCO2Reducido();
                actualizarTotalCO2();
            } else {
                lblValorTotalCO2.setText("0 kg");
            }
            panel.revalidate();
            panel.repaint();
        });

        cmbTipoMaterial3.addActionListener(e -> {
            if (chkImpactoAmbiental.isSelected()) {
                cargarReporteCO2Reducido();
                actualizarTotalCO2();
            }
        });

        return panel;
    }

    private static JPanel crearSeccionActividadUsuarios() {
        JPanel panel = new JPanel(new BorderLayout());

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Reporte de Actividad de Usuarios");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        // Panel de opciones
        JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        chkActividadUsuarios = new JCheckBox("Seleccionar Usuario:");
        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setEnabled(false);
        cmbUsuarios.setPreferredSize(new Dimension(200, 25));

        opcionesPanel.add(chkActividadUsuarios);
        opcionesPanel.add(cmbUsuarios);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        // Panel central que contendrá la tabla y los totales
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Tabla Usuarios
        tablaActividadUsuarios = new JTable();
        scrollUsuarios = new JScrollPane(tablaActividadUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(1000, 200));
        scrollUsuarios.setVisible(false);
        centralPanel.add(scrollUsuarios, BorderLayout.CENTER);

        // Panel de totales para usuarios (kg y puntos)
        JPanel totalesUsuarioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalesUsuarioPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // Total Kg Usuario
        JPanel totalKgUsuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalKgUsuario = new JLabel("Total Kg Reciclados:");
        lblTotalKgUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalKgUsuario = new JLabel("0 kg");
        lblValorTotalKgUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalKgUsuario.setForeground(Color.BLACK);
        totalKgUsuarioPanel.add(lblTotalKgUsuario);
        totalKgUsuarioPanel.add(lblValorTotalKgUsuario);

        // Total Puntos Usuario
        JPanel totalPuntosUsuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalPuntosUsuario = new JLabel("Total Puntos Obtenidos:");
        lblTotalPuntosUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosUsuario = new JLabel("0 pts");
        lblValorTotalPuntosUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosUsuario.setForeground(Color.BLACK);
        totalPuntosUsuarioPanel.add(lblTotalPuntosUsuario);
        totalPuntosUsuarioPanel.add(lblValorTotalPuntosUsuario);

        totalesUsuarioPanel.add(totalKgUsuarioPanel);
        totalesUsuarioPanel.add(totalPuntosUsuarioPanel);
        centralPanel.add(totalesUsuarioPanel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        // Eventos
        chkActividadUsuarios.addActionListener(e -> {
            boolean selected = chkActividadUsuarios.isSelected();
            cmbUsuarios.setEnabled(selected);
            scrollUsuarios.setVisible(selected);
            if (selected && cmbUsuarios.getSelectedItem() != null) {
                cargarReporteActividadUsuarios();
                actualizarTotalesUsuario();
            } else {
                lblValorTotalKgUsuario.setText("0 kg");
                lblValorTotalPuntosUsuario.setText("0 pts");
            }
            panel.revalidate();
            panel.repaint();
        });

        cmbUsuarios.addActionListener(e -> {
            if (chkActividadUsuarios.isSelected()) {
                cargarReporteActividadUsuarios();
                actualizarTotalesUsuario();
            }
        });

        return panel;
    }

    private static JPanel crearSeccionRecompensasCanjeadas() {
        JPanel panel = new JPanel(new BorderLayout());

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Reporte de Recompensas Canjeadas");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        // Panel de opciones
        JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        chkRecompensasCanjeadas = new JCheckBox("Seleccionar Tipo de Recompensa:");
        cmbTipoRecompensa = new JComboBox<>();
        cmbTipoRecompensa.setEnabled(false);
        cmbTipoRecompensa.setPreferredSize(new Dimension(200, 25));

        opcionesPanel.add(chkRecompensasCanjeadas);
        opcionesPanel.add(cmbTipoRecompensa);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        // Panel central que contendrá la tabla y el total
        JPanel centralPanel = new JPanel(new BorderLayout());

        // Tabla Recompensas
        tablaRecompensas = new JTable();
        scrollRecompensas = new JScrollPane(tablaRecompensas);
        scrollRecompensas.setPreferredSize(new Dimension(1000, 200));
        scrollRecompensas.setVisible(false);
        centralPanel.add(scrollRecompensas, BorderLayout.CENTER);

        // Panel de total puntos canjeados
        JPanel totalCanjeadosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalCanjeadosPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel lblTotalCanjeados = new JLabel("Total Puntos Utilizados:");
        lblTotalCanjeados.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosCanjeados = new JLabel("0 pts");
        lblValorTotalPuntosCanjeados.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosCanjeados.setForeground(Color.BLACK);
        totalCanjeadosPanel.add(lblTotalCanjeados);
        totalCanjeadosPanel.add(lblValorTotalPuntosCanjeados);
        centralPanel.add(totalCanjeadosPanel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        // Eventos
        chkRecompensasCanjeadas.addActionListener(e -> {
            boolean selected = chkRecompensasCanjeadas.isSelected();
            cmbTipoRecompensa.setEnabled(selected);
            scrollRecompensas.setVisible(selected);
            if (selected && cmbTipoRecompensa.getSelectedItem() != null) {
                cargarReporteRecompensasCanjeadas();
                actualizarTotalPuntosCanjeados();
            } else {
                lblValorTotalPuntosCanjeados.setText("0 pts");
            }
            panel.revalidate();
            panel.repaint();
        });

        cmbTipoRecompensa.addActionListener(e -> {
            if (chkRecompensasCanjeadas.isSelected()) {
                cargarReporteRecompensasCanjeadas();
                actualizarTotalPuntosCanjeados();
            }
        });

        return panel;
    }

    private static void cargarDatosIniciales() {
        // Cargar tipos de material
        List<String> tiposMaterial = servicioReportes.obtenerTiposMaterial();
        for (String tipo : tiposMaterial) {
            cmbTipoMaterial1.addItem(tipo);
            cmbTipoMaterial2.addItem(tipo);
            cmbTipoMaterial3.addItem(tipo);
        }

        // Cargar usuarios
        List<String> usuarios = servicioReportes.obtenerNombresUsuarios();
        for (String usuario : usuarios) {
            cmbUsuarios.addItem(usuario);
        }

        // Cargar tipos de recompensa
        List<String> recompensas = servicioReportes.obtenerTiposRecompensa();
        for (String recompensa : recompensas) {
            cmbTipoRecompensa.addItem(recompensa);
        }
    }

    private static void cargarReporteMaterialReciclado() {
        String tipoSeleccionado = (String) cmbTipoMaterial1.getSelectedItem();
        if (tipoSeleccionado != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerMaterialRecicladoPorTipo(tipoSeleccionado);

            String[] columnas = {"Tipo Material", "Nombre Usuario", "Total Kg Reciclado", "Fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        fila.get("tipo_material"),
                        fila.get("nombre_usuario"),
                        fila.get("total_kg_reciclado"),
                        fila.get("fecha")
                };
                model.addRow(rowData);
            }

            tablaMaterialReciclado.setModel(model);
            configurarAnchoColumnas(tablaMaterialReciclado);
        }
    }

    private static void cargarReportePuntosGenerados() {
        String tipoSeleccionado = (String) cmbTipoMaterial2.getSelectedItem();
        if (tipoSeleccionado != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerPuntosGeneradosPorTipo(tipoSeleccionado);

            String[] columnas = {"Tipo Material", "Nombre Usuario", "Puntos Generados", "Fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        fila.get("tipo_material"),
                        fila.get("nombre_usuario"),
                        fila.get("puntos_generados"),
                        fila.get("fecha")
                };
                model.addRow(rowData);
            }

            tablaPuntosGenerados.setModel(model);
            configurarAnchoColumnas(tablaPuntosGenerados);
        }
    }

    private static void cargarReporteCO2Reducido() {
        String tipoSeleccionado = (String) cmbTipoMaterial3.getSelectedItem();
        if (tipoSeleccionado != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerCO2ReducidoPorTipo(tipoSeleccionado);

            String[] columnas = {"Tipo Material", "Nombre Usuario", "CO2 Reducido (kg)", "Fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        fila.get("tipo_material"),
                        fila.get("nombre_usuario"),
                        fila.get("co2_reducido"),
                        fila.get("fecha")
                };
                model.addRow(rowData);
            }

            tablaCO2Reducido.setModel(model);
        }
    }

    private static void cargarReporteActividadUsuarios() {
        String usuarioSeleccionado = (String) cmbUsuarios.getSelectedItem();
        if (usuarioSeleccionado != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerActividadUsuario(usuarioSeleccionado);

            String[] columnas = {"Usuario", "Material", "Cantidad (kg)", "Puntos", "Fecha"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        fila.get("nombre_usuario"),
                        fila.get("tipo_material"),
                        fila.get("cantidad_kg"),
                        fila.get("puntos_generados"),
                        fila.get("fecha")
                };
                model.addRow(rowData);
            }

            tablaActividadUsuarios.setModel(model);
        }
    }

    private static void cargarReporteRecompensasCanjeadas() {
        String tipoSeleccionado = (String) cmbTipoRecompensa.getSelectedItem();
        if (tipoSeleccionado != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerRecompensasCanjeadas(tipoSeleccionado);

            String[] columnas = {"Usuario", "Recompensa", "Puntos Utilizados", "Fecha Canje"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        fila.get("nombre_usuario"),
                        fila.get("nombre_recompensa"),
                        fila.get("puntos_utilizados"),
                        fila.get("fecha_canje")
                };
                model.addRow(rowData);
            }

            tablaRecompensas.setModel(model);
        }
    }

    // Métodos para actualizar totales

    private static void actualizarTotalIzquierda() {
        try {
            String tipoMaterial = (String) cmbTipoMaterial1.getSelectedItem();
            if (tipoMaterial != null && !tipoMaterial.isEmpty()) {
                int total = servicioReportes.obtenerTotalKgRecicladoPorTipo(tipoMaterial);
                lblValorTotalIzquierda.setText(total + " kg");
            } else {
                lblValorTotalIzquierda.setText("0 kg");
            }
        } catch (Exception e) {
            lblValorTotalIzquierda.setText("0 kg");
            System.out.println("Error al actualizar total izquierda: " + e.getMessage());
        }
    }

    private static void actualizarTotalDerecha() {
        try {
            String tipoMaterial = (String) cmbTipoMaterial2.getSelectedItem();
            if (tipoMaterial != null && !tipoMaterial.isEmpty()) {
                int total = servicioReportes.obtenerTotalPuntosGeneradosPorTipo(tipoMaterial);
                lblValorTotalDerecha.setText(total + " pts");
            } else {
                lblValorTotalDerecha.setText("0 pts");
            }
        } catch (Exception e) {
            lblValorTotalDerecha.setText("0 pts");
            System.out.println("Error al actualizar total derecha: " + e.getMessage());
        }
    }

    private static void actualizarTotalCO2() {
        try {
            String tipoMaterial = (String) cmbTipoMaterial3.getSelectedItem();
            if (tipoMaterial != null && !tipoMaterial.isEmpty()) {
                int total = servicioReportes.obtenerTotalCO2ReducidoPorTipo(tipoMaterial);
                lblValorTotalCO2.setText(total + " kg CO2");
            } else {
                lblValorTotalCO2.setText("0 kg CO2");
            }
        } catch (Exception e) {
            lblValorTotalCO2.setText("0 kg CO2");
            System.out.println("Error al actualizar total CO2: " + e.getMessage());
        }
    }

    private static void actualizarTotalesUsuario() {
        try {
            String usuarioSeleccionado = (String) cmbUsuarios.getSelectedItem();
            if (usuarioSeleccionado != null && !usuarioSeleccionado.isEmpty()) {
                // Actualizar total de kg reciclados por el usuario
                int totalKg = servicioReportes.obtenerTotalKgRecicladoPorUsuario(usuarioSeleccionado);
                lblValorTotalKgUsuario.setText(totalKg + " kg");

                // Actualizar total de puntos obtenidos por el usuario
                int totalPuntos = servicioReportes.obtenerTotalPuntosObtenidosPorUsuario(usuarioSeleccionado);
                lblValorTotalPuntosUsuario.setText(totalPuntos + " pts");
            } else {
                lblValorTotalKgUsuario.setText("0 kg");
                lblValorTotalPuntosUsuario.setText("0 pts");
            }
        } catch (Exception e) {
            lblValorTotalKgUsuario.setText("0 kg");
            lblValorTotalPuntosUsuario.setText("0 pts");
            System.out.println("Error al actualizar totales de usuario: " + e.getMessage());
        }
    }

    private static void actualizarTotalPuntosCanjeados() {
        try {
            String tipoRecompensa = (String) cmbTipoRecompensa.getSelectedItem();
            if (tipoRecompensa != null && !tipoRecompensa.isEmpty()) {
                int total = servicioReportes.obtenerTotalPuntosUtilizadosEnCanjes(tipoRecompensa);
                lblValorTotalPuntosCanjeados.setText(total + " pts");
            } else {
                lblValorTotalPuntosCanjeados.setText("0 pts");
            }
        } catch (Exception e) {
            lblValorTotalPuntosCanjeados.setText("0 pts");
            System.out.println("Error al actualizar total puntos canjeados: " + e.getMessage());
        }
    }

    private static void configurarAnchoColumnas(JTable tabla) {
        if (tabla.getColumnCount() >= 2) {
            // Primera columna (Tipo Material) - más compacta
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(0).setMaxWidth(100);
            tabla.getColumnModel().getColumn(0).setMinWidth(80);

            // Segunda columna (Nombre Usuario) - más compacta
            if (tabla.getColumnCount() > 1) {
                tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
                tabla.getColumnModel().getColumn(1).setMaxWidth(150);
                tabla.getColumnModel().getColumn(1).setMinWidth(120);
            }

            // Tercera columna (Total Kg/Puntos/CO2) - tamaño medio
            if (tabla.getColumnCount() > 2) {
                tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
                tabla.getColumnModel().getColumn(2).setMaxWidth(120);
                tabla.getColumnModel().getColumn(2).setMinWidth(70);
            }

            // Última columna (Fecha) - más compacta
            int ultimaColumna = tabla.getColumnCount() - 1;
            tabla.getColumnModel().getColumn(ultimaColumna).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(ultimaColumna).setMaxWidth(90);
            tabla.getColumnModel().getColumn(ultimaColumna).setMinWidth(70);
        }
    }
}
