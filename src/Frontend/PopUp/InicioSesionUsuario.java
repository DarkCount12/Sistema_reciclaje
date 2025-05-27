package Frontend.PopUp;

import Backend.DAOs.UsuarioDAO;
import Backend.Servicios.UsuarioServicio;
import Backend.Utils.Colores;
import Backend.Utils.Estilos;
import Frontend.Home;
import Frontend.Page.PaginaAdministrador;
import Frontend.Page.PaginaPrincipal;
import Frontend.Page.PaginaRecolector;
import java.awt.*;
import javax.swing.*;

public class InicioSesionUsuario extends JPanel {

    public InicioSesionUsuario() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));
        setBackground(Color.decode("#FCFCFC"));

        JLabel titulo = new JLabel("Login de Usuario");
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        titulo.setForeground(Color.BLACK);
        add(titulo);
        add(Box.createRigidArea(new Dimension(0, 10)));

        JTextField txtCorreo = new JTextField(15);
        JPasswordField txtContrasena = new JPasswordField(15);

        add(crearCampo("Correo:", txtCorreo));
        add(crearCampo("Contraseña:", txtContrasena));

        JButton btnLogin = Estilos.crearBoton(Colores.YELLOW, Colores.BLACK, "Iniciar Sesión", 155, 30);
        btnLogin.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(btnLogin);

btnLogin.addActionListener(e -> {
    String correo = txtCorreo.getText();
    String contrasena = new String(txtContrasena.getPassword());
    UsuarioDAO r=new UsuarioDAO();
    UsuarioServicio usuarioServicio = new UsuarioServicio();
    boolean loginExitoso = usuarioServicio.autenticarUsuario(correo, contrasena);

    if (loginExitoso) {
        String rol = r.obtenerRolPorCorreo(correo); 
        UsuarioServicio.activarEstado(correo,rol);

        JOptionPane.showMessageDialog(this, "Bienvenido, " + correo);

        txtCorreo.setText("");
        txtContrasena.setText("");

        Timer timer = new Timer(10, evt -> {
            Window win = SwingUtilities.getWindowAncestor(this);
            if (win != null) {
                win.dispose();
                PaginaPrincipal.principal.dispose();
            }

            SwingUtilities.invokeLater(() -> {
                if ("recolector".equalsIgnoreCase(rol)) {
                    new PaginaRecolector();
                } else if("admin".equalsIgnoreCase(rol) ){
                    new PaginaAdministrador();
                } else {
                    Home.main(new String[0]);
                }
            });
        });
        timer.setRepeats(false);
        timer.start();

    } else {
        JOptionPane.showMessageDialog(this, "Error al iniciar sesión. Verifique sus credenciales.");
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
