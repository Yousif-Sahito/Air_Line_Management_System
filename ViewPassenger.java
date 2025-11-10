package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

public class ViewPassenger extends JFrame {
    JTable t;
    String[] x = {"Username", "Name", "Age", "Date of Birth", "Address", "Phone", "Email", "Nationality", "Gender", "Passport"};
    String[][] y = new String[100][10];
    Font f, headerFont;
    
    ViewPassenger(){
        super("All Passenger Details");
        setSize(1400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        // Modern fonts
        f = new Font("Segoe UI", Font.PLAIN, 14);
        headerFont = new Font("Segoe UI", Font.BOLD, 15);
         
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM passenger ORDER BY username";
            ResultSet rest = obj.stm.executeQuery(q);
            
            int i = 0;
            while(rest.next() && i < y.length){
                y[i][0] = rest.getString("username");
                y[i][1] = rest.getString("name");
                y[i][2] = rest.getString("age");
                y[i][3] = rest.getString("dob");
                y[i][4] = rest.getString("address");
                y[i][5] = rest.getString("phone");
                y[i][6] = rest.getString("email");
                y[i][7] = rest.getString("nationality");
                y[i][8] = rest.getString("gender");
                y[i][9] = rest.getString("passport");
                i++;
            }
            
            // Create table with data
            t = new JTable(y, x) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
        } catch(Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading passenger data: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        
        // ===== ENHANCED TABLE STYLING =====
        t.setFont(f);
        t.setRowHeight(25); // Reduced row height
        t.setSelectionBackground(new Color(0, 102, 204));
        t.setSelectionForeground(Color.WHITE);
        t.setGridColor(new Color(200, 200, 200));
        
        // Center align all columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int col = 0; col < t.getColumnCount(); col++) {
            t.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
        }
        
        // Left align Name and Address columns for better readability
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        t.getColumnModel().getColumn(1).setCellRenderer(leftRenderer); // Name
        t.getColumnModel().getColumn(4).setCellRenderer(leftRenderer); // Address
        t.getColumnModel().getColumn(6).setCellRenderer(leftRenderer); // Email
        
        // Style table header
        JTableHeader header = t.getTableHeader();
        header.setFont(headerFont);
        header.setBackground(new Color(40, 60, 100));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(header.getWidth(), 30)); // Reduced header height
        
        // Add scroll pane with NO side spacing
        JScrollPane js = new JScrollPane(t);
        js.getViewport().setBackground(Color.WHITE);
        js.setBorder(BorderFactory.createEmptyBorder()); // Remove scroll pane border
        
        // ===== MAIN CONTAINER WITH NO MARGINS =====
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(240, 240, 240));
        
        // REMOVED ALL MARGINS - set border to empty
        mainContainer.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        
        // Create main panel with gradient background
        GradientPanel mainPanel = new GradientPanel();
        mainPanel.setLayout(new BorderLayout());
        
        // Title label with minimal padding
        JLabel titleLabel = new JLabel("PASSENGER DATABASE", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20)); // Slightly smaller font
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0)); // Reduced padding
        
        // Content panel for table with minimal spacing
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(2, 2, 2, 2) // Minimal internal padding
        ));
        contentPanel.add(js, BorderLayout.CENTER);
        
        // Stats panel
        JPanel statsPanel = createStatsPanel();
        
        // Add components to main panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);
        mainPanel.add(statsPanel, BorderLayout.SOUTH);
        
        // Add main panel to container with NO margins
        mainContainer.add(mainPanel, BorderLayout.CENTER);
        add(mainContainer);
        
        setVisible(true);
    }
    
    private JPanel createStatsPanel() {
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5)); // Reduced spacing
        statsPanel.setBackground(new Color(40, 60, 100, 200));
        statsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Minimal spacing for stats
        
        try {
            ConnectionClass obj = new ConnectionClass();
            
            // Total passengers
            ResultSet rs1 = obj.stm.executeQuery("SELECT COUNT(*) as total FROM passenger");
            rs1.next();
            String totalPassengers = rs1.getString("total");
            
            // Gender distribution
            ResultSet rs2 = obj.stm.executeQuery("SELECT gender, COUNT(*) as count FROM passenger GROUP BY gender");
            StringBuilder genderStats = new StringBuilder();
            while (rs2.next()) {
                if (genderStats.length() > 0) genderStats.append(" | ");
                genderStats.append(rs2.getString("gender")).append(": ").append(rs2.getString("count"));
            }
            
            // Nationality distribution
            ResultSet rs3 = obj.stm.executeQuery("SELECT nationality, COUNT(*) as count FROM passenger GROUP BY nationality");
            StringBuilder nationalityStats = new StringBuilder();
            while (rs3.next()) {
                if (nationalityStats.length() > 0) nationalityStats.append(" | ");
                nationalityStats.append(rs3.getString("nationality")).append(": ").append(rs3.getString("count"));
            }
            
            // Create stat labels
            JLabel totalLabel = createStatLabel("Total Passengers: " + totalPassengers);
            JLabel genderLabel = createStatLabel("Gender: " + genderStats.toString());
            JLabel nationalityLabel = createStatLabel("Nationality: " + nationalityStats.toString());
            
            statsPanel.add(totalLabel);
            statsPanel.add(genderLabel);
            statsPanel.add(nationalityLabel);
            
            rs1.close();
            rs2.close();
            rs3.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return statsPanel;
    }
    
    private JLabel createStatLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 11)); // Smaller font
        label.setForeground(Color.WHITE);
        label.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.WHITE, 1),
            BorderFactory.createEmptyBorder(2, 4, 2, 4) // Reduced padding
        ));
        return label;
    }
    
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> new ViewPassenger());
    }
}

// Gradient Panel Class
class GradientPanel extends JPanel {
    private Color color1 = new Color(40, 60, 100);
    private Color color2 = new Color(10, 20, 40);

    public GradientPanel() {
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}