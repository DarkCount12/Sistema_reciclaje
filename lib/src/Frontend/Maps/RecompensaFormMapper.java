package Frontend.Maps;

import Backend.Modelos.Recompensa;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;

public class RecompensaFormMapper implements FormMapper<Recompensa> {
    public Map<String, JComponent> crearCampos(Recompensa r) {
        Map<String, JComponent> campos = new LinkedHashMap<>();
        JTextField nombre = new JTextField(r != null ? r.getNombre() : "");
        JTextArea descripcion = new JTextArea(r != null ? r.getDescripcion() : "");
        JTextField puntos = new JTextField(r != null ? String.valueOf(r.getPuntos_necesarios()) : "");
        campos.put("Nombre", nombre);
        campos.put("Descripción", descripcion);
        campos.put("Puntos Necesarios", puntos);
        return campos;
    }

    public Recompensa construirDesdeCampos(Map<String, JComponent> campos) {
        String nombre = ((JTextField) campos.get("Nombre")).getText();
        String descripcion = ((JTextArea) campos.get("Descripción")).getText();
        int puntos = Integer.parseInt(((JTextField) campos.get("Puntos Necesarios")).getText());
        return new Recompensa(0, nombre, descripcion, puntos);
    }

    public int getId(Recompensa r) {
        return r.getId_recompensa();
    }
}
