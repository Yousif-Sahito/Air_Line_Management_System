

package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class ViewBookedFlight extends JFrame {
    
    JTable t;
    
    // 11 column headers including Status
    String[] x = {
        "Ticket ID", "Source", "Destination", "Class", "Price", "Flight Code", 
        "Journey Time", "Journey Date", "Username", "Passenger Name", "Status"
    };
    
    // Changed to 11 columns to match headers
    String[][] y = new String[100][11]; 
    int i = 0;
    Font f;
    
    ViewBookedFlight() {
        super("Flight Booking Details");
        setSize(1300, 400);
        setLocation(0, 10);
        f = new Font("MS UI Gothic", Font.BOLD, 17);

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM booking ORDER BY ticket_id";
            ResultSet res = obj.stm.executeQuery(q);
            
            while (res.next() && i < y.length) {
                // All 11 columns including Status
                y[i][0] = res.getString("ticket_id");
                y[i][1] = res.getString("source");
                y[i][2] = res.getString("destination");
                y[i][3] = res.getString("class_name");
                y[i][4] = "Rs. " + res.getString("price"); // Added currency symbol
                y[i][5] = res.getString("flight_code");
                y[i][6] = res.getString("j_time");
                y[i][7] = res.getString("j_date");
                y[i][8] = res.getString("username");
                y[i][9] = res.getString("name");
                y[i][10] = res.getString("status");
                
                i++;
            }
            
            // Create the table
            t = new JTable(y, x);
            t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            t.setRowHeight(30);
            t.setBackground(Color.WHITE);
            t.setForeground(Color.BLACK);
            t.setGridColor(Color.GRAY);
            
            // Center align all columns
            javax.swing.table.DefaultTableCellRenderer centerRenderer = new javax.swing.table.DefaultTableCellRenderer();
            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
            for (int col = 0; col < t.getColumnCount(); col++) {
                t.getColumnModel().getColumn(col).setCellRenderer(centerRenderer);
            }
            
            // Make Status column show colors based on status
            t.getColumnModel().getColumn(10).setCellRenderer(new StatusCellRenderer());
            
            // Add the table to a scroll pane
            JScrollPane js = new JScrollPane(t);
            js.getViewport().setBackground(Color.WHITE);
            add(js);
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data: " + ex.getMessage());
        }
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
    
    // Custom cell renderer for Status column
    class StatusCellRenderer extends javax.swing.table.DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            if (value != null) {
                String status = value.toString();
                setHorizontalAlignment(JLabel.CENTER);
                setFont(new Font("Segoe UI", Font.BOLD, 14));
                
                if ("Success".equalsIgnoreCase(status)) {
                    setBackground(new Color(220, 255, 220)); // Light green
                    setForeground(new Color(0, 128, 0)); // Dark green
                } else if ("Cancelled".equalsIgnoreCase(status)) {
                    setBackground(new Color(255, 220, 220)); // Light red
                    setForeground(new Color(128, 0, 0)); // Dark red
                } else {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }
            }
            
            if (isSelected) {
                setBackground(new Color(200, 200, 255)); // Selection color
            }
            
            return c;
        }
    }
    
    public static void main(String[] args) {
        // Use invokeLater for thread-safe GUI creation
        SwingUtilities.invokeLater(() -> new ViewBookedFlight());
    }
}