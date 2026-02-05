package AMS;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class ViewCanceledTicket extends JFrame implements ActionListener{
    JTable t;
    String[] x = {"Ticket Id","Departure", "Arrival", "Class", "Price", "Flight Code", "Flight Name", "Journey Date", "Journey Time", "Username", "Name","Reason"};
    String[][] y = new String[100][12];
    int i = 0, j = 0;
    Font f;
    
    ViewCanceledTicket(){
        super("All Cancel Flight Details");
        setSize(1300, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    
        f = new Font("Segoe UI", Font.PLAIN, 14);
       
        try {
            System.out.println("Attempting to connect to database...");
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM cancel";
            System.out.println("Executing query: " + q);
            
            ResultSet rest = obj.stm.executeQuery(q);
            
            int i = 0;
            while(rest.next() && i < y.length){
                System.out.println("Processing row " + i + "...");
                
                // Map database columns to table columns correctly
                y[i][0] = rest.getString("ticket_id");
                System.out.println("Ticket ID: " + y[i][0]);
                
                y[i][1] = rest.getString("source");  // Displayed as "Departure"
                y[i][2] = rest.getString("destination");  // Displayed as "Arrival"
                y[i][3] = rest.getString("class_name");  // Displayed as "Class"
                y[i][4] = rest.getString("price");
                y[i][5] = rest.getString("flight_code");
                y[i][6] = rest.getString("f_name");  // Displayed as "Flight Name"
                y[i][7] = rest.getString("journey_date");
                y[i][8] = rest.getString("journey_time");
                y[i][9] = rest.getString("username");
                y[i][10] = rest.getString("passenger_name");  // Displayed as "Name"
                y[i][11] = rest.getString("cancellation_reason");  // Displayed as "Reason"
                 
                System.out.println("Successfully loaded row " + i + " with Ticket ID: " + y[i][0]);
                i++;
            }
            
            System.out.println("Total rows fetched from database: " + i);
            
            if (i == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No canceled tickets found in the database.\nPlease check if data exists in the 'cancel' table.", 
                    "No Data Found", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            // Debug: Print what's in the y array
            System.out.println("\n=== DATA IN Y ARRAY ===");
            for (int row = 0; row < i && row < 5; row++) {
                System.out.println("Row " + row + ":");
                for (int col = 0; col < 12; col++) {
                    System.out.println("  y[" + row + "][" + col + "] = " + 
                        (y[row][col] != null ? y[row][col] : "null"));
                }
                System.out.println();
            }
            
            // Create table with data
            t = new JTable(y, x) {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            
        } catch(SQLException ex) {
            System.err.println("SQL Exception occurred:");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "SQL Error: " + ex.getMessage() + 
                "\nError Code: " + ex.getErrorCode() +
                "\nSQL State: " + ex.getSQLState(),
                "Database Error", 
                JOptionPane.ERROR_MESSAGE);
        } catch(Exception ex) {
            System.err.println("General Exception occurred:");
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Error loading canceled tickets data: " + ex.getMessage(),
                "Error", 
                JOptionPane.ERROR_MESSAGE);
        }
        
        // Set table properties
        if (t != null) {
            t.setFont(f);
            t.setRowHeight(25);
            t.setBackground(Color.WHITE);
            t.setForeground(Color.BLACK);
            t.setGridColor(Color.GRAY);
            
            // Style table header
            t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
            t.getTableHeader().setBackground(new Color(70, 130, 180));
            t.getTableHeader().setForeground(Color.WHITE);
            
            JScrollPane js = new JScrollPane(t);
            add(js);
            
            System.out.println("Table created successfully with " + t.getRowCount() + " rows");
        } else {
            JOptionPane.showMessageDialog(this, 
                "Failed to create table. No data available.", 
                "Table Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
       
    public void actionPerformed(ActionEvent e){}
    
    public static void main(String[] args){
        // Set system look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(() -> {
            ViewCanceledTicket frame = new ViewCanceledTicket();
            frame.setVisible(true);
            System.out.println("ViewCanceledTicket frame displayed");
        });
    }
}