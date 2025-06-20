package Frontend.Page.Secciones;

import java.awt.*;
import java.util.Arrays;
import javax.swing.*;

public class ReportesAdicionales {

    public static JPanel crearPanelReportesAdicionales() {
        JPanel panelContenedor = new JPanel(new BorderLayout());
        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Configuración de fuentes
        Font fuenteTitulo = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
        Font fuenteSubtitulo = new Font("Arial Rounded MT Bold", Font.BOLD, 18);
        Font fuenteTexto = new Font("Arial", Font.PLAIN, 14);

        // Título principal
        JLabel tituloPrincipal = new JLabel("Reportes Adicionales");
        tituloPrincipal.setFont(fuenteTitulo);
        tituloPrincipal.setBounds(350, 10, 400, 40);
        panelPrincipal.add(tituloPrincipal);

        int yPosition = 60; // Posición vertical inicial

        // 1. Reporte de Puntos de Reciclaje por Tipo de Material
        panelPrincipal = crearReportePuntosMaterial(panelPrincipal, yPosition, fuenteSubtitulo, fuenteTexto);
        yPosition = obtenerNuevaPosicionY(panelPrincipal) + 30;

        // 2. Reporte de Puntos por Usuario (Ranking)
        panelPrincipal = crearReporteRankingUsuarios(panelPrincipal, yPosition, fuenteSubtitulo, fuenteTexto);
        yPosition = obtenerNuevaPosicionY(panelPrincipal) + 30;

        // 3. Reporte de Canjes y Beneficios
        panelPrincipal = crearReporteCanjesBeneficios(panelPrincipal, yPosition, fuenteSubtitulo, fuenteTexto);
        yPosition = obtenerNuevaPosicionY(panelPrincipal) + 30;

        // 4. Reporte de Nuevos Registros de Reciclaje
        panelPrincipal = crearReporteNuevosRegistros(panelPrincipal, yPosition, fuenteSubtitulo, fuenteTexto);

        // Configuración del ScrollPane
        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        panelContenedor.add(scrollPane, BorderLayout.CENTER);
        
        // Establecer tamaño preferido del panel principal
        panelPrincipal.setPreferredSize(new Dimension(900, obtenerNuevaPosicionY(panelPrincipal) + 50));
        
        return panelContenedor;
    }

    private static int obtenerNuevaPosicionY(JPanel panel) {
        if (panel.getComponentCount() == 0) return 0;
        Component lastComponent = panel.getComponent(panel.getComponentCount() - 1);
        return lastComponent.getY() + lastComponent.getHeight();
    }

    // ------------------------------------------------------------
    // REPORTE 5: Puntos de Reciclaje por Tipo de Material
    // ------------------------------------------------------------
    private static JPanel crearReportePuntosMaterial(JPanel panel, int y, Font fuenteSubtitulo, Font fuenteTexto) {
        // Título del reporte
        JLabel tituloReporte = new JLabel("5. Puntos de Reciclaje por Tipo de Material");
        tituloReporte.setFont(fuenteSubtitulo);
        tituloReporte.setBounds(20, y, 500, 30);
        panel.add(tituloReporte);
        y += 40;

        // Datos de ejemplo
        String[] materiales = {"Plástico", "Vidrio", "Metal", "Papel", "Orgánico"};
        int[] puntos = {120, 85, 110, 75, 90};

        // Gráfico de barras
        BarChart grafico = new BarChart(puntos, false, materiales);
        grafico.setBounds(50, y, 800, 200);
        panel.add(grafico);
        y += 220;

        // Tabla de datos
        String[] columnas = {"Material", "Puntos Generados"};
        String[] puntosStr = Arrays.stream(puntos).mapToObj(String::valueOf).toArray(String[]::new);
        
        TablaSimple tabla = new TablaSimple(columnas, materiales, puntosStr);
        tabla.setBounds(150, y, 600, (materiales.length * 25) + 30);
        panel.add(tabla);
        y += (materiales.length * 25) + 50;

        // Conclusión
        int totalPuntos = Arrays.stream(puntos).sum();
        JLabel conclusion = new JLabel("Total de puntos generados: " + totalPuntos + " pts");
        conclusion.setFont(fuenteTexto);
        conclusion.setBounds(50, y, 300, 25);
        panel.add(conclusion);

        return panel;
    }

