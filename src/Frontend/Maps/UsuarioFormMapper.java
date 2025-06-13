package Frontend.Maps;

import Backend.Modelos.Usuario;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;

public class UsuarioFormMapper implements FormMapper<Usuario> {

    @Override
    public Map<String, JComponent> crearCampos(Usuario usuario) {
        Map<String, JComponent> campos = new HashMap<>();

        JTextField campoNombre = new JTextField(usuario != null ? usuario.getNombre() : "");
        JTextField campoApellido = new JTextField(usuario != null ? usuario.getApellido() : "");
        JTextField campoCorreo = new JTextField(usuario != null ? usuario.getCorreo() : "");
        JPasswordField campoContrasena = new JPasswordField(usuario != null ? usuario.getContrasena() : "");
        JTextField campoTelefono = new JTextField(usuario != null && usuario.getTelefono() != null ? usuario.getTelefono().toString() : "");

        campos.put("Nombre", campoNombre);
        campos.put("Apellido", campoApellido);
        campos.put("Correo", campoCorreo);
        campos.put("Contraseña", campoContrasena);
        campos.put("Teléfono", campoTelefono);

        return campos;
    }

    @Override
    public Usuario construirDesdeCampos(Map<String, JComponent> campos) {
        String nombre = ((JTextField) campos.get("Nombre")).getText();
        String apellido = ((JTextField) campos.get("Apellido")).getText();
        String correo = ((JTextField) campos.get("Correo")).getText();
        String contrasena = new String(((JPasswordField) campos.get("Contraseña")).getPassword());
        String telefono = ((JTextField) campos.get("Teléfono")).getText();


        return new Usuario(nombre, apellido, correo, contrasena, telefono);
    }

    @Override
    public int getId(Usuario usuario) {
        return usuario.getId();
    }
}
