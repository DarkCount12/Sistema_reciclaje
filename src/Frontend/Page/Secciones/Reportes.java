package Frontend.Page.Secciones;

import Backend.Servicios.ReportesServicio;
import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Reportes {
    private static JCheckBox chkRankingUsuarios;
    private static JComboBox<Integer> cmbCantidadUsuarios;
    private static JTable tablaRankingUsuarios;
    private static JScrollPane scrollRankingUsuarios;

    private static JCheckBox chkRankingRecompensas;
    private static JComboBox<Integer> cmbCantidadRecompensas;
    private static JTable tablaRankingRecompensas;
    private static JScrollPane scrollRankingRecompensas;

    private static JLabel lblValorTotalIzquierda;
    private static JLabel lblValorTotalDerecha;
    private static JLabel lblValorTotalCO2;
    private static JLabel lblValorTotalKgUsuario;
    private static JLabel lblValorTotalPuntosUsuario;
    private static JLabel lblValorTotalPuntosCanjeados;
    private static JLabel lblValorTotalCanjesRecompensa;
    private static JLabel lblValorRecompensaCanje;

    private static JLabel lblValorTotalKgGeneral;
    private static JLabel lblValorTotalPuntosGeneral;
    private static JLabel lblValorTotalCO2General;
    private static JLabel lblValorTotalKgUsuariosGeneral;
    private static JLabel lblValorTotalPuntosUsuariosGeneral;
    private static JLabel lblValorTotalPuntosRecompensasGeneral;
    private static JLabel lblValorTotalCanjesGeneral;

    private static JLabel lblTotalIzquierda;
    private static JLabel lblTotalDerecha;
    private static JLabel lblTotalCO2;
    private static JLabel lblTotalKgUsuario;
    private static JLabel lblTotalPuntosUsuario;
    private static JLabel lblTotalPuntosCanjeados;

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
        scrollMaterial.setPreferredSize(new Dimension(400, 200));
        scrollMaterial.setVisible(false);

        tablaPuntosGenerados = new JTable();
        scrollPuntos = new JScrollPane(tablaPuntosGenerados);
        scrollPuntos.setPreferredSize(new Dimension(400, 200));
        scrollPuntos.setVisible(false);

        tablasPanel.add(scrollMaterial);
        tablasPanel.add(scrollPuntos);
        centralPanel.add(tablasPanel, BorderLayout.CENTER);

        JPanel totalesPanel = new JPanel(new BorderLayout());
        totalesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalesTipoPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel totalIzquierdaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalIzquierda = new JLabel("Total Kg reciclado (Por tipo):");
        lblTotalIzquierda.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalIzquierda = new JLabel("0 kg");
        lblValorTotalIzquierda.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalIzquierda.setForeground(Color.BLACK);
        totalIzquierdaPanel.add(lblTotalIzquierda);
        totalIzquierdaPanel.add(lblValorTotalIzquierda);

        JPanel totalDerechaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalDerecha = new JLabel("Total puntos generados (Por tipo):");
        lblTotalDerecha.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalDerecha = new JLabel("0 pts");
        lblValorTotalDerecha.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalDerecha.setForeground(Color.BLACK);
        totalDerechaPanel.add(lblTotalDerecha);
        totalDerechaPanel.add(lblValorTotalDerecha);

        totalesTipoPanel.add(totalIzquierdaPanel);
        totalesTipoPanel.add(totalDerechaPanel);

        JPanel totalesGeneralPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalesGeneralPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalKgGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalKgGeneral = new JLabel("Total Kg reciclado (Por todos los tipos):");
        lblTotalKgGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalKgGeneral = new JLabel("0 kg");
        lblValorTotalKgGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalKgGeneral.setForeground(Color.BLUE);
        totalKgGeneralPanel.add(lblTotalKgGeneral);
        totalKgGeneralPanel.add(lblValorTotalKgGeneral);

        JPanel totalPuntosGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalPuntosGeneral = new JLabel("Total puntos generados (Por todos los tipos):");
        lblTotalPuntosGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosGeneral = new JLabel("0 pts");
        lblValorTotalPuntosGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosGeneral.setForeground(Color.BLUE);
        totalPuntosGeneralPanel.add(lblTotalPuntosGeneral);
        totalPuntosGeneralPanel.add(lblValorTotalPuntosGeneral);

        totalesGeneralPanel.add(totalKgGeneralPanel);
        totalesGeneralPanel.add(totalPuntosGeneralPanel);

        totalesPanel.add(totalesTipoPanel, BorderLayout.NORTH);
        totalesPanel.add(totalesGeneralPanel, BorderLayout.SOUTH);
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
            actualizarTotalesGeneralesReciclaje();
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
            actualizarTotalesGeneralesReciclaje();
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

        JLabel lblSubtitulo = new JLabel("Reporte de Impacto Ambiental");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);
        JPanel opcionesPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        chkImpactoAmbiental = new JCheckBox("CO2 Reducido por Tipo de Material");
        cmbTipoMaterial3 = new JComboBox<>();
        cmbTipoMaterial3.setEnabled(false);

        opcionesPanel.add(chkImpactoAmbiental);
        opcionesPanel.add(cmbTipoMaterial3);
        panel.add(opcionesPanel, BorderLayout.CENTER);
        JPanel centralPanel = new JPanel(new BorderLayout());

        tablaCO2Reducido = new JTable();
        scrollCO2 = new JScrollPane(tablaCO2Reducido);
        scrollCO2.setPreferredSize(new Dimension(1000, 200));
        scrollCO2.setVisible(false);
        centralPanel.add(scrollCO2, BorderLayout.CENTER);

        JPanel totalesPanel = new JPanel(new BorderLayout());
        totalesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalCO2Panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalCO2 = new JLabel("Total Kg de CO2 reducido (Por tipo):");
        lblTotalCO2.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalCO2 = new JLabel("0 kg CO2");
        lblValorTotalCO2.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalCO2.setForeground(Color.BLACK);
        totalCO2Panel.add(lblTotalCO2);
        totalCO2Panel.add(lblValorTotalCO2);

        JPanel totalCO2GeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        totalCO2GeneralPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        JLabel lblTotalCO2General = new JLabel("Total Kg de CO2 reducido (Por todos los tipos):");
        lblTotalCO2General.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalCO2General = new JLabel("0 kg CO2");
        lblValorTotalCO2General.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalCO2General.setForeground(Color.BLUE);
        totalCO2GeneralPanel.add(lblTotalCO2General);
        totalCO2GeneralPanel.add(lblValorTotalCO2General);

        totalesPanel.add(totalCO2Panel, BorderLayout.NORTH);
        totalesPanel.add(totalCO2GeneralPanel, BorderLayout.SOUTH);
        centralPanel.add(totalesPanel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        chkImpactoAmbiental.addActionListener(e -> {
            boolean selected = chkImpactoAmbiental.isSelected();
            cmbTipoMaterial3.setEnabled(selected);
            scrollCO2.setVisible(selected);
            if (selected && cmbTipoMaterial3.getSelectedItem() != null) {
                cargarReporteCO2Reducido();
                actualizarTotalCO2();
            } else {
                lblValorTotalCO2.setText("0 kg CO2");
            }
            actualizarTotalCO2General();
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

        JLabel lblSubtitulo = new JLabel("Reporte de Actividad de Usuarios");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        // Panel principal de opciones
        JPanel opcionesPanel = new JPanel();
        opcionesPanel.setLayout(new BoxLayout(opcionesPanel, BoxLayout.Y_AXIS));
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel para el ranking de usuarios
        JPanel rankingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkRankingUsuarios = new JCheckBox("Ranking Top Usuarios por Kg Reciclados:");
        cmbCantidadUsuarios = new JComboBox<>(new Integer[]{3, 5, 7, 10});
        cmbCantidadUsuarios.setSelectedItem(3); // Por defecto 3
        cmbCantidadUsuarios.setEnabled(false);
        cmbCantidadUsuarios.setPreferredSize(new Dimension(80, 25));

        rankingPanel.add(chkRankingUsuarios);
        rankingPanel.add(cmbCantidadUsuarios);

        // Panel para selección individual de usuario
        JPanel usuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkActividadUsuarios = new JCheckBox("Seleccionar Usuario:");
        cmbUsuarios = new JComboBox<>();
        cmbUsuarios.setEnabled(false);
        cmbUsuarios.setPreferredSize(new Dimension(200, 25));

        usuarioPanel.add(chkActividadUsuarios);
        usuarioPanel.add(cmbUsuarios);

        opcionesPanel.add(rankingPanel);
        opcionesPanel.add(usuarioPanel);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        JPanel centralPanel = new JPanel(new BorderLayout());

        // Panel para las tablas - CAMBIADO A BoxLayout VERTICAL
        JPanel tablasPanel = new JPanel();
        tablasPanel.setLayout(new BoxLayout(tablasPanel, BoxLayout.Y_AXIS));

        // Tabla de ranking
        tablaRankingUsuarios = new JTable();
        scrollRankingUsuarios = new JScrollPane(tablaRankingUsuarios);
        scrollRankingUsuarios.setPreferredSize(new Dimension(1000, 150));
        scrollRankingUsuarios.setVisible(false);
        scrollRankingUsuarios.setBorder(BorderFactory.createTitledBorder("Ranking de Usuarios"));

        // Tabla de actividad individual
        tablaActividadUsuarios = new JTable();
        scrollUsuarios = new JScrollPane(tablaActividadUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(1000, 200));
        scrollUsuarios.setVisible(false);
        scrollUsuarios.setBorder(BorderFactory.createTitledBorder("Actividad del Usuario"));

        // Agregar las tablas al panel
        tablasPanel.add(scrollRankingUsuarios);
        tablasPanel.add(Box.createVerticalStrut(10)); // Espaciado entre tablas
        tablasPanel.add(scrollUsuarios);

        centralPanel.add(tablasPanel, BorderLayout.CENTER);

        // Panel de totales
        JPanel totalesPanel = new JPanel(new BorderLayout());
        totalesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalesUsuarioPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        JPanel totalKgUsuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalKgUsuario = new JLabel("Total Kg reciclado (Por usuario):");
        lblTotalKgUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalKgUsuario = new JLabel("0 kg");
        lblValorTotalKgUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalKgUsuario.setForeground(Color.BLACK);
        totalKgUsuarioPanel.add(lblTotalKgUsuario);
        totalKgUsuarioPanel.add(lblValorTotalKgUsuario);

        JPanel totalPuntosUsuarioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalPuntosUsuario = new JLabel("Total puntos generados (Por usuario):");
        lblTotalPuntosUsuario.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosUsuario = new JLabel("0 pts");
        lblValorTotalPuntosUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosUsuario.setForeground(Color.BLACK);
        totalPuntosUsuarioPanel.add(lblTotalPuntosUsuario);
        totalPuntosUsuarioPanel.add(lblValorTotalPuntosUsuario);

        totalesUsuarioPanel.add(totalKgUsuarioPanel);
        totalesUsuarioPanel.add(totalPuntosUsuarioPanel);

        JPanel totalesGeneralUsuariosPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalesGeneralUsuariosPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalKgUsuariosGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalKgUsuariosGeneral = new JLabel("Total Kg reciclado (Por todos los usuarios):");
        lblTotalKgUsuariosGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalKgUsuariosGeneral = new JLabel("0 kg");
        lblValorTotalKgUsuariosGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalKgUsuariosGeneral.setForeground(Color.BLUE);
        totalKgUsuariosGeneralPanel.add(lblTotalKgUsuariosGeneral);
        totalKgUsuariosGeneralPanel.add(lblValorTotalKgUsuariosGeneral);

        JPanel totalPuntosUsuariosGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalPuntosUsuariosGeneral = new JLabel("Total puntos generados (Por todos los usuarios):");
        lblTotalPuntosUsuariosGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosUsuariosGeneral = new JLabel("0 pts");
        lblValorTotalPuntosUsuariosGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosUsuariosGeneral.setForeground(Color.BLUE);
        totalPuntosUsuariosGeneralPanel.add(lblTotalPuntosUsuariosGeneral);
        totalPuntosUsuariosGeneralPanel.add(lblValorTotalPuntosUsuariosGeneral);

        totalesGeneralUsuariosPanel.add(totalKgUsuariosGeneralPanel);
        totalesGeneralUsuariosPanel.add(totalPuntosUsuariosGeneralPanel);

        totalesPanel.add(totalesUsuarioPanel, BorderLayout.NORTH);
        totalesPanel.add(totalesGeneralUsuariosPanel, BorderLayout.SOUTH);
        centralPanel.add(totalesPanel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        // Event Listeners
        chkRankingUsuarios.addActionListener(e -> {
            boolean selected = chkRankingUsuarios.isSelected();
            cmbCantidadUsuarios.setEnabled(selected);
            scrollRankingUsuarios.setVisible(selected);
            if (selected) {
                cargarRankingUsuarios();
            }
            actualizarTotalesGeneralesUsuarios();
            panel.revalidate();
            panel.repaint();
        });

        cmbCantidadUsuarios.addActionListener(e -> {
            if (chkRankingUsuarios.isSelected()) {
                cargarRankingUsuarios();
            }
        });

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
            actualizarTotalesGeneralesUsuarios();
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

        JLabel lblSubtitulo = new JLabel("Reporte de Recompensas Canjeadas");
        lblSubtitulo.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(lblSubtitulo, BorderLayout.NORTH);

        JPanel opcionesPanel = new JPanel(new BorderLayout());
        opcionesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // Panel superior para opciones
        JPanel opcionesSuperioresPanel = new JPanel();
        opcionesSuperioresPanel.setLayout(new BoxLayout(opcionesSuperioresPanel, BoxLayout.Y_AXIS));

        // Panel para el ranking de recompensas
        JPanel rankingRecompensasPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkRankingRecompensas = new JCheckBox("Ranking Top Recompensas por Canjes:");
        cmbCantidadRecompensas = new JComboBox<>(new Integer[]{3, 5, 7, 10});
        cmbCantidadRecompensas.setSelectedItem(3); // Por defecto 3
        cmbCantidadRecompensas.setEnabled(false);
        cmbCantidadRecompensas.setPreferredSize(new Dimension(80, 25));

        rankingRecompensasPanel.add(chkRankingRecompensas);
        rankingRecompensasPanel.add(cmbCantidadRecompensas);

        // Panel para selección individual de recompensa
        JPanel dropdownPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkRecompensasCanjeadas = new JCheckBox("Seleccionar Tipo de Recompensa:");
        cmbTipoRecompensa = new JComboBox<>();
        cmbTipoRecompensa.setEnabled(false);
        cmbTipoRecompensa.setPreferredSize(new Dimension(200, 25));
        dropdownPanel.add(chkRecompensasCanjeadas);
        dropdownPanel.add(cmbTipoRecompensa);

        opcionesSuperioresPanel.add(rankingRecompensasPanel);
        opcionesSuperioresPanel.add(dropdownPanel);

        JPanel valorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblValorCanje = new JLabel("Valor de recompensa por canje:");
        lblValorCanje.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorRecompensaCanje = new JLabel("0 pts");
        lblValorRecompensaCanje.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorRecompensaCanje.setForeground(Color.BLACK);
        valorPanel.add(lblValorCanje);
        valorPanel.add(lblValorRecompensaCanje);

        opcionesPanel.add(opcionesSuperioresPanel, BorderLayout.NORTH);
        opcionesPanel.add(valorPanel, BorderLayout.SOUTH);
        panel.add(opcionesPanel, BorderLayout.CENTER);

        JPanel centralPanel = new JPanel(new BorderLayout());

        // Panel para las tablas
        JPanel tablasPanel = new JPanel(new BorderLayout());

        // Tabla de ranking de recompensas
        tablaRankingRecompensas = new JTable();
        scrollRankingRecompensas = new JScrollPane(tablaRankingRecompensas);
        scrollRankingRecompensas.setPreferredSize(new Dimension(1000, 150));
        scrollRankingRecompensas.setVisible(false);
        scrollRankingRecompensas.setBorder(BorderFactory.createTitledBorder("Ranking de Recompensas"));

        // Tabla de recompensas individuales
        tablaRecompensas = new JTable();
        scrollRecompensas = new JScrollPane(tablaRecompensas);
        scrollRecompensas.setPreferredSize(new Dimension(1000, 200));
        scrollRecompensas.setVisible(false);
        scrollRecompensas.setBorder(BorderFactory.createTitledBorder("Detalles de Recompensa"));

        tablasPanel.add(scrollRankingRecompensas, BorderLayout.NORTH);
        tablasPanel.add(scrollRecompensas, BorderLayout.CENTER);
        centralPanel.add(tablasPanel, BorderLayout.CENTER);

        // Panel de totales (sin cambios)
        JPanel totalesPanel = new JPanel(new BorderLayout());
        totalesPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalesRecompensaPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel totalPuntosCanjeadosPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblTotalPuntosCanjeados = new JLabel("Total puntos utilizados (Por recompensa):");
        lblTotalPuntosCanjeados.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosCanjeados = new JLabel("0 pts");
        lblValorTotalPuntosCanjeados.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosCanjeados.setForeground(Color.BLACK);
        totalPuntosCanjeadosPanel.add(lblTotalPuntosCanjeados);
        totalPuntosCanjeadosPanel.add(lblValorTotalPuntosCanjeados);

        JPanel totalCanjesRecompensaPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalCanjesRecompensa = new JLabel("Total canjes realizados (Por recompensa):");
        lblTotalCanjesRecompensa.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalCanjesRecompensa = new JLabel("0 canjes");
        lblValorTotalCanjesRecompensa.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalCanjesRecompensa.setForeground(Color.BLACK);
        totalCanjesRecompensaPanel.add(lblTotalCanjesRecompensa);
        totalCanjesRecompensaPanel.add(lblValorTotalCanjesRecompensa);

        totalesRecompensaPanel.add(totalPuntosCanjeadosPanel);
        totalesRecompensaPanel.add(totalCanjesRecompensaPanel);

        JPanel totalesGeneralRecompensasPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        totalesGeneralRecompensasPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        JPanel totalPuntosRecompensasGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalPuntosRecompensasGeneral = new JLabel("Total puntos utilizados (Por todas las recompensas):");
        lblTotalPuntosRecompensasGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalPuntosRecompensasGeneral = new JLabel("0 pts");
        lblValorTotalPuntosRecompensasGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalPuntosRecompensasGeneral.setForeground(Color.BLUE);
        totalPuntosRecompensasGeneralPanel.add(lblTotalPuntosRecompensasGeneral);
        totalPuntosRecompensasGeneralPanel.add(lblValorTotalPuntosRecompensasGeneral);

        JPanel totalCanjesGeneralPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTotalCanjesGeneral = new JLabel("Total canjes realizados (Por todas las recompensas):");
        lblTotalCanjesGeneral.setFont(new Font("Arial", Font.BOLD, 12));
        lblValorTotalCanjesGeneral = new JLabel("0 canjes");
        lblValorTotalCanjesGeneral.setFont(new Font("Arial", Font.PLAIN, 12));
        lblValorTotalCanjesGeneral.setForeground(Color.BLUE);
        totalCanjesGeneralPanel.add(lblTotalCanjesGeneral);
        totalCanjesGeneralPanel.add(lblValorTotalCanjesGeneral);

        totalesGeneralRecompensasPanel.add(totalPuntosRecompensasGeneralPanel);
        totalesGeneralRecompensasPanel.add(totalCanjesGeneralPanel);

        totalesPanel.add(totalesRecompensaPanel, BorderLayout.NORTH);
        totalesPanel.add(totalesGeneralRecompensasPanel, BorderLayout.SOUTH);
        centralPanel.add(totalesPanel, BorderLayout.SOUTH);

        panel.add(centralPanel, BorderLayout.SOUTH);

        // Event Listeners
        chkRankingRecompensas.addActionListener(e -> {
            boolean selected = chkRankingRecompensas.isSelected();
            cmbCantidadRecompensas.setEnabled(selected);
            scrollRankingRecompensas.setVisible(selected);
            if (selected) {
                cargarRankingRecompensas();
            }
            actualizarTotalesGeneralesRecompensas();
            panel.revalidate();
            panel.repaint();
        });

        cmbCantidadRecompensas.addActionListener(e -> {
            if (chkRankingRecompensas.isSelected()) {
                cargarRankingRecompensas();
            }
        });

        chkRecompensasCanjeadas.addActionListener(e -> {
            boolean selected = chkRecompensasCanjeadas.isSelected();
            cmbTipoRecompensa.setEnabled(selected);
            scrollRecompensas.setVisible(selected);
            if (selected && cmbTipoRecompensa.getSelectedItem() != null) {
                cargarReporteRecompensasCanjeadas();
                actualizarTotalPuntosCanjeados();
                actualizarTotalCanjesRecompensa();
                actualizarValorRecompensaCanje();
            } else {
                lblValorTotalPuntosCanjeados.setText("0 pts");
                lblValorTotalCanjesRecompensa.setText("0 canjes");
                lblValorRecompensaCanje.setText("0 pts");
            }
            actualizarTotalesGeneralesRecompensas();
            panel.revalidate();
            panel.repaint();
        });

        cmbTipoRecompensa.addActionListener(e -> {
            if (chkRecompensasCanjeadas.isSelected()) {
                cargarReporteRecompensasCanjeadas();
                actualizarTotalPuntosCanjeados();
                actualizarTotalCanjesRecompensa();
                actualizarValorRecompensaCanje();
            }
        });

        return panel;
    }

    private static void cargarDatosIniciales() {
        List<String> tiposMaterial = servicioReportes.obtenerTiposMaterial();
        for (String tipo : tiposMaterial) {
            cmbTipoMaterial1.addItem(tipo);
            cmbTipoMaterial2.addItem(tipo);
            cmbTipoMaterial3.addItem(tipo);
        }
        List<String> usuarios = servicioReportes.obtenerNombresUsuarios();
        for (String usuario : usuarios) {
            cmbUsuarios.addItem(usuario);
        }
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

    private static void actualizarTotalIzquierda() {
        try {
            String tipoMaterial = (String) cmbTipoMaterial1.getSelectedItem();
            if (tipoMaterial != null && !tipoMaterial.isEmpty()) {
                int total = servicioReportes.obtenerTotalKgRecicladoPorTipo(tipoMaterial);
                lblValorTotalIzquierda.setText(total + " kg");
                lblTotalIzquierda.setText("Total Kg reciclado (Por \"" + tipoMaterial + "\"):");
            } else {
                lblValorTotalIzquierda.setText("0 kg");
                lblTotalIzquierda.setText("Total Kg reciclado (Por tipo):");
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
                lblTotalDerecha.setText("Total puntos generados (Por \"" + tipoMaterial + "\"):");
            } else {
                lblValorTotalDerecha.setText("0 pts");
                lblTotalDerecha.setText("Total puntos generados (Por tipo):");
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
                lblTotalCO2.setText("Total Kg de CO2 reducido (Por \"" + tipoMaterial + "\"):");
            } else {
                lblValorTotalCO2.setText("0 kg CO2");
                lblTotalCO2.setText("Total Kg de CO2 reducido (Por tipo):");
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
                lblTotalKgUsuario.setText("Total Kg reciclado (Por \"" + usuarioSeleccionado + "\"):");

                // Actualizar total de puntos obtenidos por el usuario
                int totalPuntos = servicioReportes.obtenerTotalPuntosObtenidosPorUsuario(usuarioSeleccionado);
                lblValorTotalPuntosUsuario.setText(totalPuntos + " pts");
                lblTotalPuntosUsuario.setText("Total puntos generados (Por \"" + usuarioSeleccionado + "\"):");
            } else {
                lblValorTotalKgUsuario.setText("0 kg");
                lblValorTotalPuntosUsuario.setText("0 pts");
                lblTotalKgUsuario.setText("Total Kg reciclado (Por usuario):");
                lblTotalPuntosUsuario.setText("Total puntos generados (Por usuario):");
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
                lblTotalPuntosCanjeados.setText("Total puntos utilizados (Por \"" + tipoRecompensa + "\"):");
            } else {
                lblValorTotalPuntosCanjeados.setText("0 pts");
                lblTotalPuntosCanjeados.setText("Total puntos utilizados (Por recompensa):");
            }
        } catch (Exception e) {
            lblValorTotalPuntosCanjeados.setText("0 pts");
            System.out.println("Error al actualizar total puntos canjeados: " + e.getMessage());
        }
    }

    private static void actualizarTotalCanjesRecompensa() {
        try {
            String tipoRecompensa = (String) cmbTipoRecompensa.getSelectedItem();
            if (tipoRecompensa != null && !tipoRecompensa.isEmpty()) {
                int total = servicioReportes.obtenerTotalCanjesPorRecompensa(tipoRecompensa);
                lblValorTotalCanjesRecompensa.setText(total + " canjes");
            } else {
                lblValorTotalCanjesRecompensa.setText("0 canjes");
            }
        } catch (Exception e) {
            lblValorTotalCanjesRecompensa.setText("0 canjes");
            System.out.println("Error al actualizar total canjes recompensa: " + e.getMessage());
        }
    }

    private static void actualizarValorRecompensaCanje() {
        try {
            String tipoRecompensa = (String) cmbTipoRecompensa.getSelectedItem();
            if (tipoRecompensa != null && !tipoRecompensa.isEmpty()) {
                int valor = (int) servicioReportes.obtenerValorRecompensaCanje(tipoRecompensa);
                lblValorRecompensaCanje.setText(valor + " pts");
            } else {
                lblValorRecompensaCanje.setText("0 pts");
            }
        } catch (Exception e) {
            lblValorRecompensaCanje.setText("0 pts");
            System.out.println("Error al actualizar valor recompensa canje: " + e.getMessage());
        }
    }

    private static void actualizarTotalesGeneralesReciclaje() {
        try {
            int totalKgGeneral = servicioReportes.obtenerTotalKgRecicladoGeneral();
            lblValorTotalKgGeneral.setText(totalKgGeneral + " kg");

            int totalPuntosGeneral = servicioReportes.obtenerTotalPuntosGeneradosGeneral();
            lblValorTotalPuntosGeneral.setText(totalPuntosGeneral + " pts");
        } catch (Exception e) {
            lblValorTotalKgGeneral.setText("0 kg");
            lblValorTotalPuntosGeneral.setText("0 pts");
            System.out.println("Error al actualizar totales generales reciclaje: " + e.getMessage());
        }
    }

    private static void actualizarTotalCO2General() {
        try {
            int totalCO2General = servicioReportes.obtenerTotalCO2ReducidoGeneral();
            lblValorTotalCO2General.setText(totalCO2General + " kg CO2");
        } catch (Exception e) {
            lblValorTotalCO2General.setText("0 kg CO2");
            System.out.println("Error al actualizar total CO2 general: " + e.getMessage());
        }
    }

    private static void actualizarTotalesGeneralesUsuarios() {
        try {
            int totalKgUsuariosGeneral = servicioReportes.obtenerTotalKgRecicladoGeneral();
            lblValorTotalKgUsuariosGeneral.setText(totalKgUsuariosGeneral + " kg");

            int totalPuntosUsuariosGeneral = servicioReportes.obtenerTotalPuntosGeneradosGeneral();
            lblValorTotalPuntosUsuariosGeneral.setText(totalPuntosUsuariosGeneral + " pts");
        } catch (Exception e) {
            lblValorTotalKgUsuariosGeneral.setText("0 kg");
            lblValorTotalPuntosUsuariosGeneral.setText("0 pts");
            System.out.println("Error al actualizar totales generales usuarios: " + e.getMessage());
        }
    }

    private static void actualizarTotalesGeneralesRecompensas() {
        try {
            int totalPuntosRecompensasGeneral = servicioReportes.obtenerTotalPuntosUtilizadosEnTodosLosCanjes();
            lblValorTotalPuntosRecompensasGeneral.setText(totalPuntosRecompensasGeneral + " pts");

            int totalCanjesGeneral = servicioReportes.obtenerTotalCanjesTodasRecompensas();
            lblValorTotalCanjesGeneral.setText(totalCanjesGeneral + " canjes");
        } catch (Exception e) {
            lblValorTotalPuntosRecompensasGeneral.setText("0 pts");
            lblValorTotalCanjesGeneral.setText("0 canjes");
            System.out.println("Error al actualizar totales generales recompensas: " + e.getMessage());
        }
    }

    private static void configurarAnchoColumnas(JTable tabla) {
        if (tabla.getColumnCount() >= 2) {
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(0).setMaxWidth(100);
            tabla.getColumnModel().getColumn(0).setMinWidth(80);

            if (tabla.getColumnCount() > 1) {
                tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
                tabla.getColumnModel().getColumn(1).setMaxWidth(150);
                tabla.getColumnModel().getColumn(1).setMinWidth(120);
            }
            if (tabla.getColumnCount() > 2) {
                tabla.getColumnModel().getColumn(2).setPreferredWidth(80);
                tabla.getColumnModel().getColumn(2).setMaxWidth(120);
                tabla.getColumnModel().getColumn(2).setMinWidth(70);
            }
            int ultimaColumna = tabla.getColumnCount() - 1;
            tabla.getColumnModel().getColumn(ultimaColumna).setPreferredWidth(70);
            tabla.getColumnModel().getColumn(ultimaColumna).setMaxWidth(90);
            tabla.getColumnModel().getColumn(ultimaColumna).setMinWidth(70);
        }
    }

    private static void cargarRankingUsuarios() {
        Integer cantidad = (Integer) cmbCantidadUsuarios.getSelectedItem();
        if (cantidad != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerRankingUsuariosPorKgReciclados(cantidad);

            String[] columnas = {"Posición", "Usuario", "Total Kg Reciclados", "Total Puntos"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            int posicion = 1;
            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        posicion++,
                        fila.get("nombre_usuario"),
                        fila.get("total_kg_reciclado"),
                        fila.get("total_puntos_generados")
                };
                model.addRow(rowData);
            }

            tablaRankingUsuarios.setModel(model);
            configurarAnchoColumnasRanking(tablaRankingUsuarios);
        }
    }

    private static void cargarRankingRecompensas() {
        Integer cantidad = (Integer) cmbCantidadRecompensas.getSelectedItem();
        if (cantidad != null) {
            List<Map<String, Object>> datos = servicioReportes.obtenerRankingRecompensasPorCanjes(cantidad);

            String[] columnas = {"Posición", "Recompensa", "Total Canjes", "Total Puntos Utilizados"};
            DefaultTableModel model = new DefaultTableModel(columnas, 0);

            int posicion = 1;
            for (Map<String, Object> fila : datos) {
                Object[] rowData = {
                        posicion++,
                        fila.get("nombre_recompensa"),
                        fila.get("total_canjes"),
                        fila.get("total_puntos_utilizados")
                };
                model.addRow(rowData);
            }

            tablaRankingRecompensas.setModel(model);
            configurarAnchoColumnasRanking(tablaRankingRecompensas);
        }
    }

    private static void configurarAnchoColumnasRanking(JTable tabla) {
        if (tabla.getColumnCount() >= 4) {
            // Posición
            tabla.getColumnModel().getColumn(0).setPreferredWidth(80);
            tabla.getColumnModel().getColumn(0).setMaxWidth(100);
            tabla.getColumnModel().getColumn(0).setMinWidth(60);

            // Nombre (Usuario/Recompensa)
            tabla.getColumnModel().getColumn(1).setPreferredWidth(200);
            tabla.getColumnModel().getColumn(1).setMaxWidth(250);
            tabla.getColumnModel().getColumn(1).setMinWidth(150);

            // Cantidad principal (Kg/Canjes)
            tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(2).setMaxWidth(150);
            tabla.getColumnModel().getColumn(2).setMinWidth(100);

            // Puntos
            tabla.getColumnModel().getColumn(3).setPreferredWidth(120);
            tabla.getColumnModel().getColumn(3).setMaxWidth(150);
            tabla.getColumnModel().getColumn(3).setMinWidth(100);
        }
    }
}
