package Frontend;
import Backend.UsuarioDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Usuario {




    public static void main(String[] args) {
    Usuario a= new Usuario();
        a.mostrar();
    }
        public void mostrar(){
        // Crear la ventana
        JFrame ventana = new JFrame("Formulario de Registro");
        ventana.setSize(400, 300);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);
        ventana.setLayout(new GridLayout(7, 2));
 
        JLabel titulo = new JLabel("Registro Usuario");
        titulo.setHorizontalAlignment(JLabel.CENTER); // Centrar el texto
        titulo.setFont(new Font("Arial", Font.BOLD, 20)); // Cambiar tamaño de letra y poner en negrita


        // Campos de texto
        JTextField txtNombre = new JTextField();
        JTextField txtApellido = new JTextField();
        JTextField txtCorreo = new JTextField();
        JPasswordField txtContrasena = new JPasswordField();
        JTextField txtTelefono = new JTextField();

        //Procesar el registro :)
        JButton btnRegistrar = new JButton("Registrar");

        ventana.add(titulo);
        ventana.add(new JLabel());//Espacio para que que el titulo se centre
        ventana.add(new JLabel("Nombre:"));
        ventana.add(txtNombre);
        ventana.add(new JLabel("Apellido:"));
        ventana.add(txtApellido);
        ventana.add(new JLabel("Correo:"));
        ventana.add(txtCorreo);
        ventana.add(new JLabel("Contraseña:"));
        ventana.add(txtContrasena);
        ventana.add(new JLabel("Teléfono:"));
        ventana.add(txtTelefono);
        ventana.add(btnRegistrar);




        // Acción del botón
        btnRegistrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtener los valores ingresados por el usuario
                String nombre = txtNombre.getText();
                String apellido = txtApellido.getText();
                String correo = txtCorreo.getText();
                String contrasena = new String(txtContrasena.getPassword());
                String telefono = txtTelefono.getText();




                // Llamar al método de registro y registrar mandandole los datos 
                UsuarioDAO usuarioDAO = new UsuarioDAO();
                boolean registrado = usuarioDAO.registrarUsuario(nombre, apellido, correo, contrasena, telefono);



                // Si se ha registrado e limpia los datos del formulario :v
                if (registrado) {
                    JOptionPane.showMessageDialog(ventana, "Usuario registrado exitosamente.");
                    // Limpiar los campos
                    txtNombre.setText("");
                    txtApellido.setText("");
                    txtCorreo.setText("");
                    txtContrasena.setText("");
                    txtTelefono.setText("");
                } else {
                    JOptionPane.showMessageDialog(ventana, "Error al registrar el usuario.");
                }
            }
        });
        ventana.setVisible(true);
    }
}