    // ------------------------------------------------------------
    // REPORTE 6: Puntos por Usuario (Ranking)
    // ------------------------------------------------------------
    private static JPanel crearReporteRankingUsuarios(JPanel panel, int y, Font fuenteSubtitulo, Font fuenteTexto) {
        JLabel tituloReporte = new JLabel("6. Ranking de Usuarios por Puntos");
        tituloReporte.setFont(fuenteSubtitulo);
        tituloReporte.setBounds(20, y, 500, 30);
        panel.add(tituloReporte);
        y += 40;

        // Datos de ejemplo
        String[] usuarios = {"Ana Gómez", "Carlos Ruiz", "María López", "Juan Pérez", "Luisa Martínez"};
        String[] emails = {"ana@mail.com", "carlos@mail.com", "maria@mail.com", "juan@mail.com", "luisa@mail.com"};
        int[] puntos = {2560, 2345, 1980, 1875, 1650};

        // Gráfico de ranking
        BarChart grafico = new BarChart(puntos, true, usuarios);
        grafico.setBounds(50, y, 800, 200);
        panel.add(grafico);
        y += 220;

        // Tabla de ranking
        String[] columnas = {"Usuario", "Email", "Puntos"};
        String[][] datos = new String[usuarios.length][3];
        for (int i = 0; i < usuarios.length; i++) {
            datos[i][0] = usuarios[i];
            datos[i][1] = emails[i];
            datos[i][2] = String.valueOf(puntos[i]);
        }
        
        TablaDoble tabla = new TablaDoble(columnas, datos);
        tabla.setBounds(50, y, 800, (usuarios.length * 25) + 30);
        panel.add(tabla);
        y += (usuarios.length * 25) + 50;

        // Estadísticas
        double promedio = Arrays.stream(puntos).average().orElse(0);
        JLabel lblPromedio = new JLabel(String.format("Promedio de puntos: %.2f", promedio));
        lblPromedio.setFont(fuenteTexto);
        lblPromedio.setBounds(50, y, 300, 25);
        panel.add(lblPromedio);

        return panel;
    }

    // ------------------------------------------------------------
    // REPORTE 7: Canjes y Beneficios
    // ------------------------------------------------------------
    private static JPanel crearReporteCanjesBeneficios(JPanel panel, int y, Font fuenteSubtitulo, Font fuenteTexto) {
        JLabel tituloReporte = new JLabel("7. Canjes y Beneficios");
        tituloReporte.setFont(fuenteSubtitulo);
        tituloReporte.setBounds(20, y, 500, 30);
        panel.add(tituloReporte);
        y += 40;

        // Datos de ejemplo
        String[] recompensas = {"Descuentos", "Productos", "Donaciones", "Experiencias"};
        int[] canjes = {45, 32, 18, 25};
        int[] puntos = {2250, 3200, 1800, 2500};

        // Gráfico 1: Cantidad de canjes
        JLabel lblGrafico1 = new JLabel("Cantidad de canjes por tipo");
        lblGrafico1.setFont(fuenteTexto);
        lblGrafico1.setBounds(50, y, 300, 20);
        panel.add(lblGrafico1);
        y += 25;

        BarChart grafico1 = new BarChart(canjes, false, recompensas);
        grafico1.setBounds(50, y, 800, 200);
        panel.add(grafico1);
        y += 220;

        // Gráfico 2: Puntos canjeados
        JLabel lblGrafico2 = new JLabel("Puntos canjeados por tipo");
        lblGrafico2.setFont(fuenteTexto);
        lblGrafico2.setBounds(50, y, 300, 20);
        panel.add(lblGrafico2);
        y += 25;

        BarChart grafico2 = new BarChart(puntos, false, recompensas);
        grafico2.setBounds(50, y, 800, 200);
        panel.add(grafico2);
        y += 230;

        // Tabla consolidada
        String[] columnas = {"Recompensa", "Canjes", "Puntos"};
        String[][] datos = new String[recompensas.length][3];
        for (int i = 0; i < recompensas.length; i++) {
            datos[i][0] = recompensas[i];
            datos[i][1] = String.valueOf(canjes[i]);
            datos[i][2] = String.valueOf(puntos[i]);
        }
        
        TablaDoble tabla = new TablaDoble(columnas, datos);
        tabla.setBounds(150, y, 600, (recompensas.length * 25) + 30);
        panel.add(tabla);
        y += (recompensas.length * 25) + 50;

        // Totales
        int totalCanjes = Arrays.stream(canjes).sum();
        int totalPuntos = Arrays.stream(puntos).sum();
        
        JLabel lblTotal1 = new JLabel("Total canjes: " + totalCanjes);
        lblTotal1.setFont(fuenteTexto);
        lblTotal1.setBounds(50, y, 200, 25);
        panel.add(lblTotal1);
        
        JLabel lblTotal2 = new JLabel("Total puntos canjeados: " + totalPuntos);
        lblTotal2.setFont(fuenteTexto);
        lblTotal2.setBounds(300, y, 300, 25);
        panel.add(lblTotal2);

        return panel;
    }

