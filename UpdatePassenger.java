package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
// Imports needed for custom components
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class UpdatePassenger extends JFrame implements ActionListener {

    // --- Fonts ---
    Font f_title, f_label, f_field;

    // --- Modern Color Palette (from Login) ---
    private final Color COLOR_PRIMARY = new Color(0, 102, 204);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private final Color COLOR_WHITE_TRANSLUCENT = new Color(255, 255, 255, 30);
    private final Color COLOR_ACCENT = new Color(255, 255, 255, 100);
    private final Color COLOR_DISABLED = new Color(200, 200, 200, 100);

    // --- Components ---
    JTextField tf_search_passport;
    ShadowLabel l_title, l_search_passport, l_user, l_name, l_age, l_dob, l_nat, l_addr, l_gen, l_ph, l_email, l_pass;
    PillButton bt_search, bt_update, bt_cancel, bt_clear;
    JTextField tf_username, tf_name, tf_age, tf_dob, tf_nat, tf_addr, tf_ph, tf_email, tf_pass, tf_gender;
    JLabel l1;

    public UpdatePassenger() {
        super("Update Passenger Details");
        setSize(950, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // === Custom Gradient Background Panel ===
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 950, 700);
        add(gradientPanel);

        // === Main Content Panel ===
        l1 = new JLabel();
        l1.setBounds(0, 0, 950, 700);
        l1.setLayout(null);
        l1.setOpaque(false);
        gradientPanel.add(l1);

        // --- Fonts ---
        f_title = new Font("Segoe UI", Font.BOLD, 32);
        f_label = new Font("Segoe UI", Font.BOLD, 16);
        f_field = new Font("Segoe UI", Font.PLAIN, 15);

        // --- Title with Enhanced Styling ---
        l_title = new ShadowLabel("Update Passenger Details");
        l_title.setFont(f_title);
        l_title.setForeground(Color.WHITE);
        l_title.setBounds(0, 30, 950, 50);
        l_title.setHorizontalAlignment(SwingConstants.CENTER);
        l1.add(l_title);

        // ==== SEARCH SECTION ====
        int formWidth = 800;
        int formStartX = (950 - formWidth) / 2;
        int y_pos = 100;
        int gap = 45;
        int field_h = 38;
        int label_w = 140;
        int field_w = 220;

        // Search by Passport
        l_search_passport = createLabel("Search by Passport No:");
        l_search_passport.setBounds(formStartX + 50, y_pos, label_w + 50, 30);
        l1.add(l_search_passport);
        
        tf_search_passport = createTextField();
        tf_search_passport.setBounds(formStartX + label_w + 100, y_pos, field_w, field_h);
        l1.add(tf_search_passport);
        
        bt_search = new PillButton("Search");
        bt_search.setFont(new Font("Segoe UI", Font.BOLD, 14));
        bt_search.setForeground(Color.WHITE);
        bt_search.setBaseColor(COLOR_PRIMARY);
        bt_search.setBounds(formStartX + label_w + field_w + 120, y_pos, 100, field_h);
        bt_search.addActionListener(this);
        l1.add(bt_search);

        // ==== SEPARATOR LINE ====
        JSeparator searchSeparator = new JSeparator();
        searchSeparator.setBounds(formStartX + 50, y_pos + 50, formWidth - 100, 2);
        searchSeparator.setForeground(COLOR_ACCENT);
        searchSeparator.setBackground(COLOR_ACCENT);
        l1.add(searchSeparator);

        // ==== FORM LAYOUT - Two Columns ====
        y_pos += 80;

        // --- Column 1 (Left) ---
        int col1_x = formStartX + 50;
        
        // Username
        l_user = createLabel("Username:");
        l_user.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_user);
        tf_username = createTextField();
        tf_username.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_username.setEditable(false); // Username should not be editable
        l1.add(tf_username);
        y_pos += gap;
        
        // Name
        l_name = createLabel("Name:");
        l_name.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_name);
        tf_name = createTextField();
        tf_name.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_name);
        y_pos += gap;
        
        // Age
        l_age = createLabel("Age:");
        l_age.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_age);
        tf_age = createTextField();
        tf_age.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_age);
        y_pos += gap;
        
        // Date of Birth
        l_dob = createLabel("Date of Birth:");
        l_dob.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_dob);
        tf_dob = createTextField();
        tf_dob.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_dob);
        y_pos += gap;
        
        // Nationality
        l_nat = createLabel("Nationality:");
        l_nat.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_nat);
        tf_nat = createTextField();
        tf_nat.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_nat);
        
        // --- Column 2 (Right) ---
        y_pos = 180; // Reset Y for second column
        int col2_x = formStartX + 450;
        
        // Address
        l_addr = createLabel("Address:");
        l_addr.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_addr);
        tf_addr = createTextField();
        tf_addr.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_addr);
        y_pos += gap;
        
        // Gender (Non-editable field)
        l_gen = createLabel("Gender:");
        l_gen.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_gen);
        tf_gender = createTextField();
        tf_gender.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_gender.setEditable(false); // Gender should not be editable
        tf_gender.setForeground(Color.WHITE);
        tf_gender.setBackground(COLOR_DISABLED);
        l1.add(tf_gender);
        y_pos += gap;
        
        // Phone
        l_ph = createLabel("Phone:");
        l_ph.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_ph);
        tf_ph = createTextField();
        tf_ph.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_ph);
        y_pos += gap;
        
        // Email
        l_email = createLabel("Email:");
        l_email.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_email);
        tf_email = createTextField();
        tf_email.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_email);
        y_pos += gap;
        
        // Passport No
        l_pass = createLabel("Passport No:");
        l_pass.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_pass);
        tf_pass = createTextField();
        tf_pass.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_pass.setEditable(false); // Passport should not be editable as it's used for search
        l1.add(tf_pass);
        
        // ==== Buttons Panel - Centered ====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 550, 950, 60);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 0));
        buttonPanel.setOpaque(false);
        l1.add(buttonPanel);

        bt_update = new PillButton("Update Details");
        bt_update.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_update.setForeground(Color.WHITE);
        bt_update.setBaseColor(COLOR_SUCCESS);
        bt_update.setPreferredSize(new Dimension(180, 45));
        bt_update.addActionListener(this);
        buttonPanel.add(bt_update);

        bt_clear = new PillButton("Clear");
        bt_clear.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_clear.setForeground(Color.WHITE);
        bt_clear.setBaseColor(COLOR_PRIMARY);
        bt_clear.setPreferredSize(new Dimension(120, 45));
        bt_clear.addActionListener(this);
        buttonPanel.add(bt_clear);

        bt_cancel = new PillButton("Cancel");
        bt_cancel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_cancel.setForeground(Color.WHITE);
        bt_cancel.setBaseColor(COLOR_DANGER);
        bt_cancel.setPreferredSize(new Dimension(120, 45));
        bt_cancel.addActionListener(this);
        buttonPanel.add(bt_cancel);

        // ==== Separator Line for Visual Appeal ====
        JSeparator separator = new JSeparator();
        separator.setBounds(formStartX + 50, 520, formWidth - 100, 2);
        separator.setForeground(COLOR_ACCENT);
        separator.setBackground(COLOR_ACCENT);
        l1.add(separator);

        // Initially disable update button until a passenger is found
        bt_update.setEnabled(false);

        setVisible(true);
    }

    /**
     * Helper method to create consistent labels
     */
    private ShadowLabel createLabel(String text) {
        ShadowLabel label = new ShadowLabel(text);
        label.setFont(f_label);
        label.setForeground(Color.WHITE);
        return label;
    }

    /**
     * Helper method to create consistent text fields
     */
    private JTextField createTextField() {
        JTextField tf = new JTextField();
        tf.setFont(f_field);
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

    // ===== SEARCH PASSENGER BY PASSPORT =====
    private void searchPassenger() {
        String passport = tf_search_passport.getText().trim();
        
        if (passport.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a passport number to search.", 
                "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM passenger WHERE passport = ?";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, passport);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                // Fill the form with passenger details
                tf_username.setText(rs.getString("username"));
                tf_name.setText(rs.getString("name"));
                tf_age.setText(rs.getString("age"));
                tf_dob.setText(rs.getString("dob"));
                tf_nat.setText(rs.getString("nationality"));
                tf_addr.setText(rs.getString("address"));
                
                // Set gender (non-editable)
                tf_gender.setText(rs.getString("gender"));
                
                tf_ph.setText(rs.getString("phone"));
                tf_email.setText(rs.getString("email"));
                tf_pass.setText(rs.getString("passport"));
                
                // Enable update button
                bt_update.setEnabled(true);
                
                JOptionPane.showMessageDialog(this, "Passenger found! You can now update the details.", 
                    "Search Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                clearFields();
                bt_update.setEnabled(false);
                JOptionPane.showMessageDialog(this, "No passenger found with passport number: " + passport, 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
            rs.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching passenger: " + ex.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            clearFields();
            bt_update.setEnabled(false);
        }
    }

    // Helper to clear text fields
    private void clearFields() {
        tf_username.setText("");
        tf_name.setText("");
        tf_age.setText("");
        tf_dob.setText("");
        tf_nat.setText("");
        tf_addr.setText("");
        tf_gender.setText("");
        tf_ph.setText("");
        tf_email.setText("");
        tf_pass.setText("");
        bt_update.setEnabled(false);
    }

    // ===== ACTION HANDLING =====
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_search) {
            searchPassenger();
            
        } else if (e.getSource() == bt_update) {
            updatePassenger();
            
        } else if (e.getSource() == bt_clear) {
            clearFields();
            tf_search_passport.setText("");
            tf_search_passport.requestFocus();
            
        } else if (e.getSource() == bt_cancel) {
            dispose();
        }
    }

    // ===== UPDATE PASSENGER DETAILS =====
    private void updatePassenger() {
        // Validate all fields
        if (tf_name.getText().trim().isEmpty() || tf_age.getText().trim().isEmpty() || 
            tf_dob.getText().trim().isEmpty() || tf_nat.getText().trim().isEmpty() || 
            tf_addr.getText().trim().isEmpty() || tf_ph.getText().trim().isEmpty() || 
            tf_email.getText().trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "Please fill in all fields with valid data.", 
                "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String passport = tf_pass.getText();

            // Update query (gender is NOT included as it's not editable)
            String q = "UPDATE passenger SET name=?, age=?, dob=?, nationality=?, address=?, phone=?, email=? WHERE passport=?";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, tf_name.getText());
            pst.setString(2, tf_age.getText());
            pst.setString(3, tf_dob.getText());
            pst.setString(4, tf_nat.getText());
            pst.setString(5, tf_addr.getText());
            pst.setString(6, tf_ph.getText());
            pst.setString(7, tf_email.getText());
            pst.setString(8, passport);

            int updated = pst.executeUpdate();

            if (updated > 0) {
                JOptionPane.showMessageDialog(this, "✅ Passenger details updated successfully!");
                // Clear search field after successful update
                tf_search_passport.setText("");
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Update failed. No records were changed.");
            }
            
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error during update: " + ex.getMessage(), 
                "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UpdatePassenger().setVisible(true));
    }
}

// #############################################################################
// ##                                                                         ##
// ##     HELPER CLASSES (Keep the same as your original)                    ##
// ##                                                                         ##
// #############################################################################

/**
 * A custom JPanel that paints itself with a linear gradient background.
 */
class GradientPanel extends JPanel {
    private Color color1 = new Color(40, 60, 100);  // Dark Blue/Grey
    private Color color2 = new Color(10, 20, 40);   // Even Darker Blue/Grey

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

        // Draw Shadow
        g2.setColor(shadowColor);
        g2.drawString(text, x + shadowOffsetX, y + shadowOffsetY);
        
        // Draw Text
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