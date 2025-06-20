

package Mockups;


import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MockupListaDeMateriales {

    public static void main(String[] args) {
        new MockupListaDeMateriales();
    }

    public MockupListaDeMateriales() {
        JFrame ventana = new JFrame("Mockup - Lista de Materiales");
        ventana.setSize(800, 450);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.setLocationRelativeTo(null);

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        // Título superior centrado
        JLabel titulo = new JLabel("Lista de Materiales", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        panelPrincipal.add(titulo, BorderLayout.NORTH);

        // Tabla con columnas
        String[] columnas = {"ID", "Material", "Tipo de Material", "Cantidad (kg)"};
        DefaultTableModel modelo = new DefaultTableModel(columnas, 10) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return true; // todas las celdas editables
            }
        };

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(25);
        tabla.setFont(new Font("Arial", Font.PLAIN, 14));
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));

        JScrollPane scroll = new JScrollPane(tabla);
        panelPrincipal.add(scroll, BorderLayout.CENTER);

        // Botón "Calcular" centrado en la parte inferior
        JPanel panelInferior = new JPanel();
        JButton botonCalcular = new JButton("Calcular");
        botonCalcular.setPreferredSize(new Dimension(150, 35));
        botonCalcular.setFont(new Font("Arial", Font.BOLD, 14));
        panelInferior.add(botonCalcular);

        panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

        ventana.add(panelPrincipal);
        ventana.setVisible(true);
    }
}