    // ------------------------------------------------------------
    // REPORTE 8: Nuevos Registros de Reciclaje
    // ------------------------------------------------------------
    private static JPanel crearReporteNuevosRegistros(JPanel panel, int y, Font fuenteSubtitulo, Font fuenteTexto) {
        JLabel tituloReporte = new JLabel("8. Nuevos Registros de Reciclaje");
        tituloReporte.setFont(fuenteSubtitulo);
        tituloReporte.setBounds(20, y, 500, 30);
        panel.add(tituloReporte);
        y += 40;

        // Datos de ejemplo (por mes)
        String[] meses = {"Ene", "Feb", "Mar", "Abr", "May", "Jun"};
        int[] nuevosUsuarios = {15, 22, 30, 18, 25, 35};
        double[] material = {75.5, 110.0, 150.5, 90.0, 125.5, 175.0};
        int[] puntos = {755, 1100, 1505, 900, 1255, 1750};

        // Gráfico 1: Nuevos usuarios
        JLabel lblGrafico1 = new JLabel("Nuevos usuarios por mes");
        lblGrafico1.setFont(fuenteTexto);
        lblGrafico1.setBounds(50, y, 300, 20);
        panel.add(lblGrafico1);
        y += 25;

        BarChart grafico1 = new BarChart(nuevosUsuarios, false, meses);
        grafico1.setBounds(50, y, 800, 200);
        panel.add(grafico1);
        y += 220;

        // Gráfico 2: Material reciclado
        JLabel lblGrafico2 = new JLabel("Material reciclado por nuevos usuarios (kg)");
        lblGrafico2.setFont(fuenteTexto);
        lblGrafico2.setBounds(50, y, 350, 20);
        panel.add(lblGrafico2);
        y += 25;

        int[] materialInt = Arrays.stream(material).mapToInt(d -> (int) Math.round(d)).toArray();
        BarChart grafico2 = new BarChart(materialInt, false, meses);
        grafico2.setBounds(50, y, 800, 200);
        panel.add(grafico2);
        y += 230;

        // Tabla consolidada
        String[] columnas = {"Mes", "Nuevos Usuarios", "Material (kg)", "Puntos"};
        String[][] datos = new String[meses.length][4];
        for (int i = 0; i < meses.length; i++) {
            datos[i][0] = meses[i];
            datos[i][1] = String.valueOf(nuevosUsuarios[i]);
            datos[i][2] = String.format("%.1f", material[i]);
            datos[i][3] = String.valueOf(puntos[i]);
        }
        
        TablaDoble tabla = new TablaDoble(columnas, datos);
        tabla.setBounds(50, y, 800, (meses.length * 25) + 30);
        panel.add(tabla);
        y += (meses.length * 25) + 50;

        // Totales
        int totalUsuarios = Arrays.stream(nuevosUsuarios).sum();
        double totalMaterial = Arrays.stream(material).sum();
        int totalPuntos = Arrays.stream(puntos).sum();
        
        JLabel lblTotal1 = new JLabel("Total nuevos usuarios: " + totalUsuarios);
        lblTotal1.setFont(fuenteTexto);
        lblTotal1.setBounds(50, y, 250, 25);
        panel.add(lblTotal1);
        
        JLabel lblTotal2 = new JLabel(String.format("Total material: %.1f kg", totalMaterial));
        lblTotal2.setFont(fuenteTexto);
        lblTotal2.setBounds(300, y, 250, 25);
        panel.add(lblTotal2);
        
        JLabel lblTotal3 = new JLabel("Total puntos: " + totalPuntos);
        lblTotal3.setFont(fuenteTexto);
        lblTotal3.setBounds(550, y, 200, 25);
        panel.add(lblTotal3);

        return panel;
    }

    // ------------------------------------------------------------
    // CLASES INTERNAS PARA COMPONENTES
    // ------------------------------------------------------------

    static class BarChart extends JPanel {
        private final int[] valores;
        private final String[] etiquetas;
        private final boolean autoResize;
        private final Color[] colores = {
            new Color(79, 129, 189), new Color(192, 80, 77), 
            new Color(155, 187, 89), new Color(128, 100, 162),
            new Color(247, 150, 70), new Color(75, 172, 198)
        };

