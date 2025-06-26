package Frontend.PopUp;

import Backend.DAOs.UsuarioDAO;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import java.awt.*;
import javax.swing.*;

public class RegistroUsuario extends JPanel {

    public RegistroUsuario(int rol) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        setBackground(Color.decode("#FCFCFC"));

        JLabel titulo = new JLabel("Registro de Usuario");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField txtNombre = new JTextField(15);
        JTextField txtApellido = new JTextField(15);
        JTextField txtCorreo = new JTextField(15);
        JPasswordField txtContrasena = new JPasswordField(15);
        JTextField txtTelefono = new JTextField(15);

        add(crearCampo("Nombre:", txtNombre));
        add(crearCampo("Apellido:", txtApellido));
        add(crearCampo("Correo:", txtCorreo));
        add(crearCampo("Contraseña:", txtContrasena));
        add(crearCampo("Teléfono:", txtTelefono));

        JButton btnRegistrar = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Registrar", 155, 30);
        btnRegistrar.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnRegistrar);

        btnRegistrar.addActionListener(e -> {
            String nombre = txtNombre.getText();
            String apellido = txtApellido.getText();
            String correo = txtCorreo.getText();
            String contrasena = new String(txtContrasena.getPassword());
            String telefono = txtTelefono.getText();

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean registrado = usuarioDAO.registrarUsuario(nombre, apellido, correo, contrasena, telefono,rol);

            if (registrado) {
                JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente.");
                txtNombre.setText("");
                txtApellido.setText("");
                txtCorreo.setText("");
                txtContrasena.setText("");
                txtTelefono.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error al registrar el usuario,revise que lleno los campos correctamente.");
            }
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
