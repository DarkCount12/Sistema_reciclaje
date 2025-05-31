package Frontend.Maps;

import Backend.DAOs.CrudDAO;
import java.awt.*;
import java.util.Map; // Para Map<String, JComponent>
import javax.swing.*;

public class CrudPanel<T> extends JPanel {
    private CrudDAO<T> dao;
    private FormMapper<T> formMapper;

    private JComboBox<T> comboItems;
    private JButton btnAgregar, btnActualizar, btnEliminar;
    private JPanel panelFormulario;

    public CrudPanel(CrudDAO<T> dao, FormMapper<T> formMapper) {
        this.dao = dao;
        this.formMapper = formMapper;
        inicializarUI();
    }

    private void inicializarUI() {
        setLayout(new BorderLayout());

        comboItems = new JComboBox<>();
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

        add(comboItems, BorderLayout.NORTH);
        add(acciones, BorderLayout.CENTER);
        add(panelFormulario, BorderLayout.SOUTH);

        btnAgregar.addActionListener(e -> mostrarFormulario(null, "add"));
        btnActualizar.addActionListener(e -> {
            T seleccionado = (T) comboItems.getSelectedItem();
            if (seleccionado != null)
                mostrarFormulario(seleccionado, "update");
        });
        btnEliminar.addActionListener(e -> {
            T seleccionado = (T) comboItems.getSelectedItem();
            if (seleccionado != null) {
                int respuesta = JOptionPane.showConfirmDialog(this, "¿Estás seguro de eliminar?", "Confirmación", JOptionPane.YES_NO_OPTION);
                if (respuesta == JOptionPane.YES_OPTION) {
                    dao.eliminar(formMapper.getId(seleccionado));
                    actualizarLista();
                    JOptionPane.showMessageDialog(this, "Recompensa eliminada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
                    dao.insertar(nuevo);
                    JOptionPane.showMessageDialog(this, "Recompensa añadida correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    dao.actualizar(formMapper.getId(entidad), nuevo);
                    JOptionPane.showMessageDialog(this, "Recompensa actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
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
        comboItems.removeAllItems();
        for (T item : dao.obtenerTodos()) comboItems.addItem(item);
    }
}