        public BarChart(int[] valores, boolean autoResize, String... etiquetas) {
            this.valores = valores;
            this.autoResize = autoResize;
            this.etiquetas = etiquetas;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();
            int margen = 50;
            int anchoUtil = ancho - 2 * margen;
            int altoUtil = alto - 2 * margen;

            // Ejes
            g2.drawLine(margen, margen, margen, margen + altoUtil);
            g2.drawLine(margen, margen + altoUtil, margen + anchoUtil, margen + altoUtil);

            // Dibujar barras
            int numBarras = valores.length;
            int anchoBarra = anchoUtil / (numBarras + 2);
            int maxValor = Arrays.stream(valores).max().orElse(1);

            for (int i = 0; i < numBarras; i++) {
                int alturaBarra = (int) ((double) valores[i] / maxValor * altoUtil);
                int x = margen + (i + 1) * anchoBarra;
                int y = margen + altoUtil - alturaBarra;

                g2.setColor(colores[i % colores.length]);
                g2.fillRect(x, y, (int) (anchoBarra * 0.8), alturaBarra);

                // Etiqueta valor
                g2.setColor(Color.BLACK);
                String valor = String.valueOf(valores[i]);
                int anchoTexto = g2.getFontMetrics().stringWidth(valor);
                g2.drawString(valor, x + (anchoBarra - anchoTexto) / 2, y - 5);

                // Etiqueta categoría
                String etiqueta = etiquetas[i];
                if (etiqueta.length() > 10) {
                    etiqueta = etiqueta.substring(0, 7) + "...";
                }
                anchoTexto = g2.getFontMetrics().stringWidth(etiqueta);
                g2.drawString(etiqueta, x + (anchoBarra - anchoTexto) / 2, margen + altoUtil + 15);
            }
        }
    }

    static class TablaSimple extends JPanel {
        private final String[] columnas;
        private final String[] datosCol1;
        private final String[] datosCol2;

        public TablaSimple(String[] columnas, String[] datosCol1, String[] datosCol2) {
            this.columnas = columnas;
            this.datosCol1 = datosCol1;
            this.datosCol2 = datosCol2;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();
            int alturaFila = 25;
            int alturaHeader = 30;

            // Dibujar encabezado
            g2.setColor(new Color(70, 130, 180));
            g2.fillRect(0, 0, ancho, alturaHeader);
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            g2.drawString(columnas[0], 10, 20);
            g2.drawString(columnas[1], ancho / 2 + 10, 20);

            // Dibujar filas
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int i = 0; i < datosCol1.length; i++) {
                int y = alturaHeader + i * alturaFila;
                
                // Fondo alternado
                if (i % 2 == 0) {
                    g2.setColor(new Color(240, 240, 240));
                    g2.fillRect(0, y, ancho, alturaFila);
                }
                
                // Texto
                g2.setColor(Color.BLACK);
                g2.drawString(datosCol1[i], 10, y + 18);
                g2.drawString(datosCol2[i], ancho / 2 + 10, y + 18);
                
                // Línea divisoria
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(0, y + alturaFila, ancho, y + alturaFila);
            }

            // Bordes
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, ancho - 1, alto - 1);
            g2.drawLine(ancho / 2, 0, ancho / 2, alto);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(600, 30 + datosCol1.length * 25);
        }
    }

    static class TablaDoble extends JPanel {
        private final String[] columnas;
        private final String[][] datos;

        public TablaDoble(String[] columnas, String[][] datos) {
            this.columnas = columnas;
            this.datos = datos;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();
            int alturaFila = 25;
            int alturaHeader = 30;
            int numColumnas = columnas.length;
            int anchoColumna = ancho / numColumnas;

            // Encabezado
            g2.setColor(new Color(70, 130, 180));
            g2.fillRect(0, 0, ancho, alturaHeader);
            
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 12));
            for (int i = 0; i < numColumnas; i++) {
                g2.drawString(columnas[i], i * anchoColumna + 10, 20);
            }

            // Filas
            g2.setFont(new Font("Arial", Font.PLAIN, 12));
            for (int i = 0; i < datos.length; i++) {
                int y = alturaHeader + i * alturaFila;
                
                // Fondo alternado
                if (i % 2 == 0) {
                    g2.setColor(new Color(240, 240, 240));
                    g2.fillRect(0, y, ancho, alturaFila);
                }
                
                // Texto
                g2.setColor(Color.BLACK);
                for (int j = 0; j < numColumnas; j++) {
                    g2.drawString(datos[i][j], j * anchoColumna + 10, y + 18);
                }
                
                // Línea divisoria
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(0, y + alturaFila, ancho, y + alturaFila);
            }

            // Bordes y líneas verticales
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, ancho - 1, alto - 1);
            for (int i = 1; i < numColumnas; i++) {
                g2.drawLine(i * anchoColumna, 0, i * anchoColumna, alto);
            }
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(800, 30 + datos.length * 25);
        }
    }
}