package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
// Imports needed for custom components
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class Login extends JFrame implements ActionListener {

    JLabel l1; 
    ShadowLabel l2, l3, l4;
    PillButton bt1, bt2; 
    JPasswordField pf;
    JTextField tf;
    JFrame f;

    Login() {
        f = new JFrame("Login Account");
        f.setBackground(Color.white);
        f.setLayout(null);

        // === Set a custom icon for the window (Optional but looks professional) ===
        java.net.URL iconUrl = ClassLoader.getSystemResource("AMS/Icons/plane_icon.png");
        if (iconUrl != null) {
            f.setIconImage(new ImageIcon(iconUrl).getImage());
        }

        // === Custom Gradient Background Panel ===
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 580, 350); // Match frame size
        f.add(gradientPanel); // Add gradient panel directly to the frame

        // === Main Content Panel (l1) ===
        l1 = new JLabel(); 
        l1.setBounds(0, 0, 580, 350); // Fill the entire frame area
        l1.setLayout(null); // Keep its layout null
        
        l1.setOpaque(false);
        
        gradientPanel.add(l1); // Add l1 to the gradient panel

        // === Title ===
        l3 = new ShadowLabel("Login Account"); 
        l3.setBounds(180, 30, 500, 50);
        l3.setForeground(Color.WHITE); 
        l3.setFont(new Font("Arial", Font.BOLD, 30));
        l1.add(l3);

        // === Username ===
        l2 = new ShadowLabel("Username"); 
        l2.setBounds(120, 120, 150, 30);
        l2.setForeground(Color.WHITE); 
        l2.setFont(new Font("Arial", Font.BOLD, 20));
        l1.add(l2);

        // --- Styled JTextField ---
        tf = new JTextField();
        tf.setBounds(320, 120, 150, 30);
        tf.setOpaque(false); 
        tf.setBackground(new Color(255, 255, 255, 30)); 
        tf.setForeground(Color.WHITE); 
        tf.setCaretColor(Color.WHITE); 
        tf.setFont(new Font("Arial", Font.PLAIN, 16));
        tf.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        l1.add(tf);

        // === Password ===
        l4 = new ShadowLabel("Password"); 
        l4.setBounds(120, 170, 150, 30);
        l4.setForeground(Color.WHITE); 
        l4.setFont(new Font("Arial", Font.BOLD, 20));
        l1.add(l4);

        // --- Styled JPasswordField ---
        pf = new JPasswordField();
        pf.setBounds(320, 170, 150, 30);
        pf.setOpaque(false); 
        pf.setBackground(new Color(255, 255, 255, 30)); 
        pf.setForeground(Color.WHITE); 
        pf.setCaretColor(Color.WHITE); 
        pf.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        l1.add(pf);

        // === Buttons ===
        bt1 = new PillButton("Login");
        bt1.setFont(new Font("Arial", Font.BOLD, 18));
        bt1.setForeground(Color.WHITE);
        bt1.setBaseColor(new Color(0, 102, 204)); 
        bt1.setBounds(120, 230, 150, 40);
        l1.add(bt1);

        bt2 = new PillButton("Sign Up");
        bt2.setFont(new Font("Arial", Font.BOLD, 18));
        bt2.setForeground(Color.WHITE);
        bt2.setBaseColor(new Color(220, 0, 0)); 
        bt2.setBounds(320, 230, 150, 40);
        l1.add(bt2);

        // === Action Listeners ===
        bt1.addActionListener(this);
        bt2.addActionListener(this);

        // === Frame Settings ===
        f.setSize(580, 350);
        f.setLocation(400, 200);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true); 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt1) {
            loginUser();
        } 
        else if (e.getSource() == bt2) {
            f.setVisible(false);
            new SignupClass().setVisible(true); // Now this will open the signup form
        }
    }

    private void loginUser() {
        String username = tf.getText().trim();
        String pass = String.valueOf(pf.getPassword());

        // Basic validation
        if (username.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please enter both username and password!");
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT * FROM signup WHERE username = ? AND password = ?";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, username);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                JOptionPane.showMessageDialog(null, "Login Successful!");
                f.setVisible(false);
                new HomePage().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(null, "Wrong Username or Password!");
            }
            
            rs.close();
            pst.close();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database Error: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Login();
            }
        });
    }
}

// #############################################################################
// ##                                                                         ##
// ##             CUSTOM HELPER CLASSES                                       ##
// ##                                                                         ##
// #############################################################################

/**
 * A custom JPanel that paints itself with a linear gradient background.
 */
class GradientPanel extends JPanel {
    private Color color1 = new Color(40, 60, 100);  // Dark Blue/Grey
    private Color color2 = new Color(10, 20, 40);   // Even Darker Blue/Grey

    public GradientPanel() {
        setLayout(null); // Keep layout null to allow absolute positioning of children
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        int w = getWidth();
        int h = getHeight();

        // Create a linear gradient from top (color1) to bottom (color2)
        GradientPaint gp = new GradientPaint(0, 0, color1, 0, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

/**
 * A custom JLabel that paints its text with a subtle drop shadow for readability
 * on complex backgrounds.
 */
class ShadowLabel extends JLabel {
    
    private Color shadowColor = new Color(0, 0, 0, 150); // Semi-transparent black
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
 * with gradients and interaction effects.
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
        
        // --- THIS IS THE FIX ---
        // Constants are from RenderingHints, not Graphics2D
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // -----------------------

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