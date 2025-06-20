package Frontend.PopUp;
import Backend.DAOs.PuntoRecoleccionDAO;
import java.awt.*;
import java.sql.Timestamp;
import java.util.Map;
import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.table.DefaultTableModel;


public class ListadeMateriales{


public static void main(String[] args) {
    new ListadeMateriales(1, 2);
}


public  ListadeMateriales(int idU, int idP) {
    JFrame ventana = new JFrame("Mockup - Lista de Materiales");
    ventana.setSize(800, 450);
    ventana.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    ventana.setLocationRelativeTo(null);

    JPanel panelPrincipal = new JPanel(new BorderLayout());

    JLabel titulo = new JLabel("Lista de Materiales", SwingConstants.CENTER);
    titulo.setFont(new Font("Arial", Font.BOLD, 24));
    titulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
    panelPrincipal.add(titulo, BorderLayout.NORTH);

    String[] columnas = {"ID", "Material", "Tipo de Material", "Cantidad (kg)"};
    DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return column != 0; // ID no editable
        }
    };



    // DAO para obtener datos
PuntoRecoleccionDAO dao = new PuntoRecoleccionDAO();
// Cargar tipos de material en el JComboBox
Map<String, Integer> tiposMaterial = dao.obtenerMaterialesPorPunto2(idP);

JComboBox<String> comboMateriales = new JComboBox<>();
for (Map.Entry<String, Integer> entry : tiposMaterial.entrySet()) {
    String categoria = entry.getKey();
    int id = entry.getValue();
    comboMateriales.addItem(id + " - " + categoria); // Mostrar ambos
}

// Crear tabla
JTable tabla = new JTable(modelo);
tabla.setRowHeight(25);
tabla.setFont(new Font("Arial", Font.PLAIN, 14));
tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));



// Asociar ComboBox a columna "Tipo de Material"
tabla.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(comboMateriales));


    JScrollPane scroll = new JScrollPane(tabla);
    panelPrincipal.add(scroll, BorderLayout.CENTER);

    JPanel panelInferior = new JPanel();
    JButton botonCalcular = new JButton("Guardar");
    botonCalcular.setPreferredSize(new Dimension(150, 35));
    botonCalcular.setFont(new Font("Arial", Font.BOLD, 14));
    panelInferior.add(botonCalcular);
    panelPrincipal.add(panelInferior, BorderLayout.SOUTH);

    ventana.add(panelPrincipal);
    ventana.setVisible(true);


    // Variable para autoincrementar ID
    final int[] idCounter = {1};

    // Método para agregar una fila vacía con ID automático
    Runnable agregarFila = () -> {
        modelo.addRow(new Object[]{idCounter[0]++, "", "", ""});
    };

    // Agregar la primera fila
    agregarFila.run();

            // Escuchar cambios en la tabla para agregar filas nuevas dinámicamente
            modelo.addTableModelListener(e -> {
                if (e.getType() == TableModelEvent.UPDATE) {
                    int lastRow = modelo.getRowCount() - 1;

                    // Verificamos si la última fila tiene al menos una celda no vacía (excepto ID)
                    boolean filaUltimaLlena = false;
                    for (int col = 1; col < modelo.getColumnCount(); col++) {
                        Object valor = modelo.getValueAt(lastRow, col);
                        if (valor != null && !valor.toString().trim().isEmpty()) {
                            filaUltimaLlena = true;
                            break;
                        }
                    }
                    // Si la última fila tiene datos, agregamos una nueva fila vacía para seguir agregando
                    if (filaUltimaLlena) {
                        agregarFila.run();
                    }
                }
            });




// Acción del botón Calcular
botonCalcular.addActionListener(e -> {
    // Guardar cualquier edición en curso
    if (tabla.isEditing()) {
        tabla.getCellEditor().stopCellEditing();
    }

    for (int i = 0; i < modelo.getRowCount(); i++) {
        int celdasLlenas = 0;
        int celdasTotales = modelo.getColumnCount() - 1; // Excluye columna ID

        for (int col = 1; col < modelo.getColumnCount(); col++) {
            Object valor = modelo.getValueAt(i, col);
            if (valor != null && !valor.toString().trim().isEmpty()) {
                celdasLlenas++;
            }
        }

        // Validación de fila incompleta
        if (celdasLlenas > 0 && celdasLlenas < celdasTotales) {
            JOptionPane.showMessageDialog(ventana,
                    "La fila " + (i + 1) + " está incompleta. Por favor, llénala completamente o déjala vacía.",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validación de número en la columna "Cantidad (kg)" (columna 3)
        if (celdasLlenas == celdasTotales) {
            Object cantidad = modelo.getValueAt(i, 3);
            try {
                if (cantidad == null || cantidad.toString().trim().isEmpty()) throw new NumberFormatException();
                double valor = Double.parseDouble(cantidad.toString());
                if (valor < 0) throw new NumberFormatException();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(ventana,
                        "La fila " + (i + 1) + " contiene un valor inválido en la columna 'Cantidad (kg)'.",
                        "Error de validación",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    }


/////////
 Timestamp ahora = new Timestamp(System.currentTimeMillis());
        dao.insertarReciclaje(ahora, idU, idP);

                for (int i = 0; i < modelo.getRowCount(); i++) {
                    Object materialObj = modelo.getValueAt(i, 1);
                    Object tipoMaterialObj = modelo.getValueAt(i, 2);
                    Object cantidadObj = modelo.getValueAt(i, 3);

                    if (materialObj == null || tipoMaterialObj == null || cantidadObj == null) continue;

String material = materialObj.toString().trim();
String tipoMaterialTexto = tipoMaterialObj.toString().trim();
String cantidadTexto = cantidadObj.toString().trim();

// Ignorar fila vacía o incompleta (al menos una columna vacía)
if (material.isEmpty() || tipoMaterialTexto.isEmpty() || cantidadTexto.isEmpty()) continue;

try {
    int idTipoMaterial = Integer.parseInt(tipoMaterialTexto.split(" - ")[0]);
    double cantidad = Double.parseDouble(cantidadTexto);

    // Insertar solo si el material no está vacío
    if (!material.isEmpty()) {
       
        int idreciclaje = dao.obtenerUltimoIdReciclaje();
        dao.insertarMaterial(idreciclaje, material, cantidad, idTipoMaterial);
    }
} catch (NumberFormatException ex) {
    JOptionPane.showMessageDialog(ventana,
        "La fila " + (i + 1) + " contiene valores inválidos.",
        "Error de validación",
        JOptionPane.ERROR_MESSAGE);
    return;
}

                }

                JOptionPane.showMessageDialog(ventana, "Materiales registrados correctamente.");
                ventana.dispose();

});




}

}