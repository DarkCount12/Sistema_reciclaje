package Frontend.Maps;

import Backend.DAOs.Recompensa2DAO;
import Backend.Modelos.Descuento;
import Backend.Modelos.Recompensa;
import java.util.*;
import javax.swing.*;

public class DescuentoFormMapper implements FormMapper<Descuento> {

    private Map<String, Integer> recompensaMap = new HashMap<>(); // nombre -> id

    @Override
    public Map<String, JComponent> crearCampos(Descuento d) {
        Map<String, JComponent> campos = new LinkedHashMap<>();

        JTextField nombre = new JTextField(d != null ? d.getNombre_descuento() : "");
        JTextField porcentaje = new JTextField(d != null ? String.valueOf(d.getPorcentaje_descuento()) : "");

        // Spinner para fecha inicio
        JSpinner inicio = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorInicio = new JSpinner.DateEditor(inicio, "yyyy-MM-dd");
        inicio.setEditor(editorInicio);
        if (d != null && d.getFecha_inicio() != null) {
            inicio.setValue(d.getFecha_inicio());
        }

        // Spinner para fecha fin
        JSpinner fin = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editorFin = new JSpinner.DateEditor(fin, "yyyy-MM-dd");
        fin.setEditor(editorFin);
        if (d != null && d.getFecha_fin() != null) {
            fin.setValue(d.getFecha_fin());
        }

        // Obtener recompensas disponibles
        Recompensa2DAO recompensaDAO = new Recompensa2DAO();
        List<Recompensa> recompensas = recompensaDAO.obtenerTodos();

        JComboBox<String> comboRecompensas = new JComboBox<>();
        for (Recompensa r : recompensas) {
            comboRecompensas.addItem(r.getNombre());
            recompensaMap.put(r.getNombre(), r.getId_recompensa());
        }

        // Si estamos editando, seleccionar la recompensa correspondiente
        if (d != null) {
            for (Map.Entry<String, Integer> entry : recompensaMap.entrySet()) {
                if (entry.getValue().equals(d.getId_recompensa())) {
                    comboRecompensas.setSelectedItem(entry.getKey());
                    break;
                }
            }
        }

        campos.put("Nombre", nombre);
        campos.put("Porcentaje Descuento", porcentaje);
        campos.put("Fecha Inicio", inicio);
        campos.put("Fecha Fin", fin);
        campos.put("Recompensa", comboRecompensas); // Cambiamos el campo

        return campos;
    }

    @Override
    public Descuento construirDesdeCampos(Map<String, JComponent> campos) {
        Descuento d = new Descuento();

        try {
            d.setNombre_descuento(((JTextField) campos.get("Nombre")).getText());
            d.setPorcentaje_descuento(Double.parseDouble(((JTextField) campos.get("Porcentaje Descuento")).getText()));

            Date fechaInicio = (Date) ((JSpinner) campos.get("Fecha Inicio")).getValue();
            Date fechaFin = (Date) ((JSpinner) campos.get("Fecha Fin")).getValue();

            if (fechaInicio == null || fechaFin == null) {
                throw new IllegalArgumentException("Debes seleccionar ambas fechas.");
            }

            d.setFecha_inicio(fechaInicio);
            d.setFecha_fin(fechaFin);

            // Obtener el id de la recompensa seleccionada
            JComboBox<String> combo = (JComboBox<String>) campos.get("Recompensa");
            String recompensaSeleccionada = (String) combo.getSelectedItem();
            Integer idRecompensa = recompensaMap.get(recompensaSeleccionada);
            d.setId_recompensa(idRecompensa);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al convertir los datos: " + e.getMessage());
        }

        return d;
    }

    @Override
    public int getId(Descuento d) {
        return d.getId_descuento();
    }
}
