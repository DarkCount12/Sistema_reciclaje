package Frontend.PopUp;

import Backend.DAOs.UsuarioDAO;
import Backend.Modelos.Usuario;
import Backend.Servicios.Cache;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Frontend.Home;
import Frontend.Page.PaginaPrincipal;
import Frontend.PopUp.SolicitudRespuesta.ConfirmacionPersonalizada;
import java.awt.*;
import javax.swing.*;

public class EdicionUsuario extends JPanel {

    public EdicionUsuario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        setBackground(Color.decode("#FCFCFC"));

        JLabel titulo = new JLabel("Editar Usuario");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 10)));

        String cache = Cache.obtenerCorreo();
        if (cache == null) {
            JOptionPane.showMessageDialog(this, "No se ha encontrado un usuario activo.");
            return;
        }
  //      String correoUsuario = cache.split(":")[1];
          String correoUsuario = cache;

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        Usuario usuario = usuarioDAO.obtenerUsuarioPorCorreo(correoUsuario);
        if (usuario == null) {
            JOptionPane.showMessageDialog(this, "No se encontró el usuario.");
            return;
        }

        JTextField txtNombre = new JTextField(usuario.nombre, 15);
        JTextField txtApellido = new JTextField(usuario.apellido, 15);
        JTextField txtCorreo = new JTextField(usuario.correo, 15);
        txtCorreo.setEditable(false); 
        JPasswordField txtContrasena = new JPasswordField(usuario.contrasena, 15);
        JTextField txtTelefono = new JTextField(usuario.telefono, 15);

        add(crearCampo("Nombre:", txtNombre));
        add(crearCampo("Apellido:", txtApellido));
        add(crearCampo("Correo:", txtCorreo));
        add(crearCampo("Contraseña:", txtContrasena));
        add(crearCampo("Teléfono:", txtTelefono));

        JButton btnActualizar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Actualizar", 355, 30);
        btnActualizar.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnActualizar);

        JButton btnEliminar = Estilos.crearBoton(Colores.RED, Colores.WHITE, "Eliminar Cuenta", 355, 35);
        btnEliminar.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 5)));
        add(btnEliminar);

        btnActualizar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText(); 
            String contrasena = new String(txtContrasena.getPassword());
            String telefono = txtTelefono.getText();

            boolean actualizado = usuarioDAO.actualizarUsuarioPorCorreo(correo, nombre, apellido, contrasena, telefono);

            if (actualizado) {
                JOptionPane.showMessageDialog(this, "Usuario actualizado exitosamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al actualizar el usuario.");
            }
        });

      btnEliminar.addActionListener(e -> {
            ConfirmacionPersonalizada.mostrar("¿Estás seguro de que deseas eliminar tu cuenta?", new ConfirmacionPersonalizada.ConfirmacionListener() {
                @Override
                public void onConfirmar() {
                    boolean eliminado = usuarioDAO.eliminarUsuarioPorCorreo(correoUsuario);

                    if (eliminado) {
                        JOptionPane.showMessageDialog(EdicionUsuario.this, "Cuenta eliminada exitosamente.");
                        Cache.guardarEnCache("null","null"); // sobrescribe el cache

                        // Cerrar la ventana actual
                        Window win = SwingUtilities.getWindowAncestor(EdicionUsuario.this);
                        if (win != null) {
                            win.dispose();
                        }

                        // Cerrar cualquier ventana de la página principal si está abierta
                        if (PaginaPrincipal.principal != null) {
                            PaginaPrincipal.principal.dispose();
                        }

                        // Abrir la pantalla Home
                        SwingUtilities.invokeLater(() -> {
                            Home.main(new String[0]);
                        });

                    } else {
                        JOptionPane.showMessageDialog(EdicionUsuario.this, "Error al eliminar la cuenta.");
                    }
                }

                @Override
                public void onCancelar() {
                    // No hacer nada si cancela
                }
            });
        });






    }



    private JPanel crearCampo(String etiqueta, JComponent campo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
        JLabel label = new JLabel(etiqueta);
        label.setFont(new Font("Arial", Font.PLAIN, 15));
        label.setPreferredSize(new Dimension(120, 30));
        panel.add(label, BorderLayout.WEST);
        panel.add(campo, BorderLayout.CENTER);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        panel.setAlignmentX(Component.CENTER_ALIGNMENT);
        return panel;
    }
}
