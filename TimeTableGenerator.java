import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;

public class TimeTableGenerator extends JFrame {

    private JTable table;
    private DefaultTableModel model;
    private JComboBox<String> cbDays, cbPeriod, cbSubject, cbTeacher;

    public TimeTableGenerator() {
        setTitle("INDER { 📅 Time Table Generator }");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // ===== LEFT PANEL (Image + App Name) =====
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setBackground(new Color(230, 240, 255));
        leftPanel.setPreferredSize(new Dimension(220, 0));

        try {
            ImageIcon icon = new ImageIcon("inderinsta.png"); // Replace with your image file
            Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
            JLabel imgLabel = new JLabel(new ImageIcon(img));
            imgLabel.setHorizontalAlignment(JLabel.CENTER);
            imgLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

            JLabel appName = new JLabel("<html><center><b>Time Table<br>Generator</b></center></html>");
            appName.setFont(new Font("Segoe UI", Font.BOLD, 18));
            appName.setForeground(new Color(0, 51, 153));
            appName.setHorizontalAlignment(JLabel.CENTER);

            leftPanel.add(imgLabel, BorderLayout.CENTER);
            leftPanel.add(appName, BorderLayout.SOUTH);
        } catch (Exception e) {
            leftPanel.add(new JLabel("No Image", JLabel.CENTER));
        }

        add(leftPanel, BorderLayout.WEST);

        // ===== HEADING =====
        JLabel heading = new JLabel("Weekly Time Table Generator", JLabel.CENTER);
        heading.setFont(new Font("Segoe UI", Font.BOLD, 24));
        heading.setForeground(new Color(0, 102, 204));
        heading.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(heading, BorderLayout.NORTH);

        // ===== TABLE =====

        String[] columnNames = { "Day", "Period 1", "Period 2", "Period 3", "Period 4", "Period 5", "Period 6",
                "Period 7" };
        String[][] data = {
                { "Monday", "", "", "", "", "", "", "" },
                { "Tuesday", "", "", "", "", "", "", "" },
                { "Wednesday", "", "", "", "", "", "", "" },
                { "Thursday", "", "", "", "", "", "", "" },
                { "Friday", "", "", "", "", "", "", "" }
        };

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(0, 102, 204));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 2));
        add(scrollPane, BorderLayout.CENTER);

        // ===== BUTTON PANEL =====
        JButton btnAssign = new JButton("Assign");
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnDownloadImage = new JButton("Download Image");

        // Button styling
        btnAssign.setBackground(new Color(0, 153, 76));
        btnAssign.setForeground(Color.WHITE);
        btnAssign.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnAssign.setFocusPainted(false);

        btnDelete.setBackground(new Color(204, 0, 0));
        btnDelete.setForeground(Color.WHITE);
        btnDelete.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDelete.setFocusPainted(false);

        btnDownloadImage.setBackground(new Color(0, 102, 204));
        btnDownloadImage.setForeground(Color.WHITE);
        btnDownloadImage.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnDownloadImage.setFocusPainted(false);

        JPanel btnPanel = new JPanel();
        btnPanel.setBackground(new Color(240, 248, 255));
        btnPanel.add(btnAssign);
        btnPanel.add(btnDelete);
        btnPanel.add(btnDownloadImage);
        add(btnPanel, BorderLayout.NORTH);

        // ===== COMBO PANEL =====
        JPanel comboPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        comboPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        comboPanel.setBackground(new Color(240, 248, 255));

        String[] days = { "Monday", "Tuesday", "Wednesday", "Thursday", "Friday" };
        String[] periods = { "1", "2", "3", "4", "5", "6", "7" };
        String[] subjects = { "Math", "Science", "History", "English", "Computer" };
        String[] teachers = { "Mr. INDER", "Ms. SINGH", "Mr. DAYAL", "Ms. JIT" };

        cbDays = new JComboBox<>(days);
        cbPeriod = new JComboBox<>(periods);
        cbSubject = new JComboBox<>(subjects);
        cbTeacher = new JComboBox<>(teachers);

        comboPanel.add(new JLabel("Select Day:"));
        comboPanel.add(cbDays);
        comboPanel.add(new JLabel("Select Period:"));
        comboPanel.add(cbPeriod);
        comboPanel.add(new JLabel("Select Subject:"));
        comboPanel.add(cbSubject);
        comboPanel.add(new JLabel("Select Teacher:"));
        comboPanel.add(cbTeacher);

        add(comboPanel, BorderLayout.SOUTH);

        // ===== BUTTON ACTIONS =====
        btnAssign.addActionListener(_ -> assignSubject());
        btnDelete.addActionListener(_ -> deleteSelected());
        btnDownloadImage.addActionListener(_ -> saveTableAsImage());
    }

    // Assign selected subject and teacher to table
    private void assignSubject() {
        int dayIndex = cbDays.getSelectedIndex();
        int periodIndex = cbPeriod.getSelectedIndex() + 1;

        String subject = cbSubject.getSelectedItem().toString();
        String teacher = cbTeacher.getSelectedItem().toString();

        model.setValueAt(subject + " (" + teacher + ")", dayIndex, periodIndex);
    }

    // Delete selected cell
    private void deleteSelected() {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();

        if (row == -1 || col == -1) {
            JOptionPane.showMessageDialog(this, "Please select a cell to delete.", "Warning",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (col == 0) {
            JOptionPane.showMessageDialog(this, "Cannot delete the day name.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        model.setValueAt("", row, col);
    }

    // Save table as image
    private void saveTableAsImage() {
        try {
            int width = table.getWidth();
            int height = table.getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = image.createGraphics();
            table.paint(g2);
            g2.dispose();

            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setSelectedFile(new java.io.File("TimeTable.png"));
            int option = fileChooser.showSaveDialog(this);

            if (option == JFileChooser.APPROVE_OPTION) {
                javax.imageio.ImageIO.write(image, "png", fileChooser.getSelectedFile());
                JOptionPane.showMessageDialog(this, "Image saved successfully!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error saving image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new TimeTableGenerator().setVisible(true));
    }
}
