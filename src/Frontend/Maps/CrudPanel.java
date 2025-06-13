package Frontend.Maps;

import Backend.DAOs.CrudDAO;
import Backend.DAOs.PuntoReciclaje2DAO;
import Backend.DAOs.PuntoTipoMaterialDAO;
import Backend.Modelos.Descuento;
import Backend.Modelos.PuntoReciclaje;
import Backend.Modelos.Recompensa;
import Backend.Modelos.TipoMaterial;
import Backend.Modelos.Usuario;
import java.awt.*;
import java.util.Map;
import javax.swing.*; // Para Map<String, JComponent>
import javax.swing.table.DefaultTableModel;

public class CrudPanel<T> extends JPanel {
    private CrudDAO<T> dao;
    private FormMapper<T> formMapper;

 /////   private JComboBox<T> comboItems;
    private JTable tablaItems;
    private DefaultTableModel tablaModel;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private JPanel panelFormulario;

    public CrudPanel(CrudDAO<T> dao, FormMapper<T> formMapper) {
        this.dao = dao;
        this.formMapper = formMapper;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());

 
        tablaModel = new DefaultTableModel();
        tablaItems = new JTable(tablaModel);
        tablaItems.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scroll = new JScrollPane(tablaItems);
        scroll.setPreferredSize(new Dimension(500, tablaItems.getRowHeight() * 14)); // 10 filas visibles
        add(scroll, BorderLayout.NORTH);
        
        actualizarLista();

        btnAgregar = new JButton("Agregar");
        btnActualizar = new JButton("Actualizar");
        btnEliminar = new JButton("Eliminar");

        JPanel acciones = new JPanel();
        acciones.add(btnAgregar);
        acciones.add(btnActualizar);
        acciones.add(btnEliminar);

        panelFormulario = new JPanel(new GridLayout(0, 2));
        panelFormulario.setVisible(false);

