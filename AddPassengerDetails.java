package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
// Imports needed for custom components
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class AddPassengerDetails extends JFrame implements ActionListener {

    // --- Modern Color Palette ---
    private final Color COLOR_PRIMARY = new Color(0, 102, 204);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private final Color COLOR_WHITE_TRANSLUCENT = new Color(255, 255, 255, 30);

    // --- Components ---
    PillButton bt_save, bt_close;
    ShadowLabel l_title, l_username, l_name, l_age, l_dob, l_addr, l_phone, l_email, l_nat, l_gen, l_pass;
    JTextField tf_username, tf_name, tf_age, tf_dob, tf_addr, tf_phone, tf_email, tf_nat, tf_pass;
    JComboBox<String> cb_gender;
    JLabel l1;

    public AddPassengerDetails() {
        super("Add Passenger Details");
        setSize(900, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // === Custom Gradient Background Panel ===
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 900, 650);
        add(gradientPanel);

        // === Main Content Panel ===
        l1 = new JLabel();
        l1.setBounds(0, 0, 900, 650);
        l1.setLayout(null);
        l1.setOpaque(false);
        gradientPanel.add(l1);

        // --- Fonts ---
        Font f_title = new Font("Segoe UI", Font.BOLD, 32);
        Font f_label = new Font("Segoe UI", Font.BOLD, 16);
        Font f_field = new Font("Segoe UI", Font.PLAIN, 15);

        // ==== TITLE SECTION ====
        l_title = new ShadowLabel("Add New Passenger");
        l_title.setFont(f_title);
        l_title.setForeground(Color.WHITE);
        l_title.setBounds(0, 40, 900, 50);
        l_title.setHorizontalAlignment(SwingConstants.CENTER);
        l1.add(l_title);

        // ==== FORM LAYOUT - Two Columns ====
        int formWidth = 800;
        int formStartX = (900 - formWidth) / 2;
        int y_pos = 120;
        int gap = 45;
        int field_h = 38;
        int label_w = 140;
        int field_w = 220;

        // --- Column 1 (Left) ---
        int col1_x = formStartX + 50;
        
        // Username
        l_username = createLabel("Username:");
        l_username.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_username);
        tf_username = createTextField();
        tf_username.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_username);
        y_pos += gap;
        
        // Age
        l_age = createLabel("Age:");
        l_age.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_age);
        tf_age = createTextField();
        tf_age.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_age);
        y_pos += gap;
        
        // Address
        l_addr = createLabel("Address:");
        l_addr.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_addr);
        tf_addr = createTextField();
        tf_addr.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_addr);
        y_pos += gap;
        
        // Email
        l_email = createLabel("Email ID:");
        l_email.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_email);
        tf_email = createTextField();
        tf_email.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_email);
        y_pos += gap;
        
        // Gender
        l_gen = createLabel("Gender:");
        l_gen.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_gen);
        
        // Create gender combo box
        String[] genderOptions = {"Select Gender", "Male", "Female", "Other"};
        cb_gender = createComboBox(genderOptions);
        cb_gender.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        l1.add(cb_gender);
        
        // --- Column 2 (Right) ---
        y_pos = 120; // Reset Y for second column
        int col2_x = formStartX + 450;
        
        // Name
        l_name = createLabel("Name:");
        l_name.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_name);
        tf_name = createTextField();
        tf_name.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_name);
        y_pos += gap;
        
        // Date of Birth
        l_dob = createLabel("Date of Birth:");
        l_dob.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_dob);
        tf_dob = createTextField();
        tf_dob.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_dob);
        y_pos += gap;
        
        // Phone
        l_phone = createLabel("Phone:");
        l_phone.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_phone);
        tf_phone = createTextField();
        tf_phone.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_phone);
        y_pos += gap;
        
        // Nationality
        l_nat = createLabel("Nationality:");
        l_nat.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_nat);
        tf_nat = createTextField();
        tf_nat.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_nat);
        y_pos += gap;
        
        // Passport No
        l_pass = createLabel("Passport No:");
        l_pass.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_pass);
        tf_pass = createTextField();
        tf_pass.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        l1.add(tf_pass);

        // ==== SEPARATOR LINE ====
        JSeparator separator = new JSeparator();
        separator.setBounds(formStartX + 50, 480, formWidth - 100, 2);
        separator.setForeground(new Color(255, 255, 255, 100));
        separator.setBackground(new Color(255, 255, 255, 100));
        l1.add(separator);

        // ==== BUTTONS PANEL - Centered ====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 500, 900, 60);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        l1.add(buttonPanel);

        bt_save = new PillButton("Save Passenger");
        bt_save.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_save.setForeground(Color.WHITE);
        bt_save.setBaseColor(COLOR_SUCCESS);
        bt_save.setPreferredSize(new Dimension(180, 45));
        bt_save.addActionListener(this);
        buttonPanel.add(bt_save);

        bt_close = new PillButton("Close");
        bt_close.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_close.setForeground(Color.WHITE);
        bt_close.setBaseColor(COLOR_DANGER);
        bt_close.setPreferredSize(new Dimension(180, 45));
        bt_close.addActionListener(this);
        buttonPanel.add(bt_close);

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
    
    /**
     * Helper method to create consistent combo boxes
     */
    private JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> cb = new JComboBox<>(items);
        cb.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        cb.setBackground(new Color(255, 255, 255, 200));
        cb.setForeground(Color.BLACK);
        cb.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, 
                    int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (isSelected) {
                    c.setBackground(COLOR_PRIMARY);
                    c.setForeground(Color.WHITE);
                } else {
                    c.setBackground(Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });
        return cb;
    }

    // ===== ACTION HANDLING =====
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_save) {
            String username = tf_username.getText();
            String name = tf_name.getText();
            String age = tf_age.getText();
            String dob = tf_dob.getText();
            String address = tf_addr.getText();
            String phone = tf_phone.getText();
            String email = tf_email.getText();
            String nationality = tf_nat.getText();
            String passport = tf_pass.getText();
            
            // Get selected gender
            String gender = (String) cb_gender.getSelectedItem();

            // Basic validation
            if (username.isEmpty() || name.isEmpty() || age.isEmpty() || dob.isEmpty() || 
                address.isEmpty() || phone.isEmpty() || email.isEmpty() || nationality.isEmpty() || 
                passport.isEmpty() || gender.equals("Select Gender")) {
                
                JOptionPane.showMessageDialog(this, "Please fill in all fields and select a valid gender!", 
                    "Validation Error", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                ConnectionClass obj = new ConnectionClass();

                // Insert data into passenger table (ORIGINAL LOGIC PRESERVED)
                String q = "INSERT INTO passenger(username, name, age, dob, address, phone, email, nationality, gender, passport) "
                        + "VALUES ('" + username + "','" + name + "','" + age + "','" + dob + "','" + address + "','" + phone + "','" + email + "','" + nationality + "','" + gender + "','" + passport + "')";

                obj.stm.executeUpdate(q);
                JOptionPane.showMessageDialog(null, "✅ Passenger details saved successfully!");
                System.out.println("Passenger details inserted successfully!");

                // Clear form after successful save
                clearForm();
                
                // Optionally close the window
                // dispose();

            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "❌ Error saving passenger details: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == bt_close) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to close? Unsaved data will be lost.", 
                "Confirm Close", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
            }
        }
    }

    /**
     * Helper method to clear all form fields
     */
    private void clearForm() {
        tf_username.setText("");
        tf_name.setText("");
        tf_age.setText("");
        tf_dob.setText("");
        tf_addr.setText("");
        tf_phone.setText("");
        tf_email.setText("");
        tf_nat.setText("");
        tf_pass.setText("");
        cb_gender.setSelectedIndex(0); // Reset to "Select Gender"
        tf_username.requestFocus(); // Set focus back to first field
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AddPassengerDetails().setVisible(true));
    }
}

// #############################################################################
// ##                                                                         ##
// ##     HELPER CLASSES (Same as before)                                    ##
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