package Frontend.Maps;

import Backend.DAOs.Usuario2DAO;
import Backend.Modelos.Puntaje;
import Backend.Modelos.Usuario;
import java.awt.*;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class UsuarioCrudPanel extends JPanel {

    private Usuario2DAO Usuario2DAO = new Usuario2DAO();
    private UsuarioFormMapper formMapper = new UsuarioFormMapper();

    private JTextField txtBusqueda;
    private JComboBox<String> comboRoles;
    private JButton btnBuscar;

    private JTable tablaUsuarios;
    private DefaultTableModel tablaModel;

    private JPanel panelFormulario;
    private JLabel lblRol;
    private JLabel lblPuntaje;

    public UsuarioCrudPanel() {
        initUI();
        cargarRoles();
        cargarUsuarios();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // PANEL FILTROS ARRIBA
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBusqueda = new JTextField(20);
        comboRoles = new JComboBox<>();
        comboRoles.addItem("Todos");
        btnBuscar = new JButton("Buscar");

        panelFiltros.add(new JLabel("Buscar (nombre, apellido, correo):"));
        panelFiltros.add(txtBusqueda);
        panelFiltros.add(new JLabel("Filtrar por rol:"));
        panelFiltros.add(comboRoles);
        panelFiltros.add(btnBuscar);

        add(panelFiltros, BorderLayout.NORTH);

        // TABLA DE USUARIOS
        tablaModel = new DefaultTableModel(new String[]{"ID", "Nombre", "Apellido", "Correo", "Teléfono"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Evitar edición directa
            }
        };
        tablaUsuarios = new JTable(tablaModel);
        tablaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollTabla = new JScrollPane(tablaUsuarios);
        scrollTabla.setPreferredSize(new Dimension(600, 300));
        add(scrollTabla, BorderLayout.CENTER);

        // PANEL FORMULARIO Y DATOS EXTRA
        JPanel panelInferior = new JPanel(new BorderLayout());

        panelFormulario = new JPanel(new GridLayout(0, 2));
        panelFormulario.setVisible(false);
        panelInferior.add(panelFormulario, BorderLayout.CENTER);

        JPanel panelDatosExtra = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lblRol = new JLabel("Rol: ");
        lblPuntaje = new JLabel("Puntaje total: ");
        panelDatosExtra.add(lblRol);
        panelDatosExtra.add(Box.createHorizontalStrut(20));
        panelDatosExtra.add(lblPuntaje);

        panelInferior.add(panelDatosExtra, BorderLayout.SOUTH);

        add(panelInferior, BorderLayout.SOUTH);

        // BOTONES CRUD
        JPanel panelBotones = new JPanel();
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        add(panelBotones, BorderLayout.WEST);

        // EVENTOS
        btnBuscar.addActionListener(e -> buscarUsuarios());
        btnAgregar.addActionListener(e -> mostrarFormulario(null, "add"));
        btnEditar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaModel.getValueAt(fila, 0);
                Usuario usuario = Usuario2DAO.obtenerPorID(id);
                if (usuario != null) {
                    mostrarFormulario(usuario, "update");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para editar.");
            }
        });
        btnEliminar.addActionListener(e -> {
            int fila = tablaUsuarios.getSelectedRow();
            if (fila >= 0) {
                int id = (int) tablaModel.getValueAt(fila, 0);
                Usuario usuario = Usuario2DAO.obtenerPorID(id);
                if (usuario != null) {
                    int resp = JOptionPane.showConfirmDialog(this, "¿Eliminar usuario seleccionado?", "Confirmar", JOptionPane.YES_NO_OPTION);
                    if (resp == JOptionPane.YES_OPTION) {
                        Usuario2DAO.eliminar(id);
                        cargarUsuarios();
                        limpiarDatosExtra();
                        JOptionPane.showMessageDialog(this, "Usuario eliminado.");
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selecciona un usuario para eliminar.");
            }
        });

        tablaUsuarios.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                mostrarDatosExtraSeleccion();
            }
        });
    }

    private void cargarRoles() {
        
        comboRoles.addItem("Admin");
        comboRoles.addItem("Usuario");
        comboRoles.addItem("Recolector");
       
    }

    private void cargarUsuarios() {
        List<Usuario> usuarios = Usuario2DAO.obtenerTodos();
        cargarUsuariosEnTabla(usuarios);
    }

    private void buscarUsuarios() {
        String texto = txtBusqueda.getText().trim();
        String rolSeleccionado = (String) comboRoles.getSelectedItem();

        List<Usuario> usuarios;

        boolean filtrarRol = rolSeleccionado != null && !rolSeleccionado.equals("Todos");

        if (texto.isEmpty() && !filtrarRol) {
            usuarios = Usuario2DAO.obtenerTodos();
        } else if (filtrarRol && texto.isEmpty()) {
            // Buscar solo por rol
            // Aquí necesitarás convertir el nombre de rol a id, o hacer otro método en DAO para buscar por nombre rol
            // Para demo, asumamos un método ficticio que retorna usuarios por nombre rol:
            usuarios = Usuario2DAO.buscarPorRolNombre(rolSeleccionado);
        } else if (!filtrarRol) {
            // Buscar solo por texto
            usuarios = Usuario2DAO.buscarPorNombreApellidoOCorreo(texto);
        } else {
            // Buscar por texto y por rol combinados (puedes hacer la consulta SQL combinada o filtrar en java)
            // Para simplicidad, filtramos en Java:
            List<Usuario> porTexto = Usuario2DAO.buscarPorNombreApellidoOCorreo(texto);
            usuarios = porTexto.stream()
                .filter(u -> rolSeleccionado.equals(Usuario2DAO.obtenerRolDeUsuario(u.getId())))
                .toList();
        }

        cargarUsuariosEnTabla(usuarios);
        limpiarDatosExtra();
    }

    private void cargarUsuariosEnTabla(List<Usuario> usuarios) {
        tablaModel.setRowCount(0);
        for (Usuario u : usuarios) {
            tablaModel.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getCorreo(),
                    u.getTelefono()
            });
        }
    }

    private void mostrarFormulario(Usuario usuario, String modo) {
        panelFormulario.removeAll();
        Map<String, JComponent> campos = formMapper.crearCampos(usuario);

        for (Map.Entry<String, JComponent> entry : campos.entrySet()) {
            panelFormulario.add(new JLabel(entry.getKey()));
            panelFormulario.add(entry.getValue());
        }

        JButton btnGuardar = new JButton("Guardar");
        btnGuardar.addActionListener(e -> {
            try {
                Usuario nuevo = formMapper.construirDesdeCampos(campos);
                if ("add".equals(modo)) {
                    Usuario2DAO.insertar(nuevo);
                    JOptionPane.showMessageDialog(this, "Usuario añadido correctamente.");
                } else {
                    Usuario2DAO.actualizar(usuario.getId(), nuevo);
                    JOptionPane.showMessageDialog(this, "Usuario actualizado correctamente.");
                }
                cargarUsuarios();
                panelFormulario.setVisible(false);
                limpiarDatosExtra();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error guardando usuario: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panelFormulario.add(new JLabel());
        panelFormulario.add(btnGuardar);
        panelFormulario.setVisible(true);
        revalidate();
        repaint();
    }

   private void mostrarDatosExtraSeleccion() {
        int fila = tablaUsuarios.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tablaModel.getValueAt(fila, 0);
            String rol = Usuario2DAO.obtenerRolDeUsuario(id);
            Puntaje puntaje = Usuario2DAO.obtenerPuntajeDeUsuario(id);

            lblRol.setText("Rol: " + rol);

            if (puntaje != null) {
                lblPuntaje.setText("Totales: " + puntaje.getPuntosTotales() +
                                " | Ganados: " + puntaje.getPuntosGanados() +
                                " | Gastados: " + puntaje.getPuntosGastados());
            } else {
                lblPuntaje.setText("Puntaje: No disponible");
            }
        } else {
            limpiarDatosExtra();
        }
    }


    private void limpiarDatosExtra() {
        lblRol.setText("Rol: ");
        lblPuntaje.setText("Puntaje total: ");
        tablaUsuarios.clearSelection();
        panelFormulario.setVisible(false);
    }
}
