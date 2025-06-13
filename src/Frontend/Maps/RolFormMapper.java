package Frontend.Maps;

import Backend.Modelos.Rol;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.*;


public class RolFormMapper implements FormMapper<Rol> {

    @Override
    public Map<String, JComponent> crearCampos(Rol rol) {
        Map<String, JComponent> campos = new LinkedHashMap<>();

        JTextField txtNombre = new JTextField();
        JTextField txtDescripcion = new JTextField();

        if (rol != null) {
            txtNombre.setText(rol.getNombreRol());
            txtDescripcion.setText(rol.getDescripcion());
        }

        campos.put("Nombre del Rol", txtNombre);
        campos.put("Descripción", txtDescripcion);

        return campos;
    }

    @Override
    public Rol construirDesdeCampos(Map<String, JComponent> campos) {
        Rol rol = new Rol();
        rol.setNombreRol(((JTextField) campos.get("Nombre del Rol")).getText());
        rol.setDescripcion(((JTextField) campos.get("Descripción")).getText());
        return rol;
    }

    @Override
    public int getId(Rol rol) {
        return rol.getIdRol();
    }
}
