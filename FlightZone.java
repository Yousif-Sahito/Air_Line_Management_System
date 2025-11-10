package AMS;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.JComboBox;

public class FlightZone extends JFrame implements ActionListener {

    private JTable flightTable;
    private JComboBox<String> flightChoice, fromChoice, toChoice;
    private JButton showDetailsBtn, searchFlightsBtn, refreshBtn, clearBtn;
    private JLabel statusLabel;

    // Define our new modern row color
    private final Color ALT_ROW_COLOR = new Color(240, 245, 250);

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            System.err.println("Failed to set Nimbus Look and Feel. Using default.");
        }

        SwingUtilities.invokeLater(() -> {
            new FlightZone().setVisible(true);
        });
    }

    FlightZone() {
        initializeUI();
    }

    private void initializeUI() {
        setLayout(new BorderLayout(5, 5));
        getContentPane().setBackground(new Color(30, 60, 90));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(1000, 700);
        setTitle("Pakistan Airlines - Flight Information System");
        setLocationRelativeTo(null);

        // --- Header ---
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // --- Main ---
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // --- Status bar ---
        JPanel statusPanel = createStatusPanel();
        add(statusPanel, BorderLayout.SOUTH);

        // Initialize components
        initializeComponents();
        
        // Load data
        populateFlightCodes();
        populateLocationChoices();
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 82, 155));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 15));
        JLabel titleLabel = new JLabel(" Pakistan Airlines Flight Information System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        return headerPanel;
    }

    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new BorderLayout(5, 10));
        mainPanel.setOpaque(false);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // --- Controls ---
        JPanel controlPanel = createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        // --- Table ---
        JScrollPane scrollPane = createTableScrollPane();
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        return mainPanel;
    }

    private JPanel createControlPanel() {
        JPanel controlPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        controlPanel.setBackground(new Color(240, 245, 250));
        controlPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 220, 240), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // Row 1: Flight Code Search
        JPanel flightCodePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        flightCodePanel.setBackground(new Color(240, 245, 250));
        
        JLabel flightCodeLabel = new JLabel("✈️ Search by Flight Code:");
        flightCodeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        flightCodeLabel.setForeground(new Color(0, 82, 155));
        flightCodePanel.add(flightCodeLabel);

        flightChoice = new JComboBox<>();
        flightChoice.setFont(new Font("Arial", Font.PLAIN, 14));
        flightChoice.setBackground(Color.WHITE);
        flightChoice.setPreferredSize(new Dimension(200, 30));
        flightCodePanel.add(flightChoice);

        showDetailsBtn = createStyledButton("🔍 Show Flight Details", new Color(0, 123, 255));
        showDetailsBtn.addActionListener(this);
        flightCodePanel.add(showDetailsBtn);

        // Row 2: Location Search
        JPanel locationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        locationPanel.setBackground(new Color(240, 245, 250));
        
        JLabel fromLabel = new JLabel("📍 From:");
        fromLabel.setFont(new Font("Arial", Font.BOLD, 14));
        fromLabel.setForeground(new Color(0, 82, 155));
        locationPanel.add(fromLabel);

        fromChoice = new JComboBox<>();
        fromChoice.setFont(new Font("Arial", Font.PLAIN, 14));
        fromChoice.setBackground(Color.WHITE);
        fromChoice.setPreferredSize(new Dimension(150, 30));
        locationPanel.add(fromChoice);

        JLabel toLabel = new JLabel("🎯 To:");
        toLabel.setFont(new Font("Arial", Font.BOLD, 14));
        toLabel.setForeground(new Color(0, 82, 155));
        locationPanel.add(toLabel);

        toChoice = new JComboBox<>();
        toChoice.setFont(new Font("Arial", Font.PLAIN, 14));
        toChoice.setBackground(Color.WHITE);
        toChoice.setPreferredSize(new Dimension(150, 30));
        locationPanel.add(toChoice);

        searchFlightsBtn = createStyledButton("🔎 Search Available Flights", new Color(40, 167, 69));
        searchFlightsBtn.addActionListener(this);
        locationPanel.add(searchFlightsBtn);

        // Add action buttons
        refreshBtn = createStyledButton("🔄 Refresh All", new Color(255, 193, 7));
        refreshBtn.addActionListener(this);
        locationPanel.add(refreshBtn);

        clearBtn = createStyledButton("🗑️ Clear Results", new Color(220, 53, 69));
        clearBtn.addActionListener(this);
        locationPanel.add(clearBtn);

        // Add both panels to main control panel
        controlPanel.add(flightCodePanel);
        controlPanel.add(locationPanel);

        return controlPanel;
    }

    private JScrollPane createTableScrollPane() {
        // Initialize table first
        flightTable = new JTable();
        flightTable.setFont(new Font("Arial", Font.BOLD, 13));
        flightTable.setRowHeight(28);
        flightTable.setGridColor(new Color(220, 220, 220));
        flightTable.setFillsViewportHeight(true);
        
        // Table header
        flightTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        flightTable.getTableHeader().setBackground(new Color(0, 82, 155));
        flightTable.getTableHeader().setForeground(Color.WHITE);

        // Set table model
        DefaultTableModel model = new DefaultTableModel(
                new Object[]{"Flight Code", "Flight Name", "Departure", "Arrival", "Capacity", "Class Name", "Price"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table non-editable
            }
        };
        flightTable.setModel(model);

        // Apply renderers
        ModernTableCellRenderer defaultRenderer = new ModernTableCellRenderer(SwingConstants.LEFT);
        ModernTableCellRenderer centerRenderer = new ModernTableCellRenderer(SwingConstants.CENTER);
        ModernTableCellRenderer rightRenderer = new ModernTableCellRenderer(SwingConstants.RIGHT);

        flightTable.setDefaultRenderer(Object.class, defaultRenderer);
        flightTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer); // Capacity
        flightTable.getColumnModel().getColumn(6).setCellRenderer(rightRenderer); // Price

        JScrollPane scrollPane = new JScrollPane(flightTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 1));
        return scrollPane;
    }

    private JPanel createStatusPanel() {
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        statusPanel.setBackground(new Color(240, 245, 250));
        statusPanel.setBorder(BorderFactory.createLineBorder(new Color(200, 220, 240), 1));
        statusLabel = new JLabel("💡 Select a flight code OR choose From/To locations to search for available flights");
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusPanel.add(statusLabel);
        return statusPanel;
    }

    private void initializeComponents() {
        // Ensure all components are properly initialized
        if (flightChoice == null) flightChoice = new JComboBox<>();
        if (fromChoice == null) fromChoice = new JComboBox<>();
        if (toChoice == null) toChoice = new JComboBox<>();
        if (showDetailsBtn == null) showDetailsBtn = createStyledButton("🔍 Show Details", new Color(0, 123, 255));
        if (searchFlightsBtn == null) searchFlightsBtn = createStyledButton("🔎 Search Flights", new Color(40, 167, 69));
        if (refreshBtn == null) refreshBtn = createStyledButton("🔄 Refresh", new Color(255, 193, 7));
        if (clearBtn == null) clearBtn = createStyledButton("🗑️ Clear", new Color(220, 53, 69));
        if (statusLabel == null) statusLabel = new JLabel("💡 Select a flight code OR choose From/To locations to search for available flights");
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private void populateFlightCodes() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT DISTINCT f_code FROM flight ORDER BY f_code";
            ResultSet rs = obj.stm.executeQuery(query);

            // Clear existing items safely
            if (flightChoice != null) {
                flightChoice.removeAllItems();
                flightChoice.addItem("-- Select Flight Code --");
                
                while (rs.next()) {
                    flightChoice.addItem(rs.getString("f_code"));
                }
            }

            rs.close();
        } catch (Exception ex) {
            showError("Error loading flight codes: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void populateLocationChoices() {
        try {
            ConnectionClass obj = new ConnectionClass();
            
            // Get all unique departure locations
            String departureQuery = "SELECT DISTINCT source FROM flight ORDER BY source";
            ResultSet departureRs = obj.stm.executeQuery(departureQuery);

            if (fromChoice != null) {
                fromChoice.removeAllItems();
                fromChoice.addItem("-- Select Departure --");
                
                while (departureRs.next()) {
                    fromChoice.addItem(departureRs.getString("source"));
                }
            }
            departureRs.close();

            // Get all unique arrival locations
            String arrivalQuery = "SELECT DISTINCT destination FROM flight ORDER BY destination";
            ResultSet arrivalRs = obj.stm.executeQuery(arrivalQuery);

            if (toChoice != null) {
                toChoice.removeAllItems();
                toChoice.addItem("-- Select Arrival --");
                
                while (arrivalRs.next()) {
                    toChoice.addItem(arrivalRs.getString("destination"));
                }
            }
            arrivalRs.close();

        } catch (Exception ex) {
            showError("Error loading locations: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == showDetailsBtn) {
            showFlightDetails();
        } else if (e.getSource() == searchFlightsBtn) {
            searchFlightsByLocation();
        } else if (e.getSource() == refreshBtn) {
            refreshData();
        } else if (e.getSource() == clearBtn) {
            clearTable();
        }
    }

    private void showFlightDetails() {
        if (flightChoice == null || flightChoice.getSelectedItem() == null) {
            showError("Flight selection not properly initialized");
            return;
        }

        String selectedFlight = (String) flightChoice.getSelectedItem();

        if (selectedFlight == null || selectedFlight.equals("-- Select Flight Code --")) {
            showError("Please select a valid flight code");
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT f_code, f_name, source, destination, capacity, class_name, price FROM flight WHERE f_code = ?";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setString(1, selectedFlight);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
            boolean hasData = false;

            while (rs.next()) {
                hasData = true;

                // Prevent duplicate flight code rows
                boolean exists = false;
                for (int i = 0; i < model.getRowCount(); i++) {
                    if (model.getValueAt(i, 0).equals(rs.getString("f_code"))) {
                        exists = true;
                        break;
                    }
                }
                if (exists) continue;

                model.addRow(new Object[]{
                    rs.getString("f_code"),
                    rs.getString("f_name"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getInt("capacity"),
                    rs.getString("class_name"),
                    "Rs. " + rs.getInt("price")
                });
            }

            if (hasData)
                statusLabel.setText("✅ Added details for flight " + selectedFlight);
            else
                showInfo("No details found for flight code: " + selectedFlight);

            rs.close();
            pst.close();

        } catch (Exception ex) {
            showError("Error fetching flight details: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void searchFlightsByLocation() {
        if (fromChoice == null || toChoice == null || 
            fromChoice.getSelectedItem() == null || toChoice.getSelectedItem() == null) {
            showError("Location selections not properly initialized");
            return;
        }

        String fromLocation = (String) fromChoice.getSelectedItem();
        String toLocation = (String) toChoice.getSelectedItem();

        if (fromLocation.equals("-- Select Departure --") || toLocation.equals("-- Select Arrival --")) {
            showError("Please select both departure and arrival locations");
            return;
        }

        if (fromLocation.equals(toLocation)) {
            showError("Departure and arrival locations cannot be the same");
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT f_code, f_name, source, destination, capacity, class_name, price " +
                         "FROM flight WHERE source = ? AND destination = ? ORDER BY f_code";
            PreparedStatement pst = obj.con.prepareStatement(query);
            pst.setString(1, fromLocation);
            pst.setString(2, toLocation);
            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
            model.setRowCount(0); // Clear existing rows

            int flightCount = 0;
            while (rs.next()) {
                flightCount++;
                model.addRow(new Object[]{
                    rs.getString("f_code"),
                    rs.getString("f_name"),
                    rs.getString("source"),
                    rs.getString("destination"),
                    rs.getInt("capacity"),
                    rs.getString("class_name"),
                    "Rs. " + rs.getInt("price")
                });
            }

            if (flightCount > 0) {
                statusLabel.setText("✅ Found " + flightCount + " flights from " + fromLocation + " to " + toLocation);
            } else {
                showInfo("No flights found from " + fromLocation + " to " + toLocation);
                statusLabel.setText("❌ No flights available for the selected route");
            }

            rs.close();
            pst.close();

        } catch (Exception ex) {
            showError("Error searching flights: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void refreshData() {
        populateFlightCodes();
        populateLocationChoices();
        clearTable();
        showInfo("All data refreshed successfully!");
        statusLabel.setText("💡 Flight codes and locations reloaded. Ready for selection.");
    }

    private void clearTable() {
        DefaultTableModel model = (DefaultTableModel) flightTable.getModel();
        model.setRowCount(0);
        if (flightChoice != null) flightChoice.setSelectedIndex(0);
        if (fromChoice != null) fromChoice.setSelectedIndex(0);
        if (toChoice != null) toChoice.setSelectedIndex(0);
        statusLabel.setText("💡 Select a flight code OR choose From/To locations to search for available flights");
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This inner class creates the "zebra stripe" effect (alternating row colors)
     * and allows for setting text alignment.
     */
    class ModernTableCellRenderer extends DefaultTableCellRenderer {

        public ModernTableCellRenderer(int alignment) {
            setHorizontalAlignment(alignment);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                                                     boolean isSelected, boolean hasFocus,
                                                     int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(Color.WHITE); // Even rows
                } else {
                    c.setBackground(ALT_ROW_COLOR); // Odd rows
                }
            }
            return c;
        }
    }
}