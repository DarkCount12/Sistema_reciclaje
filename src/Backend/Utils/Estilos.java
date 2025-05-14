package Backend.Utils;

import java.awt.*;
import javax.swing.*;

public class Estilos {

    public static Color colorFondo = new Color(240, 248, 255);
    public static Color colorBoton = new Color(0, 123, 255); 
    public static Color colorTextoBoton = Color.WHITE;

    public static void aplicarEstiloVentana(JFrame ventana) {
        ventana.getContentPane().setBackground(colorFondo);
    }

    public static void aplicarEstiloBoton(JButton boton) {
        boton.setBackground(colorBoton);
        boton.setForeground(colorTextoBoton);
        boton.setFocusPainted(false);
        boton.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public static void aplicarEstiloEtiqueta(JLabel etiqueta) {
        etiqueta.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static void aplicarEstiloCampo(JTextField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static void aplicarEstiloCampo(JPasswordField campo) {
        campo.setFont(new Font("Arial", Font.PLAIN, 14));
    }

    public static JPanel crearPanelAmarillo() {
        JPanel panel = new JPanel();

        panel.setBackground(new Color(255, 255, 255, 128)); 
        panel.setOpaque(false); 
        panel.setPreferredSize(new Dimension(850, 100));
        panel.setBounds(100, 0, 600, 100);

        panel.setLayout(null);

        JLabel logoLabel = new JLabel();

        ImageIcon logoIcono = new ImageIcon("imagenes\\logo_sistema.png");
        Image logoImage = logoIcono.getImage();
        Image resizedImage = logoImage.getScaledInstance(55, 55, Image.SCALE_SMOOTH);
        logoLabel.setIcon(new ImageIcon(resizedImage));
        logoLabel.setBounds(20, -7, 150, 100);

        JLabel sistema = new JLabel("Sistema de Reciclaje");
        sistema.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        sistema.setAlignmentX(Component.CENTER_ALIGNMENT);
        sistema.setForeground(Color.decode(Colores.WHITE));
        sistema.setBounds(85, -9, 300, 100);

        panel.add(sistema);
        panel.add(logoLabel);

        return panel;
    }


    public static JButton crearBotonConImagen(int ancho, int altura, String rutaImagen) {
        ImageIcon iconoOriginal = new ImageIcon(rutaImagen);
        Image imagenOriginal = iconoOriginal.getImage();

        Image imagenRedimensionada = imagenOriginal.getScaledInstance(ancho, altura, Image.SCALE_SMOOTH);
        ImageIcon iconoRedimensionado = new ImageIcon(imagenRedimensionada);

        JButton boton = new JButton(iconoRedimensionado);

        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
        boton.setFocusPainted(false);
        boton.setMargin(new Insets(0, 0, 0, 0));
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return boton;
    }

    public static JPanel crearPanelBlanco() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255, 128)); 
        panel.setOpaque(false); 
        panel.setPreferredSize(new Dimension(850, 500));
        panel.setBounds(0, 100, 850, 400);
        return panel;
    }

    public static JButton crearBoton(String hexColorFondo, String hexColorTexto, String texto, int anchoBtn, int altoBtn) {
        JButton boton = new JButton(texto);

        boton.setBackground(Color.decode(hexColorFondo));
        boton.setForeground(Color.decode(hexColorTexto));
        boton.setFont(new Font("Arial", Font.PLAIN, 15));
        boton.setPreferredSize(new Dimension(anchoBtn, altoBtn));
        boton.setMaximumSize(new Dimension(anchoBtn, altoBtn)); 
        boton.setMinimumSize(new Dimension(anchoBtn, altoBtn)); 
        boton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boton.setFocusPainted(false);

        return boton;
    }
}
