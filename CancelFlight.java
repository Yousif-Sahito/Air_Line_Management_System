package AMS;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class CancelFlight extends JFrame implements ActionListener, ItemListener {

    // --- Modern Color Palette ---
    private final Color COLOR_PRIMARY = new Color(0, 102, 204);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_WARNING = new Color(255, 193, 7);
    private final Color COLOR_WHITE_TRANSLUCENT = new Color(255, 255, 255, 30);

    // --- Components ---
    ShadowLabel l_title, l_ticket, l_source, l_dest, l_class, l_price, l_flightCode, l_flightName, l_journeyTime, l_journeyDate, l_username, l_name, l_reason;
    PillButton bt_cancel, bt_back;
    JTextField tf_source, tf_dest, tf_class, tf_price, tf_flightCode, tf_flightName, tf_journeyTime, tf_journeyDate, tf_username, tf_name, tf_reason;
    JComboBox<String> ch_ticket;
    JLabel l1;

    public CancelFlight() {
        super("Cancel Flight Ticket");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // === Custom Gradient Background Panel ===
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 1000, 700);
        add(gradientPanel);

        // === Main Content Panel ===
        l1 = new JLabel();
        l1.setBounds(0, 0, 1000, 700);
        l1.setLayout(null);
        l1.setOpaque(false);
        gradientPanel.add(l1);

        // --- Fonts ---
        Font f_title = new Font("Segoe UI", Font.BOLD, 32);
        Font f_label = new Font("Segoe UI", Font.BOLD, 16);
        Font f_field = new Font("Segoe UI", Font.PLAIN, 15);

        // ==== TITLE SECTION ====
        l_title = new ShadowLabel("Cancel Your Flight Ticket");
        l_title.setFont(f_title);
        l_title.setForeground(Color.WHITE);
        l_title.setBounds(0, 30, 1000, 50);
        l_title.setHorizontalAlignment(SwingConstants.CENTER);
        l1.add(l_title);

        // ==== FORM LAYOUT - Two Columns ====
        int formWidth = 900;
        int formStartX = (1000 - formWidth) / 2;
        int y_pos = 100;
        int gap = 45;
        int field_h = 38;
        int label_w = 140;
        int field_w = 220;

        // --- Column 1 (Left) ---
        int col1_x = formStartX + 50;
        
        // Ticket ID
        l_ticket = createLabel("Ticket ID:");
        l_ticket.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_ticket);
        ch_ticket = new JComboBox<>();
        ch_ticket.setFont(f_field);
        ch_ticket.setBackground(new Color(255, 255, 255, 200));
        ch_ticket.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        ch_ticket.setRenderer(new CustomComboBoxRenderer());
        l1.add(ch_ticket);
        y_pos += gap;
        
        // Source
        l_source = createLabel("Source:");
        l_source.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_source);
        tf_source = createTextField();
        tf_source.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_source.setEditable(false);
        l1.add(tf_source);
        y_pos += gap;
        
        // Destination
        l_dest = createLabel("Destination:");
        l_dest.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_dest);
        tf_dest = createTextField();
        tf_dest.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_dest.setEditable(false);
        l1.add(tf_dest);
        y_pos += gap;
        
        // Class
        l_class = createLabel("Class:");
        l_class.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_class);
        tf_class = createTextField();
        tf_class.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_class.setEditable(false);
        l1.add(tf_class);
        y_pos += gap;
        
        // Price
        l_price = createLabel("Price:");
        l_price.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_price);
        tf_price = createTextField();
        tf_price.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_price.setEditable(false);
        l1.add(tf_price);
        
        // --- Column 2 (Right) ---
        y_pos = 100; // Reset Y for second column
        int col2_x = formStartX + 500;
        
        // Flight Code
        l_flightCode = createLabel("Flight Code:");
        l_flightCode.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_flightCode);
        tf_flightCode = createTextField();
        tf_flightCode.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_flightCode.setEditable(false);
        l1.add(tf_flightCode);
        y_pos += gap;
        
        // Flight Name
        l_flightName = createLabel("Flight Name:");
        l_flightName.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_flightName);
        tf_flightName = createTextField();
        tf_flightName.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_flightName.setEditable(false);
        l1.add(tf_flightName);
        y_pos += gap;
        
        // Journey Date
        l_journeyDate = createLabel("Journey Date:");
        l_journeyDate.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_journeyDate);
        tf_journeyDate = createTextField();
        tf_journeyDate.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_journeyDate.setEditable(false);
        l1.add(tf_journeyDate);
        y_pos += gap;
        
        // Journey Time
        l_journeyTime = createLabel("Journey Time:");
        l_journeyTime.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_journeyTime);
        tf_journeyTime = createTextField();
        tf_journeyTime.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_journeyTime.setEditable(false);
        l1.add(tf_journeyTime);
        y_pos += gap;
        
        // Username
        l_username = createLabel("Username:");
        l_username.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_username);
        tf_username = createTextField();
        tf_username.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_username.setEditable(false);
        l1.add(tf_username);
        y_pos += gap;
        
        // Name
        l_name = createLabel("Passenger Name:");
        l_name.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_name);
        tf_name = createTextField();
        tf_name.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_name.setEditable(false);
        l1.add(tf_name);

        // ==== REASON FIELD - Full Width ====
        y_pos += gap + 20;
        l_reason = createLabel("Cancellation Reason:");
        l_reason.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_reason);
        tf_reason = new JTextField();
        tf_reason.setFont(f_field);
        tf_reason.setOpaque(false); 
        tf_reason.setBackground(COLOR_WHITE_TRANSLUCENT); 
        tf_reason.setForeground(Color.WHITE); 
        tf_reason.setCaretColor(Color.WHITE); 
        tf_reason.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        tf_reason.setBounds(col1_x + label_w, y_pos, formWidth - 100, field_h);
        tf_reason.setEditable(true);
        tf_reason.setToolTipText("Please provide reason for cancellation");
        l1.add(tf_reason);

        // ==== SEPARATOR LINE ====
        JSeparator separator = new JSeparator();
        separator.setBounds(formStartX + 50, 520, formWidth - 100, 2);
        separator.setForeground(new Color(255, 255, 255, 100));
        separator.setBackground(new Color(255, 255, 255, 100));
        l1.add(separator);

        // ==== BUTTONS PANEL - Centered ====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 560, 1000, 60);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        l1.add(buttonPanel);

        bt_cancel = new PillButton("Cancel Flight");
        bt_cancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_cancel.setForeground(Color.WHITE);
        bt_cancel.setBaseColor(COLOR_WARNING);
        bt_cancel.setPreferredSize(new Dimension(180, 45));
        bt_cancel.addActionListener(this);
        buttonPanel.add(bt_cancel);

        bt_back = new PillButton("Back");
        bt_back.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_back.setForeground(Color.WHITE);
        bt_back.setBaseColor(COLOR_DANGER);
        bt_back.setPreferredSize(new Dimension(180, 45));
        bt_back.addActionListener(this);
        buttonPanel.add(bt_back);

        // ===== LOAD TICKET IDs =====
        loadTicketIds();
        
        // Add item listener for ticket selection
        ch_ticket.addItemListener(this);

        setVisible(true);
    }

    /**
     * Helper method to create consistent labels
     */
    private ShadowLabel createLabel(String text) {
        ShadowLabel label = new ShadowLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 16));
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Helper method to create consistent text fields
     */
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tf.setOpaque(false); 
        tf.setBackground(COLOR_WHITE_TRANSLUCENT); 
        tf.setForeground(Color.WHITE); 
        tf.setCaretColor(Color.WHITE); 
        tf.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        return tf;
    }

    // ===== LOAD TICKET IDs FROM DATABASE =====
    private void loadTicketIds() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT DISTINCT ticket_id FROM booking WHERE status = 'Success'";
            ResultSet rs = obj.stm.executeQuery(q);
            ch_ticket.addItem("--- Select Ticket ID ---");
            while (rs.next()) {
                ch_ticket.addItem(rs.getString("ticket_id"));
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading ticket IDs: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // ===== FILL DETAILS WHEN TICKET SELECTED =====
    private void fillBookingDetails(String ticketId) {
        if (ticketId == null || ticketId.contains("---")) {
            clearFields();
            return;
        }
        
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM booking WHERE ticket_id = ? AND status = 'Success'";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, ticketId);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                tf_source.setText(rs.getString("source"));
                tf_dest.setText(rs.getString("destination"));
                tf_class.setText(rs.getString("class_name"));
                tf_price.setText(rs.getString("price"));
                tf_flightCode.setText(rs.getString("flight_code"));
                tf_flightName.setText(rs.getString("f_name"));
                tf_journeyDate.setText(rs.getString("j_date"));
                tf_journeyTime.setText(rs.getString("j_time"));
                tf_username.setText(rs.getString("username"));
                tf_name.setText(rs.getString("name"));
            } else {
                clearFields();
                JOptionPane.showMessageDialog(this, "No active booking found for this ticket ID.",
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
            rs.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching booking details: " + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
            clearFields();
        }
    }
    
    // Helper to clear text fields
    private void clearFields() {
        tf_source.setText("");
        tf_dest.setText("");
        tf_class.setText("");
        tf_price.setText("");
        tf_flightCode.setText("");
        tf_flightName.setText("");
        tf_journeyDate.setText("");
        tf_journeyTime.setText("");
        tf_username.setText("");
        tf_name.setText("");
        tf_reason.setText("");
    }

    // ===== ITEM LISTENER FOR TICKET SELECTION =====
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == ch_ticket && e.getStateChange() == ItemEvent.SELECTED) {
            String selectedTicket = (String) ch_ticket.getSelectedItem();
            if (selectedTicket != null && !selectedTicket.contains("---")) {
                fillBookingDetails(selectedTicket);
            } else {
                clearFields();
            }
        }
    }

    // ===== ACTION HANDLING =====
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_cancel) {
            cancelFlight();
        } else if (e.getSource() == bt_back) {
            dispose();
        }
    }

    // ===== CANCEL FLIGHT FUNCTIONALITY =====
    private void cancelFlight() {
        String ticketId = (String) ch_ticket.getSelectedItem();
        String reason = tf_reason.getText().trim();
        
        if (ticketId == null || ticketId.contains("---")) {
            JOptionPane.showMessageDialog(this, "Please select a ticket to cancel.",
                "Selection Error", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (reason.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please provide a cancellation reason.",
                "Reason Required", JOptionPane.WARNING_MESSAGE);
            tf_reason.requestFocus();
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Are you sure you want to cancel ticket: " + ticketId + "?\nThis action cannot be undone.",
            "Confirm Cancellation", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        
        Connection conn = null;
        PreparedStatement pstUpdate = null;
        PreparedStatement pstInsert = null;
        PreparedStatement pstSelect = null;
        
        try {
            ConnectionClass obj = new ConnectionClass();
            conn = obj.con;
            
            // Start transaction
            conn.setAutoCommit(false);
            
            // First, get all booking details before updating
            String selectQuery = "SELECT * FROM booking WHERE ticket_id = ? AND status = 'Success'";
            pstSelect = conn.prepareStatement(selectQuery);
            pstSelect.setString(1, ticketId);
            ResultSet rs = pstSelect.executeQuery();
            
            if (!rs.next()) {
                JOptionPane.showMessageDialog(this, "Ticket not found or already cancelled.",
                    "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Store booking details
            String source = rs.getString("source");
            String destination = rs.getString("destination");
            String className = rs.getString("class_name");
            String price = rs.getString("price");
            String flightCode = rs.getString("flight_code");
            String flightName = rs.getString("f_name");
            String journeyDate = rs.getString("j_date");
            String journeyTime = rs.getString("j_time");
            String username = rs.getString("username");
            String passengerName = rs.getString("name");
            String age = rs.getString("age") != null ? rs.getString("age") : "";
            String dob = rs.getString("dob") != null ? rs.getString("dob") : "";
            String passport = rs.getString("passport") != null ? rs.getString("passport") : "";
            
            rs.close();
            
            // STEP 1: Update booking status to 'Cancelled'
            String updateQuery = "UPDATE booking SET status = 'Cancelled', cancellation_reason = ? WHERE ticket_id = ?";
            pstUpdate = conn.prepareStatement(updateQuery);
            pstUpdate.setString(1, reason);
            pstUpdate.setString(2, ticketId);
            
            int updated = pstUpdate.executeUpdate();
            
            if (updated > 0) {
                // STEP 2: Insert into cancel table
                String insertQuery = "INSERT INTO cancel (" +
                    "ticket_id, source, destination, class_name, f_name, price, " +
                    "flight_code, username, passenger_name, age, dob, passport, " +
                    "journey_time, journey_date, cancellation_reason, refund_status, refund_amount" +
                    ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                
                pstInsert = conn.prepareStatement(insertQuery);
                pstInsert.setString(1, ticketId);
                pstInsert.setString(2, source);
                pstInsert.setString(3, destination);
                pstInsert.setString(4, className);
                pstInsert.setString(5, flightName);
                pstInsert.setString(6, price);
                pstInsert.setString(7, flightCode);
                pstInsert.setString(8, username);
                pstInsert.setString(9, passengerName);
                pstInsert.setString(10, age);
                pstInsert.setString(11, dob);
                pstInsert.setString(12, passport);
                pstInsert.setString(13, journeyTime);
                pstInsert.setString(14, journeyDate);
                pstInsert.setString(15, reason);
                pstInsert.setString(16, "Pending"); // refund_status
                pstInsert.setString(17, calculateRefundAmount(price)); // refund_amount
                
                int inserted = pstInsert.executeUpdate();
                
                if (inserted > 0) {
                    // Commit transaction
                    conn.commit();
                    
                    JOptionPane.showMessageDialog(this, 
                        "✅ Flight ticket " + ticketId + " has been cancelled successfully!\n" +
                        "Refund will be processed as per policy.",
                        "Cancellation Successful", JOptionPane.INFORMATION_MESSAGE);
                    
                    // Remove cancelled ticket from dropdown
                    ch_ticket.removeItem(ticketId);
                    clearFields();
                    if (ch_ticket.getItemCount() > 0) {
                        ch_ticket.setSelectedIndex(0);
                    }
                } else {
                    // Rollback if insert failed
                    conn.rollback();
                    JOptionPane.showMessageDialog(this, 
                        "Cancellation failed: Could not save cancellation details.",
                        "Cancellation Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Cancellation failed. Ticket not found or already cancelled.",
                    "Cancellation Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception ex) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database Error during cancellation: " + ex.getMessage(),
                "Cancellation Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (pstSelect != null) pstSelect.close();
                if (pstUpdate != null) pstUpdate.close();
                if (pstInsert != null) pstInsert.close();
                if (conn != null) conn.setAutoCommit(true);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // Helper method to calculate refund amount
    private String calculateRefundAmount(String price) {
        try {
            double originalPrice = Double.parseDouble(price);
            // Example: 90% refund for cancellation
            double refundAmount = originalPrice * 0.9;
            return String.valueOf((int) refundAmount);
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CancelFlight().setVisible(true));
    }
}

// =========================================================================
// CUSTOM COMBO BOX RENDERER FOR BETTER STYLING
// =========================================================================
class CustomComboBoxRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, 
                                                  boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        
        label.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        label.setBorder(BorderFactory.createEmptyBorder(5, 8, 5, 8));
        
        if (isSelected) {
            label.setBackground(new Color(0, 102, 204, 200));
            label.setForeground(Color.WHITE);
        } else {
            label.setBackground(Color.WHITE);
            label.setForeground(Color.BLACK);
        }
        
        return label;
    }
}

/**
 * A custom JPanel that paints itself with a linear gradient background.
 */
class GradientPanel extends JPanel {
    private Color color1 = new Color(40, 60, 100);
    private Color color2 = new Color(10, 20, 40);

    public GradientPanel() {
        setLayout(null);
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

/**
 * A custom JLabel that paints its text with a subtle drop shadow.
 */
class ShadowLabel extends JLabel {
    
    private Color shadowColor = new Color(0, 0, 0, 150);
    private int shadowOffsetX = 2;
    private int shadowOffsetY = 2;

    public ShadowLabel(String text) {
        super(text);
    }
    
    public ShadowLabel(Icon image) {
        super(image);
    }

    @Override
    protected void paintComponent(Graphics g) {
        String text = getText(); 
        if (text == null || text.isEmpty()) {
            super.paintComponent(g);
            return; 
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, 
                            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        
        Insets insets = getInsets();
        int x = insets.left;
        int y = insets.top + g2.getFontMetrics().getAscent();

        if (getHorizontalAlignment() == CENTER) {
            x = (getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        } else if (getHorizontalAlignment() == RIGHT) {
            x = getWidth() - insets.right - g2.getFontMetrics().stringWidth(text);
        }

        if (getVerticalAlignment() == CENTER) {
            y = (getHeight() + g2.getFontMetrics().getAscent() - g2.getFontMetrics().getDescent()) / 2;
        } else if (getVerticalAlignment() == BOTTOM) {
            y = getHeight() - insets.bottom - g2.getFontMetrics().getDescent();
        }

        g2.setColor(shadowColor);
        g2.drawString(text, x + shadowOffsetX, y + shadowOffsetY);
        
        g2.setColor(getForeground());
        g2.drawString(text, x, y);
        
        g2.dispose();
    }
}

/**
 * A custom JButton class that paints itself as a pill-shaped button
 */
class PillButton extends JButton {

    private Color baseColor; 
    private int borderRadius = 999; 

    public PillButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBaseColor(new Color(200, 0, 0)); 
    }

    public void setBaseColor(Color baseColor) {
        this.baseColor = baseColor;
        repaint();
    }

    public Color getBaseColor() {
        return baseColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arc = height; 

        Color endColor = baseColor.darker().darker();
        Color highlightColor = baseColor.brighter().brighter();

        if (getModel().isPressed()) {
            endColor = baseColor.darker().darker().darker();
            highlightColor = baseColor.darker();
            g2.translate(0, 1); 
        } else if (getModel().isRollover()) {
            endColor = baseColor.darker();
            highlightColor = baseColor.brighter().brighter().brighter();
        }

        Paint paint = new LinearGradientPaint(0, 0, 0, height,
                                              new float[]{0f, 1f},
                                              new Color[]{highlightColor, endColor});
        g2.setPaint(paint);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, arc, arc));

        g2.setColor(new Color(255, 255, 255, 50)); 
        g2.draw(new RoundRectangle2D.Float(1, 1, width - 2, height - 2, arc, arc));
        g2.setColor(new Color(0, 0, 0, 50)); 
        g2.draw(new RoundRectangle2D.Float(0, 0, width - 1, height - 1, arc, arc));

        if (getModel().isPressed()) {
            g2.translate(0, -1);
        }
        
        super.paintComponent(g);
        g2.dispose();
    }
}