       // add(comboItems, BorderLayout.NORTH);
       // add(tablaItems, BorderLayout.NORTH);
        add(acciones, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> mostrarFormulario(null, "add"));
        btnActualizar.addActionListener(e -> {
        //    T seleccionado = (T) comboItems.getSelectedItem();


        int fila = tablaItems.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tablaModel.getValueAt(fila, 0);
            T seleccionado = dao.obtenerPorID(id);
            // usar seleccionado como antes
              if (seleccionado != null)
                mostrarFormulario(seleccionado, "update");
        }
      
        });



        btnEliminar.addActionListener(e -> {
        int fila = tablaItems.getSelectedRow();
        if (fila >= 0) {
            int id = (int) tablaModel.getValueAt(fila, 0);
            T seleccionado = dao.obtenerPorID(id);

            if (seleccionado != null) {
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {

                    // Verificar si es PuntoReciclaje
                    if (seleccionado instanceof PuntoReciclaje) {
                        PuntoTipoMaterialDAO ptmDAO = new PuntoTipoMaterialDAO();
                        ptmDAO.eliminarRelacionesPorPunto(formMapper.getId(seleccionado));
                    }

                    // Luego eliminar el objeto
                    dao.eliminar(formMapper.getId(seleccionado));
                    actualizarLista();
                    JOptionPane.showMessageDialog(this, "Eliminación procesada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
    });

    }

    private void mostrarFormulario(T entidad, String modo) {
        panelFormulario.removeAll();
        Map<String, JComponent> campos = formMapper.crearCampos(entidad);
        for (Map.Entry<String, JComponent> entry : campos.entrySet()) {
            panelFormulario.add(new JLabel(entry.getKey()));
            panelFormulario.add(entry.getValue());
        }

        JButton btnGuardar = new JButton("Guardar");
                btnGuardar.addActionListener(e -> {
            try {
                T nuevo = formMapper.construirDesdeCampos(campos);
                  if (modo.equals("add")) {

                if (nuevo instanceof PuntoReciclaje) {
                PuntoReciclaje2DAO prDAO = new PuntoReciclaje2DAO();
                int idNuevoReciclaje = prDAO.insertarYRetornarId((PuntoReciclaje) nuevo);

                PuntoTipoMaterialDAO ptmDAO = new PuntoTipoMaterialDAO();
                PuntoReciclajeFormMapper prFormMapper = (PuntoReciclajeFormMapper) formMapper;

                ptmDAO.insertarRelaciones(idNuevoReciclaje, prFormMapper.getMaterialesSeleccionados());
            } else {
                // Los demás DAOs siguen igual
                dao.insertar(nuevo);
            }

            JOptionPane.showMessageDialog(this, "Se añadió correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                } else {
                    dao.actualizar(formMapper.getId(entidad), nuevo);

                 // Si estamos trabajando con PuntoReciclaje, también actualizamos las relaciones
                    if (nuevo instanceof PuntoReciclaje) {
                        PuntoTipoMaterialDAO ptmDAO = new PuntoTipoMaterialDAO();
                        PuntoReciclajeFormMapper prFormMapper = (PuntoReciclajeFormMapper) formMapper;

                        int idPunto = formMapper.getId(entidad);

                        ptmDAO.eliminarRelacionesPorPunto(idPunto);
                        ptmDAO.insertarRelaciones(idPunto, prFormMapper.getMaterialesSeleccionados());
                    }
                    JOptionPane.showMessageDialog(this, "Se actualizo  correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                }
                actualizarLista();
                panelFormulario.setVisible(false);
            } catch (Exception ex) {
                ex.printStackTrace(); // Opcional: útil para depuración en consola
                JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        panelFormulario.add(new JLabel());
        panelFormulario.add(btnGuardar);
        panelFormulario.setVisible(true);
        revalidate();
        repaint();
    }


    
private void actualizarLista() {
    tablaModel.setRowCount(0);

    if (!dao.obtenerTodos().isEmpty()) {
        T ejemplo = dao.obtenerTodos().get(0);

        if (ejemplo instanceof Recompensa r) {
            tablaModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Descripción", "Puntos"});
            for (T item : dao.obtenerTodos()) {
                r = (Recompensa) item;
                tablaModel.addRow(new Object[]{
                    r.getId_recompensa(),
                    r.getNombre(),
                    r.getDescripcion(),
                    r.getPuntos_necesarios()
                });
            }

        } else if (ejemplo instanceof PuntoReciclaje p) {
            tablaModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Ubicación", "Horario", "Latitud", "Longitud"});
            for (T item : dao.obtenerTodos()) {
                p = (PuntoReciclaje) item;
                tablaModel.addRow(new Object[]{
                    p.getIdPunto(),
                    p.getNombre(),
                    p.getUbicacion(),
                    p.getHorario(),
                    p.getLatitud(),
                    p.getLongitud()
                });
            }

        } else if (ejemplo instanceof TipoMaterial tm) {
            tablaModel.setColumnIdentifiers(new String[]{"ID", "Categoría", "Puntos/kg", "CO2 reducido/kg"});
            for (T item : dao.obtenerTodos()) {
                tm = (TipoMaterial) item;
                tablaModel.addRow(new Object[]{
                    tm.getId_tipo_material(),
                    tm.getCategoria(),
                    tm.getPuntos_kg(),
                    tm.getCo2_reducido_kg()
                });
            }

        } else if (ejemplo instanceof Descuento d) {
            tablaModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Porcentaje", "Inicio", "Fin", "ID Recompensa"});
            for (T item : dao.obtenerTodos()) {
                d = (Descuento) item;
                tablaModel.addRow(new Object[]{
                    d.getId_descuento(),
                    d.getNombre_descuento(),
                    d.getPorcentaje_descuento(),
                    d.getFecha_inicio(),
                    d.getFecha_fin(),
                    d.getId_recompensa()
                });
            }
        }else if (ejemplo instanceof Usuario u) {
            tablaModel.setColumnIdentifiers(new String[]{"ID", "Nombre", "Apellido", "Correo", "Teléfono"});
            for (T item : dao.obtenerTodos()) {
                u = (Usuario) item;
                tablaModel.addRow(new Object[]{
                    u.getId(),
                    u.getNombre(),
                    u.getApellido(),
                    u.getCorreo(),
                    u.getTelefono()
                });
            }
        }


    }
}

















}
