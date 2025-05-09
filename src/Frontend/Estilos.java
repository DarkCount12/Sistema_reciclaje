package Frontend;

import javax.swing.*;
import java.awt.*;

public class Estilos {
    
    // Colores base
    public static Color colorFondo = new Color(240, 248, 255); // Azul muy claro
    public static Color colorBoton = new Color(0, 123, 255); // Azul vivo
    public static Color colorTextoBoton = Color.WHITE;

    // Aplicar estilo a la ventana
    public static void aplicarEstiloVentana(JFrame ventana) {
        ventana.getContentPane().setBackground(colorFondo);
    }

    // Aplicar estilo a los botones
    public static void aplicarEstiloBoton(JButton boton) {
        boton.setBackground(colorBoton);
        boton.setForeground(colorTextoBoton);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    // Aplicar estilo a etiquetas
    public static void aplicarEstiloEtiqueta(JLabel etiqueta) {
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    // Aplicar estilo a campos de texto
    public static void aplicarEstiloCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
    }
    
    public static void aplicarEstiloCampo(JPasswordField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
    }


///////////PANELES


    public static JPanel crearPanelAmarillo(){
        JPanel panel = new JPanel();
        
        panel.setBackground(Color.YELLOW); 
        panel.setPreferredSize(new Dimension(850, 100));
          panel.setBounds(0, 0, 600, 100); 
        
        panel.setLayout(null); 
          
        JLabel logoLabel = new JLabel();
        
        ImageIcon logoIcono = new ImageIcon("imagenes\\reciclajelogo.png"); 
        Image logoImage = logoIcono.getImage();
        Image resizedImage = logoImage.getScaledInstance(87, 87, Image.SCALE_SMOOTH); 
        logoLabel.setIcon(new ImageIcon(resizedImage));
        logoLabel.setBounds(30, 0, 150, 100); 
        panel.add(logoLabel);
           
        return panel;
    }

    public static JPanel crearPanelBlanco() {
        JPanel panel = new JPanel();  
        panel.setBackground(Color.WHITE);  
        panel.setPreferredSize(new Dimension(850, 500));  
       panel.setBounds(0, 100, 850, 700);
        return panel;  
    }



    //Boton
    // crear boton
    public static JButton crearBoton(String texto) {
        JButton boton=new JButton(texto);
        
        boton.setBackground(Color.YELLOW);  
        boton.setForeground(Color.BLACK); 
        boton.setFont(new Font("Arial", Font.BOLD, 18)); 
        boton.setPreferredSize(new Dimension(170, 40)); 
         return boton;
    }









}
