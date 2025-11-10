package AMS;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.awt.event.*;

public class CheckPaymentsDetails extends JFrame {
    JTextField text;
    JTable table;
    JButton show, backButton;

    public CheckPaymentsDetails() {
        initialize();
    }

    private void initialize() {
        setTitle("Payment Details - Pakistan Airlines");
        getContentPane().setBackground(new Color(245, 247, 250));
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Header Panel with solid color
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204));
        headerPanel.setBounds(0, 0, 1100, 100);
        headerPanel.setLayout(null);
        add(headerPanel);

        JLabel title = new JLabel("Payment Details");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 32));
        title.setBounds(50, 30, 400, 40);
        headerPanel.add(title);

        // Back Button - Rounded
        backButton = new RoundedButton("← Back");
        backButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        backButton.setBackground(new Color(220, 53, 69));
        backButton.setForeground(Color.WHITE);
        backButton.setFocusPainted(false);
        backButton.setBounds(900, 35, 120, 40);
        headerPanel.add(backButton);

        // Main Content Panel
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBounds(50, 130, 1000, 500);
        contentPanel.setLayout(null);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        add(contentPanel);

        // Search Section
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(248, 249, 250));
        searchPanel.setBounds(50, 30, 900, 80);
        searchPanel.setLayout(null);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));
        contentPanel.add(searchPanel);

        JLabel fcode = new JLabel("Enter Username:");
        fcode.setFont(new Font("Segoe UI", Font.BOLD, 16));
        fcode.setForeground(new Color(60, 60, 60));
        fcode.setBounds(30, 25, 150, 30);
        searchPanel.add(fcode);

        text = new JTextField();
        text.setBounds(180, 25, 350, 40);
        text.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        text.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(0, 15, 0, 15)
        ));
        searchPanel.add(text);

        // Show Button - Rounded
        show = new RoundedButton("Show Payments");
        show.setFont(new Font("Segoe UI", Font.BOLD, 14));
        show.setBackground(new Color(40, 167, 69));
        show.setForeground(Color.WHITE);
        show.setFocusPainted(false);
        show.setBounds(550, 25, 180, 40);
        searchPanel.add(show);

        // Table Panel
        JPanel tablePanel = new JPanel();
        tablePanel.setBackground(Color.WHITE);
        tablePanel.setBounds(50, 140, 900, 300);
        tablePanel.setLayout(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        contentPanel.add(tablePanel);

        // Table with modern styling
        table = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Custom table styling
        table.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        table.setRowHeight(35);
        table.setSelectionBackground(new Color(0, 102, 204));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(240, 240, 240));
        table.setShowGrid(true);
        
        // Table header styling
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
        header.setBackground(new Color(0, 102, 204));
        header.setForeground(Color.WHITE);
        header.setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(table);
        sp.setBorder(BorderFactory.createEmptyBorder());
        sp.getViewport().setBackground(Color.WHITE);
        tablePanel.add(sp, BorderLayout.CENTER);

        // Status indicator
        JPanel statusPanel = new JPanel();
        statusPanel.setBackground(new Color(248, 249, 250));
        statusPanel.setBounds(50, 460, 900, 30);
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        contentPanel.add(statusPanel);

        JLabel statusLabel = new JLabel("Showing successful payments only");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(100, 100, 100));
        statusPanel.add(statusLabel);

        // Action Listeners
        show.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPayments();
            }
        });

        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Add double-click listener to table
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    int row = table.rowAtPoint(evt.getPoint());
                    if (row >= 0) {
                        showRowDetails(row);
                    }
                }
            }
        });

        // Add hover effects
        addHoverEffects();

        setVisible(true);
    }

    // Rounded Button Class
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setFocusPainted(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Button background
            if (getModel().isPressed()) {
                g2.setColor(getBackground().darker());
            } else if (getModel().isRollover()) {
                g2.setColor(getBackground().brighter());
            } else {
                g2.setColor(getBackground());
            }
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);

            // Button text
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            // No border
        }
    }

    private void showPayments() {
        try {
            String usn = text.getText().trim();
            if (usn.isEmpty()) {
                showMessage("Please enter a username to search!", "Input Required", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ConnectionClass obj = new ConnectionClass();
            String query = "SELECT ticket_id, price, j_time, j_date, username, status " +
                          "FROM booking " +
                          "WHERE username = '" + usn + "' AND status = 'Success'";

            ResultSet res = obj.stm.executeQuery(query);
            
            // Create table model manually
            DefaultTableModel model = new DefaultTableModel();
            
            // Add column names
            model.addColumn("Ticket ID");
            model.addColumn("Amount (PKR)");
            model.addColumn("Time");
            model.addColumn("Date");
            model.addColumn("Username");
            model.addColumn("Status");
            
            boolean hasData = false;
            while (res.next()) {
                hasData = true;
                model.addRow(new Object[]{
                    res.getString("ticket_id"),
                    res.getString("price"),
                    res.getString("j_time"),
                    res.getString("j_date"),
                    res.getString("username"),
                    res.getString("status")
                });
            }
            
            if (!hasData) {
                showMessage("No successful payments found for username: " + usn, "No Results", JOptionPane.INFORMATION_MESSAGE);
                table.setModel(new DefaultTableModel());
                return;
            }
            
            table.setModel(model);
            
            // Auto-adjust column widths
            for (int column = 0; column < table.getColumnCount(); column++) {
                javax.swing.table.TableColumn tableColumn = table.getColumnModel().getColumn(column);
                int preferredWidth = tableColumn.getMinWidth();
                int maxWidth = tableColumn.getMaxWidth();
                
                for (int row = 0; row < table.getRowCount(); row++) {
                    javax.swing.table.TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
                    Component c = table.prepareRenderer(cellRenderer, row, column);
                    int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
                    preferredWidth = Math.max(preferredWidth, width);
                    
                    if (preferredWidth >= maxWidth) {
                        preferredWidth = maxWidth;
                        break;
                    }
                }
                tableColumn.setPreferredWidth(preferredWidth + 10);
            }
            
            res.close();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            showMessage("Error retrieving payment data!\nPlease check the username and try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showRowDetails(int row) {
        try {
            String ticketId = table.getValueAt(row, 0).toString();
            String amount = table.getValueAt(row, 1).toString();
            String time = table.getValueAt(row, 2).toString();
            String date = table.getValueAt(row, 3).toString();
            
            String message = String.format(
                "<html><div style='text-align: center;'>" +
                "<h3 style='color: #0066cc;'>Payment Details</h3>" +
                "<table align='center' cellpadding='5'>" +
                "<tr><td><b>Ticket ID:</b></td><td>%s</td></tr>" +
                "<tr><td><b>Amount:</b></td><td>%s PKR</td></tr>" +
                "<tr><td><b>Time:</b></td><td>%s</td></tr>" +
                "<tr><td><b>Date:</b></td><td>%s</td></tr>" +
                "</table></div></html>",
                ticketId, amount, time, date
            );
            
            JOptionPane.showMessageDialog(this, message, "Payment Details", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            // Ignore errors in row details
        }
    }

    private void showMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(this, message, title, messageType);
    }

    private void addHoverEffects() {
        // Show button hover effect
        show.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                show.setBackground(new Color(33, 136, 56));
                show.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                show.setBackground(new Color(40, 167, 69));
            }
        });

        // Back button hover effect
        backButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                backButton.setBackground(new Color(200, 35, 51));
                backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
            public void mouseExited(MouseEvent evt) {
                backButton.setBackground(new Color(220, 53, 69));
            }
        });

        // Text field focus effect
        text.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent evt) {
                text.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(0, 102, 204), 2),
                    BorderFactory.createEmptyBorder(0, 15, 0, 15)
                ));
            }
            public void focusLost(FocusEvent evt) {
                text.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                    BorderFactory.createEmptyBorder(0, 15, 0, 15)
                ));
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new CheckPaymentsDetails();
            }
        });
    }
}