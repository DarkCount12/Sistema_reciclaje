package Frontend.PopUp;


import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.Donaciones;
import Backend.Modelos.Usuario;
import Backend.Servicios.Cache;
import Backend.Servicios.DonacionesServicio;
import Backend.Utils.Estilos;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class AccederDonaciones extends JPanel {
    private DonacionesServicio donacionesServicio;
    private JTable tablaDonaciones;
    private DefaultTableModel modelo;

    public AccederDonaciones() {
        this.donacionesServicio = new DonacionesServicio();
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel principal con pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de nueva donación
        tabbedPane.addTab("Nueva Donación", crearPanelNuevaDonacion());
        
        // Pestaña de historial
        tabbedPane.addTab("Historial", crearPanelHistorial());
        
        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel crearPanelNuevaDonacion() {
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Componentes del formulario
        JLabel lblMonto = new JLabel("Monto ($):");
        lblMonto.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblMonto.setBounds(50, 50, 100, 20);
        
        JTextField txtMonto = new JTextField();
        txtMonto.setBounds(160, 50, 200, 25);
        
        JLabel lblMetodo = new JLabel("Método de Pago:");
        lblMetodo.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        lblMetodo.setBounds(50, 100, 120, 20);
        
        JComboBox<String> cmbMetodo = new JComboBox<>(new String[]{
            "Tarjeta de Crédito", "PayPal", "Transferencia Bancaria"
        });
        cmbMetodo.setBounds(160, 100, 200, 25);
        
        JButton btnDonar = Estilos.crearBoton("#4CAF50", "#FFFFFF", "Realizar Donación", 150, 30);
        btnDonar.setBounds(160, 150, 150, 30);
        btnDonar.addActionListener(e -> registrarDonacion(
            txtMonto.getText(),
            (String)cmbMetodo.getSelectedItem()
        ));
        
        // Agregar componentes al panel
        panel.add(lblMonto);
        panel.add(txtMonto);
        panel.add(lblMetodo);
        panel.add(cmbMetodo);
        panel.add(btnDonar);
        
        return panel;
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        // Modelo de tabla
        String[] columnas = {"Fecha", "Monto", "Método"};
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tablaDonaciones = new JTable(modelo);
        tablaDonaciones.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
        tablaDonaciones.setRowHeight(25);
        
        JScrollPane scrollPane = new JScrollPane(tablaDonaciones);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Cargar datos iniciales
        cargarHistorial();
        
        return panel;
    }

    private void registrarDonacion(String montoStr, String metodo) {
        try {
            double monto = Double.parseDouble(montoStr);
            if (monto <= 0) {
                JOptionPane.showMessageDialog(this, "El monto debe ser positivo", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            UsuarioDAO usuarioDAO= new UsuarioDAO();
            Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(Cache.obtenerCorreo());
            int idUsuario = usuario.getId();
            boolean exito = donacionesServicio.registrarDonacion(
                idUsuario,
                monto,
                metodo,
                new Date()
            );
            
            if (exito) {
                JOptionPane.showMessageDialog(this, "Donación registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarHistorial();
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar donación", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un monto válido", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarHistorial() {
        modelo.setRowCount(0); // Limpiar tabla
        
         UsuarioDAO usuarioDAO= new UsuarioDAO();
            Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(Cache.obtenerCorreo());
            int idUsuario = usuario.getId();
        List<Donaciones> donaciones = donacionesServicio.obtenerDonacionesPorUsuario(idUsuario);
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        for (Donaciones d : donaciones) {
            modelo.addRow(new Object[]{
                sdf.format(d.getFechaDonacion()),
                String.format("$%.2f", d.getMonto()),
                d.getMetodoPago()
            });
        }
    }
}

