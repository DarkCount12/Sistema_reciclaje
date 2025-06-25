package Frontend.Page;




import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;

public class Donaciones extends JFrame {
    public Donaciones() {
        setTitle("ReciclaPlus");
        setSize(400, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Encabezado
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(38, 50, 56));
        header.setBorder(new EmptyBorder(10, 15, 10, 15));
        JLabel title = new JLabel("ReciclaPlus");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        JLabel user = new JLabel("Calletana");
        user.setForeground(Color.WHITE);
        header.add(title, BorderLayout.WEST);
        header.add(user, BorderLayout.EAST);
        add(header, BorderLayout.NORTH);

        // Panel principal
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(10, 15, 10, 15));

        // Título principal
        JLabel mainTitle = new JLabel("Mis Donaciones");
        mainTitle.setFont(new Font("SansSerif", Font.BOLD, 18));
        mainPanel.add(mainTitle);

        // Buscador y puntos
        JPanel searchPanel = new JPanel(new BorderLayout(10, 0));
        JTextField searchField = new JTextField("Buscar causa");
        searchField.setBorder(new CompoundBorder(
                new RoundBorder(8, Color.GRAY),
                new EmptyBorder(5, 10, 5, 10)));
        searchField.setPreferredSize(new Dimension(200, 30));

        JPanel pointsBox = new JPanel();
        pointsBox.setBackground(new Color(38, 50, 56));
        pointsBox.setBorder(new RoundBorder(8, Color.GRAY));
        JLabel points = new JLabel("<html><center>Puntos disponibles:<br><b>1500</b></center></html>");
        points.setForeground(Color.WHITE);
        points.setBorder(new EmptyBorder(5, 10, 5, 10));
        pointsBox.add(points);

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(pointsBox, BorderLayout.EAST);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(searchPanel);

        // Filtros
        JPanel filters = new JPanel(new GridLayout(1, 2, 10, 0));
        JComboBox<String> category = new JComboBox<>(new String[]{"Categoría"});
        styleComboBox(category);
        JComboBox<String> range = new JComboBox<>(new String[]{"Rango de puntos"});
        styleComboBox(range);
        filters.add(category);
        filters.add(range);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(filters);

        // Donaciones
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createDonationCard("Reforestación", "Apoya la plantación de árboles", 400));
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(createDonationCard("Acción humanitaria", "Contribuye a ayuda de emergencia", 700));

        // Historial
        JLabel historyTitle = new JLabel("Historial de Donaciones");
        historyTitle.setFont(new Font("SansSerif", Font.BOLD, 16));
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(historyTitle);
        JTextArea history = new JTextArea("• 12/05/2025  Reforestación | 400 pts\n• 05/05/2025  Acción humanitaria | 700 pts");
        history.setEditable(false);
        history.setBackground(mainPanel.getBackground());
        history.setFont(new Font("Monospaced", Font.PLAIN, 12));
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(history);

        add(mainPanel, BorderLayout.CENTER);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setBorder(new RoundBorder(8, Color.GRAY));
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
    }

    private JPanel createDonationCard(String title, String desc, int points) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setBorder(new CompoundBorder(
                new RoundBorder(12, Color.LIGHT_GRAY),
                new EmptyBorder(10, 10, 10, 10)));
        card.setBackground(Color.WHITE);

        JLabel icon = new JLabel("🌱");
        icon.setFont(new Font("SansSerif", Font.PLAIN, 30));
        JPanel left = new JPanel();
        left.setBackground(Color.WHITE);
        left.add(icon);
        card.add(left, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        JLabel descLabel = new JLabel(desc);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        JLabel ptsLabel = new JLabel("Puntos necesarios: " + points);
        ptsLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        textPanel.add(titleLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(descLabel);
        textPanel.add(Box.createVerticalStrut(2));
        textPanel.add(ptsLabel);
        card.add(textPanel, BorderLayout.CENTER);

        // Panel para el botón en la esquina inferior derecha
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        JButton donateBtn = new JButton("DONAR");
        donateBtn.setBackground(new Color(38, 50, 56));
        donateBtn.setForeground(Color.WHITE);
        donateBtn.setFocusPainted(false);
        donateBtn.setBorder(new RoundBorder(8, new Color(38, 50, 56)));
        donateBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        // Botón más delgado (10px de altura)
        donateBtn.setPreferredSize(new Dimension(80, 25));
        donateBtn.setMaximumSize(new Dimension(80, 25));
        donateBtn.setMinimumSize(new Dimension(80, 25));

        buttonPanel.add(donateBtn);

        // Panel sur para posicionar el botón en la parte inferior
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(Color.WHITE);
        southPanel.add(buttonPanel, BorderLayout.EAST);

        card.add(southPanel, BorderLayout.SOUTH);

        return card;
    }

    // Clase para bordes redondeados
    static class RoundBorder extends AbstractBorder {
        private int radius;
        private Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.drawRoundRect(x, y, width-1, height-1, radius, radius);
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(this.radius, this.radius, this.radius, this.radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = this.radius;
            return insets;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new  Donaciones().setVisible(true);
        });
    }
}