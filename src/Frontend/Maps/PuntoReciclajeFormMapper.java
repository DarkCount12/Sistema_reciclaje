package Frontend.Maps;

import Backend.DAOs.PuntoTipoMaterialDAO;
import Backend.Modelos.PuntoReciclaje;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.text.SimpleDateFormat;


import Frontend.Components.MapSelectorPanel;

public class PuntoReciclajeFormMapper implements FormMapper<PuntoReciclaje> {

    private JTextField latitud;
    private JTextField longitud;
    private int[] materialesSeleccionados;

    @Override
    public Map<String, JComponent> crearCampos(PuntoReciclaje punto) {
        Map<String, JComponent> campos = new LinkedHashMap<>();

        JTextField nombre = new JTextField(punto != null ? punto.getNombre() : "");
        JTextField ubicacion = new JTextField(punto != null ? punto.getUbicacion() : "");
        latitud = new JTextField(punto != null ? String.valueOf(punto.getLatitud()) : "");
        longitud = new JTextField(punto != null ? String.valueOf(punto.getLongitud()) : "");

        // Creamos los spinners para horario inicio y fin
        JSpinner horaInicio = new JSpinner(new SpinnerDateModel());
        JSpinner horaFin = new JSpinner(new SpinnerDateModel());
        horaInicio.setEditor(new JSpinner.DateEditor(horaInicio, "HH:mm:ss"));
        horaFin.setEditor(new JSpinner.DateEditor(horaFin, "HH:mm:ss"));

        // Si editamos un punto existente, cargamos el horario para separar en dos horas
        if (punto != null && punto.getHorario() != null) {
            String[] horas = punto.getHorario().split(" - ");
            if (horas.length == 2) {
                try {
                    java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
                    horaInicio.setValue(sdf.parse(horas[0].trim()));
                    horaFin.setValue(sdf.parse(horas[1].trim()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        campos.put("Nombre", nombre);
        campos.put("Ubicación", ubicacion);
        campos.put("Hora de Apertura", horaInicio);
        campos.put("Hora de Cierre", horaFin);
        campos.put("Latitud", latitud);
        campos.put("Longitud", longitud);

        // Botón para seleccionar en mapa
        JButton seleccionarMapaBtn = new JButton("Seleccionar en Mapa");
        seleccionarMapaBtn.addActionListener(e -> abrirMapa());
        campos.put("Seleccionar en Mapa", seleccionarMapaBtn);

        // Botón para seleccionar materiales permitidos
        JButton seleccionarMaterialesBtn = new JButton("Seleccionar Materiales Permitidos");
        seleccionarMaterialesBtn.addActionListener(e -> abrirSelectorMateriales(punto));
        campos.put("Materiales Permitidos", seleccionarMaterialesBtn);

        return campos;
    }



    private void abrirMapa() {
        JDialog mapaDialog = new JDialog();
        mapaDialog.setTitle("Seleccionar Punto en Mapa");
        mapaDialog.setSize(600, 400);
        mapaDialog.setLocationRelativeTo(null);

        MapSelectorPanel panelMapa = new MapSelectorPanel();

        panelMapa.setMapClickListener(position -> {
            double lat = position.getLatitude();
            double lon = position.getLongitude();
            latitud.setText(String.valueOf(lat));
            longitud.setText(String.valueOf(lon));
        });

        mapaDialog.add(panelMapa);
        mapaDialog.setModal(true);
        mapaDialog.setVisible(true);
    }





private void abrirSelectorMateriales(PuntoReciclaje punto)  {

        PuntoTipoMaterialDAO daoRelacion = new PuntoTipoMaterialDAO();
        List<Integer> materialesPermitidos = new ArrayList<>();

        if (punto != null && punto.getIdPunto() != 0) { // Si estamos editando
            materialesPermitidos = daoRelacion.obtenerMaterialesPorPunto(punto.getIdPunto());
        }



    JDialog dialog = new JDialog();
    dialog.setTitle("Seleccionar Materiales Permitidos");
    dialog.setSize(400, 300);
    dialog.setLocationRelativeTo(null);
    dialog.setModal(true);

    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    // Usamos el DAO directamente para obtener los materiales
    Backend.DAOs.TipoMaterial2DAO dao = new Backend.DAOs.TipoMaterial2DAO();
    List<Backend.Modelos.TipoMaterial> materiales = dao.obtenerTodos();

    List<JCheckBox> checkBoxes = new ArrayList<>();
    for (Backend.Modelos.TipoMaterial material : materiales) {
    JCheckBox checkBox = new JCheckBox(material.getCategoria());
    checkBox.setActionCommand(String.valueOf(material.getId_tipo_material()));

    if (materialesPermitidos.contains(material.getId_tipo_material())) {
        checkBox.setSelected(true);
    }

    panel.add(checkBox);
    checkBoxes.add(checkBox);
}


    JButton aceptarBtn = new JButton("Aceptar");
    aceptarBtn.addActionListener(e -> {
        List<Integer> seleccionados = new ArrayList<>();
        for (JCheckBox cb : checkBoxes) {
            if (cb.isSelected()) {
                seleccionados.add(Integer.parseInt(cb.getActionCommand()));
            }
        }
        materialesSeleccionados = seleccionados.stream().mapToInt(i -> i).toArray();
        dialog.dispose();
    });

    dialog.add(new JScrollPane(panel), "Center");
    dialog.add(aceptarBtn, "South");
    dialog.setVisible(true);
}


    public int[] getMaterialesSeleccionados() {
        return materialesSeleccionados;
    }

    
        @Override
        public PuntoReciclaje construirDesdeCampos(Map<String, JComponent> campos) {
            String nombre = ((JTextField) campos.get("Nombre")).getText().trim();
            String ubicacion = ((JTextField) campos.get("Ubicación")).getText().trim();
            Date horaInicio = (Date) ((JSpinner) campos.get("Hora de Apertura")).getValue();
            Date horaFin = (Date) ((JSpinner) campos.get("Hora de Cierre")).getValue();

            java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm");
            String horario = sdf.format(horaInicio) + " - " + sdf.format(horaFin);

            double lat = Double.parseDouble(((JTextField) campos.get("Latitud")).getText());
            double lon = Double.parseDouble(((JTextField) campos.get("Longitud")).getText());

            if (nombre.isEmpty() || ubicacion.isEmpty() || horario.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Por favor, complete todos los campos.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                return null;
            }

            return new PuntoReciclaje(0, nombre, ubicacion, horario, lat, lon);
        }


    @Override
    public int getId(PuntoReciclaje punto) {
        return punto.getIdPunto();
    }
}
