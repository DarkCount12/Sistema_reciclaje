package Frontend.Page.Secciones;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class Reportes {

    public static JPanel crearPanelReportes() {
        int[] tamañoTabla1 = {7, 3};
        int[] tamañoTabla2 = {5, 5};
        int tamañoTablaSeccion1;

        String[] nombresColumnas = {"Recompensa", "Puntos Necesarios"};
        String[] recompensas = {"Descuento 10% en tienda ecológica", "Botella de agua reutilizable", "Kit de bolsas biodegradables", "Planta pequeña para el hogar", "Taza de bambú personalizada"};
        String[] puntosNecesarios = {"500", "300", "250", "400", "350"};
        String[] totalRecicladoColumnas = {"Tipo de Material", "Cantidad Reciclado (Kg)"};
        String[] puntosGeneradosColumnas = {"Tipo de Material", "Puntos Generados"};
        String[] co2ReducidoColumnas = {"Tipo de Material", "CO2 Reducido"};
        String[] tipoReciclado = {"Plástico", "Vidrio", "Metal", "Papel", "Orgánico"};
        String[] cantidadReciclado = {"80", "55", "70", "90", "60"};
        String[] puntosMaterial = {"45", "65", "85", "40", "75"};
        String[] co2Reducido = {"300", "300", "250", "200", "180"};

        JPanel panelContenedor = new JPanel();
        panelContenedor.setLayout(new java.awt.BorderLayout());
        
        JPanel panelPrincipal = new JPanel(null);
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        Font fuente_h1 = new Font("Arial Rounded MT Bold", Font.BOLD, 24);
        Font fuente_h2 = new Font("Arial Rounded MT Bold", Font.BOLD, 20);
        Font fuente_h3 = new Font("Arial Rounded MT Bold", Font.PLAIN, 18);
        Font fuente_h4 = new Font("Arial Rounded MT Bold", Font.PLAIN, 14);

        JLabel titulo = new JLabel("Reportes Administrativos");
        titulo.setFont(fuente_h1);
        titulo.setBounds(330, 15, 400, 35);
        panelPrincipal.add(titulo);

        JLabel tituloSeccion1 = new JLabel("Reporte de Actividad de Reciclaje");
        tituloSeccion1.setFont(fuente_h2);
        tituloSeccion1.setBounds(20, 60, 500, 30);
        panelPrincipal.add(tituloSeccion1);

        JLabel subtitulo1Seccion1 = new JLabel("Total Material Reciclado por Tipo (Kg)");
        subtitulo1Seccion1.setFont(fuente_h3);
        subtitulo1Seccion1.setBounds(20, 95, 350, 25);
        panelPrincipal.add(subtitulo1Seccion1);

        int[] valores1 = {80, 55, 70, 90, 60};
        BarChart grafico1 = new BarChart(valores1, false, "Plástico", "Vidrio", "Metal", "Papel", "Orgánico");
        grafico1.setBounds(20, 125, 400, 150);
        panelPrincipal.add(grafico1);

        JLabel conclusion1Seccion1 = new JLabel("Total de Material Reciclado: 355 Kg");
        conclusion1Seccion1.setFont(fuente_h4);
        conclusion1Seccion1.setBounds(20, 278, 500, 30);
        panelPrincipal.add(conclusion1Seccion1);

        JLabel subtitulo2Seccion1 = new JLabel("Puntos generados por Tipo de Material");
        subtitulo2Seccion1.setFont(fuente_h3);
        subtitulo2Seccion1.setBounds(500, 95, 350, 25);
        panelPrincipal.add(subtitulo2Seccion1);

        int[] valores2 = {45, 65, 85, 40, 75};
        BarChart grafico2 = new BarChart(valores2, false, "Plástico", "Vidrio", "Metal", "Papel", "Orgánico");
        grafico2.setBounds(500, 125, 400, 150);
        panelPrincipal.add(grafico2);

        JLabel conclusion2Seccion1 = new JLabel("Total de Puntos Acumulados: 490 pts");
        conclusion2Seccion1.setFont(fuente_h4);
        conclusion2Seccion1.setBounds(500, 278, 500, 30);
        panelPrincipal.add(conclusion2Seccion1);

        TablaGeneral tablaReciclaje = new TablaGeneral(tamañoTabla2, totalRecicladoColumnas, tipoReciclado, cantidadReciclado);
        Dimension tamañoReciclaje = tablaReciclaje.getPreferredSize();
        tablaReciclaje.setBounds(20, 310, 400, tamañoReciclaje.height - 5);
        panelPrincipal.add(tablaReciclaje);

        TablaGeneral tablaPuntos = new TablaGeneral(tamañoTabla2, puntosGeneradosColumnas, tipoReciclado, puntosMaterial);
        Dimension tamañoPuntos = tablaPuntos.getPreferredSize();
        tablaPuntos.setBounds(500, 310, 400, tamañoPuntos.height - 5);
        panelPrincipal.add(tablaPuntos);

        tamañoTablaSeccion1 = tamañoReciclaje.height;
        if (tamañoPuntos.height > tamañoReciclaje.height) {
          tamañoTablaSeccion1 = tamañoPuntos.height; 
        }

        JLabel tituloSeccion2 = new JLabel("Reporte de Impacto Ambiental");
        tituloSeccion2.setFont(fuente_h2);
        tituloSeccion2.setBounds(20, (320 + tamañoTablaSeccion1), 500, 30);
        panelPrincipal.add(tituloSeccion2);

        JLabel subtitulo1Seccion2 = new JLabel("CO2 reducido por tipo de material");
        subtitulo1Seccion2.setFont(fuente_h3);
        subtitulo1Seccion2.setBounds(200, (355 + tamañoTablaSeccion1), 350, 25);
        panelPrincipal.add(subtitulo1Seccion2);

        int[] valores3 = {300, 300, 250, 200, 180};
        BarChart grafico3 = new BarChart(valores3, false, "Plástico", "Vidrio", "Metal", "Papel", "Orgánico");
        grafico3.setBounds(200, (385 + tamañoTablaSeccion1), 600, 150);
        panelPrincipal.add(grafico3);

        JLabel conclusion1Seccion2 = new JLabel("Total de CO2 Reducido: 680 Kg");
        conclusion1Seccion2.setFont(fuente_h4);
        conclusion1Seccion2.setBounds(200, (538 + tamañoTablaSeccion1), 500, 30);
        panelPrincipal.add(conclusion1Seccion2);

        TablaGeneral tablaCO2 = new TablaGeneral(tamañoTabla2, co2ReducidoColumnas, tipoReciclado, co2Reducido);
        Dimension tamañoCO2 = tablaCO2.getPreferredSize();
        tablaCO2.setBounds(290, (570 + tamañoTablaSeccion1), 400, tamañoCO2.height - 5);
        panelPrincipal.add(tablaCO2);
        
        JLabel tituloSeccion3 = new JLabel("Reporte de Actividad de Usuarios");
        tituloSeccion3.setFont(fuente_h2);
        tituloSeccion3.setBounds(20, (580 + tamañoTablaSeccion1 + tamañoCO2.height), 500, 30);
        panelPrincipal.add(tituloSeccion3);

        String[] nombres = {"Juan Pérez", "María García", "Carlos Rodríguez"};
        String[] emails = {"juan@gmail.com", "maria@gmail.com", "carlos@gmail.com"};
        String[] materialesKg = {"125.5", "89.2", "156.8"};
        String[] puntos = {"1255", "892", "1568"};

        TablaUsuarios tabla = new TablaUsuarios(nombres, emails, materialesKg, puntos);
        Dimension tamañoPreferido = tabla.getPreferredSize();
        tabla.setBounds(50, (618 + tamañoTablaSeccion1 + tamañoCO2.height), 860, tamañoPreferido.height-5); 
        panelPrincipal.add(tabla);

        JLabel tituloSeccion4 = new JLabel("Reporte de Recompensas Canjeadas");
        tituloSeccion4.setFont(fuente_h2);
        tituloSeccion4.setBounds(20, (630 + tamañoPreferido.height + tamañoTablaSeccion1 + tamañoCO2.height), 500, 30);
        panelPrincipal.add(tituloSeccion4);

        JLabel subtitulo1Seccion4 = new JLabel("Numero de Canjes por Tipo de Recompensa");
        subtitulo1Seccion4.setFont(fuente_h3);
        subtitulo1Seccion4.setBounds(20, (665 + tamañoPreferido.height + tamañoTablaSeccion1 + tamañoCO2.height), 550, 25);
        panelPrincipal.add(subtitulo1Seccion4);

        int[] valores4 = {80, 55, 70, 90, 60};
        BarChart grafico4 = new BarChart(valores4, false, "Descuento 10% en tienda ecológica", "Botella de agua reutilizable", "Kit de bolsas biodegradables", "Planta pequeña para el hogar", "Taza de bambú personalizada");
        grafico4.setBounds(20, (700 + tamañoPreferido.height + tamañoTablaSeccion1 + tamañoCO2.height), 400, 150);
        panelPrincipal.add(grafico4);

        JLabel tituloRecompensas = new JLabel("Recompensas Disponibles");
        tituloRecompensas.setFont(fuente_h3);
        tituloRecompensas.setBounds(500, (663 + tamañoPreferido.height + tamañoTablaSeccion1 + tamañoCO2.height), 400, 30);
        panelPrincipal.add(tituloRecompensas);
        
        TablaGeneral tablaRecompensas = new TablaGeneral(tamañoTabla1, nombresColumnas, recompensas, puntosNecesarios);
        Dimension tamañoRecompensas = tablaRecompensas.getPreferredSize();
        tablaRecompensas.setBounds(500, (698 + tamañoPreferido.height + tamañoTablaSeccion1 + tamañoCO2.height), 400, tamañoRecompensas.height - 5);
        panelPrincipal.add(tablaRecompensas);

        JScrollPane scrollPane = new JScrollPane(panelPrincipal);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
        verticalScrollBar.setPreferredSize(new Dimension(8, Integer.MAX_VALUE));
        
        JScrollBar horizontalScrollBar = scrollPane.getHorizontalScrollBar();
        horizontalScrollBar.setPreferredSize(new Dimension(Integer.MAX_VALUE, 8));
        
        verticalScrollBar.setUI(new ModernScrollBarUI());
        horizontalScrollBar.setUI(new ModernScrollBarUI());
        
        panelContenedor.add(scrollPane, java.awt.BorderLayout.CENTER);
        panelPrincipal.setPreferredSize(new Dimension(900, (700 + tamañoPreferido.height + tamañoRecompensas.height + tamañoTablaSeccion1 + tamañoCO2.height)));
        
        return panelContenedor;
    }

    static class ModernScrollBarUI extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(180, 180, 180);
            this.thumbDarkShadowColor = new Color(150, 150, 150);
            this.thumbHighlightColor = new Color(200, 200, 200);
            this.thumbLightShadowColor = new Color(160, 160, 160);
            this.trackColor = new Color(240, 240, 240);
            this.trackHighlightColor = new Color(245, 245, 245);
        }

        @Override
        protected void paintThumb(Graphics g, javax.swing.JComponent c, java.awt.Rectangle thumbBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(thumbColor);
            g2.fillRoundRect(thumbBounds.x + 1, thumbBounds.y + 1, thumbBounds.width - 2, thumbBounds.height - 2, 4, 4);
            g2.dispose();
        }

        @Override
        protected void paintTrack(Graphics g, javax.swing.JComponent c, java.awt.Rectangle trackBounds) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(trackColor);
            g2.fillRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height);
            g2.dispose();
        }

        @Override
        protected javax.swing.JButton createDecreaseButton(int orientation) {
            return createZeroButton();
        }

        @Override
        protected javax.swing.JButton createIncreaseButton(int orientation) {
            return createZeroButton();
        }

        private javax.swing.JButton createZeroButton() {
            javax.swing.JButton button = new javax.swing.JButton();
            button.setPreferredSize(new Dimension(0, 0));
            button.setMinimumSize(new Dimension(0, 0));
            button.setMaximumSize(new Dimension(0, 0));
            return button;
        }
    }

    static class BarChart extends JPanel {
        private final int[] valores;
        private final String[] etiquetas;
        private final boolean autoResize;
        private final Color[] colores = {
            new Color(100, 120, 200),
            new Color(120, 150, 210),
            new Color(140, 170, 220),
            new Color(160, 190, 230),
            new Color(180, 210, 240),
            new Color(200, 220, 250),
            new Color(90, 110, 190),
            new Color(110, 140, 200),
            new Color(130, 160, 215),
            new Color(150, 180, 225)
        };

        public BarChart(int[] valores, boolean autoResize, String... etiquetas) {
            this.valores = valores;
            this.autoResize = autoResize;
            this.etiquetas = etiquetas;
            setBackground(Color.WHITE);
            
            if (autoResize && valores.length > 5) {
                int anchoMinimo = 60;
                int anchoCalculado = valores.length * anchoMinimo + 70;
                setPreferredSize(new Dimension(anchoCalculado, 150));
            }
        }

        public BarChart(int[] valores, String... etiquetas) {
            this(valores, false, etiquetas);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (valores == null || valores.length == 0) return;

            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int ancho = getWidth();
            int alto = getHeight();
            int paddingIzq = 50;
            int paddingDer = 20;
            int paddingSup = 20;
            int paddingInf = 30;
            int espacioUsableAltura = alto - paddingSup - paddingInf;
            int espacioUsableAncho = ancho - paddingIzq - paddingDer;

            int valorMax = getMaxValue(valores);
            int escalaMax = ((valorMax + 9) / 10) * 10 + 10;
            int numDivisiones = 6;
            int paso = escalaMax / numDivisiones;

            g2.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i <= numDivisiones; i++) {
                int y = alto - paddingInf - (i * espacioUsableAltura / numDivisiones);
                g2.drawLine(paddingIzq, y, ancho - paddingDer, y);
                g2.setColor(Color.BLACK);
                g2.drawString(String.valueOf(i * paso), 10, y + 5);
                g2.setColor(Color.LIGHT_GRAY);
            }

            int numBarras = valores.length;            
            int barraAncho;
            if (autoResize) {
                barraAncho = Math.max(40, espacioUsableAncho / numBarras);
            } else {
                barraAncho = espacioUsableAncho / numBarras;
            }
            
            for (int i = 0; i < numBarras; i++) {
                int valor = valores[i];
                int alturaBarra = (int) ((double) valor / escalaMax * espacioUsableAltura);
                int x = paddingIzq + i * barraAncho;
                int y = alto - paddingInf - alturaBarra;

                g2.setColor(colores[i % colores.length]);
                
                int margen = Math.max(2, Math.min(5, barraAncho / 8));
                g2.fillRect(x + margen, y, barraAncho - (2 * margen), alturaBarra);

                g2.setColor(Color.BLACK);
                
                if (i < etiquetas.length) {
                    String etiqueta = etiquetas[i];
                    int maxChars = Math.max(3, barraAncho / 8);
                    if (etiqueta.length() > maxChars) {
                        etiqueta = etiqueta.substring(0, Math.max(1, maxChars - 2)) + "..";
                    }
                    java.awt.FontMetrics fm = g2.getFontMetrics();
                    int anchoTexto = fm.stringWidth(etiqueta);
                    int xTexto = x + (barraAncho - anchoTexto) / 2;
                    g2.drawString(etiqueta, xTexto, alto - 10);
                }
                
                String valorTexto = String.valueOf(valor);
                java.awt.FontMetrics fm = g2.getFontMetrics();
                int anchoValor = fm.stringWidth(valorTexto);
                int xValor = x + (barraAncho - anchoValor) / 2;
                g2.drawString(valorTexto, xValor, y - 5);
            }

            g2.setColor(Color.BLACK);
            g2.drawLine(paddingIzq, alto - paddingInf, ancho - paddingDer, alto - paddingInf);
            g2.drawLine(paddingIzq, paddingSup, paddingIzq, alto - paddingInf);
        }

        private int getMaxValue(int[] array) {
            int max = array[0];
            for (int v : array) if (v > max) max = v;
            return max;
        }
    }

    static class TablaUsuarios extends JPanel {
        private final String[] nombres;
        private final String[] emails;
        private final String[] materialesKg;
        private final String[] puntos;
        private final int numFilas;

        public TablaUsuarios(String[] nombres, String[] emails, String[] materialesKg, String[] puntos) {
            int minLength = Math.min(Math.min(nombres.length, emails.length), Math.min(materialesKg.length, puntos.length));
            this.nombres = nombres;
            this.emails = emails;
            this.materialesKg = materialesKg;
            this.puntos = puntos;
            this.numFilas = minLength;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
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
            
            int anchoNombre = (int)(ancho * 0.35);
            int anchoEmail = (int)(ancho * 0.35);
            int anchoMaterial = (int)(ancho * 0.15);
            int anchoPuntos = (int)(ancho * 0.15);
            
            int xNombre = 0;
            int xEmail = anchoNombre;
            int xMaterial = anchoNombre + anchoEmail;
            int xPuntos = anchoNombre + anchoEmail + anchoMaterial;

            Font fuenteHeader = new Font("Arial", Font.BOLD, 12);
            Font fuenteData = new Font("Arial", Font.PLAIN, 11);

            g2.setFont(fuenteHeader);
            g2.setColor(new Color(70, 130, 180));
            g2.fillRect(0, 0, ancho, alturaHeader);
            
            g2.setColor(Color.WHITE);
            g2.drawString("Nombre Completo", xNombre + 5, 20);
            g2.drawString("Gmail", xEmail + 5, 20);
            g2.drawString("Material Kg", xMaterial + 5, 20);
            g2.drawString("Puntos", xPuntos + 5, 20);

            g2.setColor(Color.WHITE);
            g2.drawLine(xEmail, 0, xEmail, alturaHeader);
            g2.drawLine(xMaterial, 0, xMaterial, alturaHeader);
            g2.drawLine(xPuntos, 0, xPuntos, alturaHeader);

            g2.setFont(fuenteData);
            for (int i = 0; i < numFilas; i++) {
                int yFila = alturaHeader + (i * alturaFila);
                
                if (i % 2 == 0) {
                    g2.setColor(new Color(248, 248, 248));
                    g2.fillRect(0, yFila, ancho, alturaFila);
                }
                
                g2.setColor(Color.BLACK);
                
                String nombre = truncarTexto(nombres[i], anchoNombre - 10, g2.getFontMetrics());
                g2.drawString(nombre, xNombre + 5, yFila + 17);
                
                String email = truncarTexto(emails[i], anchoEmail - 10, g2.getFontMetrics());
                g2.drawString(email, xEmail + 5, yFila + 17);
                
                g2.drawString(materialesKg[i], xMaterial + 5, yFila + 17);
                g2.drawString(puntos[i], xPuntos + 5, yFila + 17);
                
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(0, yFila + alturaFila, ancho, yFila + alturaFila);
            }

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(xEmail, alturaHeader, xEmail, alturaHeader + (numFilas * alturaFila));
            g2.drawLine(xMaterial, alturaHeader, xMaterial, alturaHeader + (numFilas * alturaFila));
            g2.drawLine(xPuntos, alturaHeader, xPuntos, alturaHeader + (numFilas * alturaFila));
            
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, ancho - 1, alturaHeader + (numFilas * alturaFila) - 1);
        }

        private String truncarTexto(String texto, int anchoMaximo, java.awt.FontMetrics fm) {
            if (fm.stringWidth(texto) <= anchoMaximo) {
                return texto;
            }
            
            String textoPuntos = "...";
            int anchoPuntos = fm.stringWidth(textoPuntos);
            
            for (int i = texto.length() - 1; i > 0; i--) {
                String textoCorto = texto.substring(0, i);
                if (fm.stringWidth(textoCorto) + anchoPuntos <= anchoMaximo) {
                    return textoCorto + textoPuntos;
                }
            }
            
            return texto.substring(0, 1) + textoPuntos;
        }

        @Override
        public Dimension getPreferredSize() {
            int alturaCalculada = 30 + (numFilas * 25) + 5;
            return new Dimension(860, alturaCalculada);
        }
    }

    static class TablaGeneral extends JPanel {
        private final int[] listaTamaño;
        private final String[] nombresColumnas;
        private final String[] recompensas;
        private final String[] puntosNecesarios;
        private final int numFilas;

        public TablaGeneral(int[] listaTamaño, String[] nombresColumnas, String[] recompensas, String[] puntosNecesarios) {
            int minLength = Math.min(recompensas.length, puntosNecesarios.length);
            this.listaTamaño = listaTamaño;
            this.nombresColumnas = nombresColumnas;
            this.recompensas = recompensas;
            this.puntosNecesarios = puntosNecesarios;
            this.numFilas = minLength;
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
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
            
            int anchoRecompensa = (int)(ancho * listaTamaño[0] * 0.1);     
            int anchoPuntos = (int)(ancho * listaTamaño[1] * 0.1);          
            
            int xRecompensa = 0;
            int xPuntos = anchoRecompensa;

            Font fuenteHeader = new Font("Arial", Font.BOLD, 12);
            Font fuenteData = new Font("Arial", Font.PLAIN, 11);

            g2.setFont(fuenteHeader);
            g2.setColor(new Color(70, 130, 180));
            g2.fillRect(0, 0, ancho, alturaHeader);
            
            g2.setColor(Color.WHITE);
            g2.drawString(nombresColumnas[0], xRecompensa + 5, 20);
            g2.drawString(nombresColumnas[1], xPuntos + 5, 20);

            g2.setColor(Color.WHITE);
            g2.drawLine(xPuntos, 0, xPuntos, alturaHeader);

            g2.setFont(fuenteData);
            for (int i = 0; i < numFilas; i++) {
                int yFila = alturaHeader + (i * alturaFila);
                
                if (i % 2 == 0) {
                    g2.setColor(new Color(248, 255, 248)); 
                    g2.fillRect(0, yFila, ancho, alturaFila);
                }
                
                g2.setColor(Color.BLACK);
                
                String recompensa = truncarTexto(recompensas[i], anchoRecompensa - 10, g2.getFontMetrics());
                g2.drawString(recompensa, xRecompensa + 5, yFila + 17);
                g2.drawString(puntosNecesarios[i], xPuntos + 5, yFila + 17);
                
                g2.setColor(Color.LIGHT_GRAY);
                g2.drawLine(0, yFila + alturaFila, ancho, yFila + alturaFila);
            }

            g2.setColor(Color.LIGHT_GRAY);
            g2.drawLine(xPuntos, alturaHeader, xPuntos, alturaHeader + (numFilas * alturaFila));
            
            g2.setColor(Color.GRAY);
            g2.drawRect(0, 0, ancho - 1, alturaHeader + (numFilas * alturaFila) - 1);
        }

        private String truncarTexto(String texto, int anchoMaximo, java.awt.FontMetrics fm) {
            if (fm.stringWidth(texto) <= anchoMaximo) {
                return texto;
            }
            
            String textoPuntos = "...";
            int anchoPuntos = fm.stringWidth(textoPuntos);
            
            for (int i = texto.length() - 1; i > 0; i--) {
                String textoCorto = texto.substring(0, i);
                if (fm.stringWidth(textoCorto) + anchoPuntos <= anchoMaximo) {
                    return textoCorto + textoPuntos;
                }
            }
            
            return texto.substring(0, 1) + textoPuntos;
        }

        @Override
        public Dimension getPreferredSize() {
            int alturaCalculada = 30 + (numFilas * 25) + 5;
            return new Dimension(860, alturaCalculada);
        }
    }
}
