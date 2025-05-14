package Backend.Servicios;

import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.Usuario;
import Backend.Utils.Constantes;

import java.util.List;

public class UsuarioServicio {

    private static String estado;
    private UsuarioDAO usuarioDAO;

    public UsuarioServicio() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public boolean autenticarUsuario(String correo, String contrasena) {
        List<Usuario> usuarios = usuarioDAO.obtenerTodosLosUsuarios();

        for (Usuario usuario : usuarios) {
            if (usuario.correo.equals(correo)) {
                if (usuario.contrasena.equals(contrasena)) {
                    activarEstado(correo);
                    return true;
                }
            }
        }

        return false;
    }

    public static String getEstado() {
        return estado;
    }

    public static void activarEstado(String correoUsuario) {
        estado = Constantes.ESTADO_ACTIVO;
        Cache.guardarEnCache("Usuario:" + correoUsuario);
    }

    public static void desactivarEstado() {
        estado = Constantes.ESTADO_INACTIVO;
        Cache.guardarEnCache("Usuario:null");
    }
}
