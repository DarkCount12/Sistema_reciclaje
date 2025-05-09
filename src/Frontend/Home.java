package Frontend;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class Home {
    

public static void main(String[] args) {

    JFrame principal =new JFrame("Ventana Principal");
    principal.setSize(850,600);
    principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    principal.setLocationRelativeTo(null);
   
    JPanel panelAmarillo=Estilos.crearPanelAmarillo();
    JPanel panelBlanco = Estilos.crearPanelBlanco();
    panelAmarillo.setLayout(null);
    panelBlanco.setLayout(null);

   //botones
   JButton botonLogin = Estilos.crearBoton("Iniciar Sesion");
   JButton botonRegistro =Estilos.crearBoton("Registrarse");

   botonLogin.setBounds(110, 260, botonRegistro.getPreferredSize().width, botonRegistro.getPreferredSize().height);
 //botonLogin.addActionListener(new ActionListener() {
//    @Override
  //  public void actionPerformed(ActionEvent e) {
 //      Login login = new Login();

        // Verificar si el usuario inicia sesión correctamente
 //       login.addLoginSuccessListener(new Login.LoginSuccessListener() {
 //       @Override
 //        public void onLoginSuccess() {
 //        dispose(); // Cierra la ventana Home
 //        }});
 //}});

 botonRegistro.setBounds(110, 320, botonRegistro.getPreferredSize().width, botonRegistro.getPreferredSize().height);

 botonRegistro.addActionListener(new ActionListener() {
   @Override
   public void actionPerformed(ActionEvent e) {

     String[] opciones = {"Usuario", "Administrador"};
     String seleccion = (String) JOptionPane.showInputDialog(
             null,
             "Seleccione el tipo de usuario:",
             "Registro",
             JOptionPane.QUESTION_MESSAGE,
             null,
             opciones,
             opciones[0]
     );

     // Abrir la ventana de registro correspondiente
     if (seleccion != null) {
         switch (seleccion) {
             case "Usuario":
                Usuario a= new Usuario();
                a.mostrar();
                 break;
             case "Administrador":
                 new Usuario();
                 break;
           }
       }
 }});

 panelBlanco.add(botonLogin);
 panelBlanco.add(botonRegistro);

principal.add(panelBlanco);
principal.add(panelAmarillo);


 // imagen
 ImageIcon imagenIcono = new ImageIcon( "imagenes\\logoprincipal.png" ); 
 Image imagen = imagenIcono.getImage();

 imagen = imagen.getScaledInstance(425, 500, Image.SCALE_SMOOTH); 
 ImageIcon imagenRedimensionada = new ImageIcon(imagen);
 JLabel etiquetaImagen = new JLabel(imagenRedimensionada);
 etiquetaImagen.setBounds(425, 0, 425, 500); 
 panelBlanco.add(etiquetaImagen);

 // titulo
 JLabel label3 = new JLabel("BIENVENIDO");
 label3.setForeground(Color.BLACK);
 label3.setFont(new Font("Arial", Font.BOLD, 35));
  label3.setBounds(100, 20, 250, 50);
  panelBlanco.add(label3);

  //parrafo

  JTextArea textArea = new JTextArea("Únete a una nueva forma de reciclar .\n" + //
            "Gana recompensas mientras ayudas a cuidar el planeta.\n" + //
            "Reciclar nunca fue tan fácil, útil y motivador.");
textArea.setBounds(60, 100, 300, 190);  
textArea.setFont(new Font("Arial", Font.BOLD, 16)); 
textArea.setForeground(Color.BLACK);  
textArea.setBackground(Color.WHITE);   
textArea.setLineWrap(true);           
textArea.setWrapStyleWord(true);      
textArea.setEditable(false);         

panelBlanco.add(textArea); 





principal.setVisible(true);


}
}
