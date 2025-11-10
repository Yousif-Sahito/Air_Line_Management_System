package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
// Imports needed for the new button class
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class FlightJourney extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    // Changed from JButton to PillButton
    PillButton bt1, bt2; 
    Choice ch1, ch2;

    public FlightJourney() {
        super("Select Source & Destination");

        setSize(550, 400); // Increased size for better spacing
        setLocation(400, 200);
        setLayout(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // ==== Professional Gradient Background ====
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 550, 400);
        gradientPanel.setLayout(null);
        add(gradientPanel);

        Font titleFont = new Font("Arial", Font.BOLD, 26);
        Font labelFont = new Font("Arial", Font.BOLD, 20);
        Font choiceFont = new Font("Arial", Font.PLAIN, 16);
        Font buttonFont = new Font("Arial", Font.BOLD, 18);

        // ==== Title Label ====
        JLabel titleLabel = new JLabel("Select Source & Destination", JLabel.CENTER);
        titleLabel.setBounds(50, 40, 450, 40);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(titleFont);
        gradientPanel.add(titleLabel);

        // ==== Labels ====
        l2 = new JLabel("Source:");
        l2.setBounds(50, 110, 150, 35);
        l2.setForeground(Color.WHITE);
        l2.setFont(labelFont);
        gradientPanel.add(l2);

        l3 = new JLabel("Destination:");
        l3.setBounds(50, 180, 150, 35);
        l3.setForeground(Color.WHITE);
        l3.setFont(labelFont);
        gradientPanel.add(l3);

        // ==== Choices ====
        ch1 = new Choice();
        ch1.setBounds(220, 110, 250, 40); // Increased height and width
        ch1.setFont(choiceFont);
        ch1.setBackground(Color.WHITE);
        ch1.setForeground(Color.BLACK);

        ch2 = new Choice();
        ch2.setBounds(220, 180, 250, 40); // Increased height and width
        ch2.setFont(choiceFont);
        ch2.setBackground(Color.WHITE);
        ch2.setForeground(Color.BLACK);

        gradientPanel.add(ch1);
        gradientPanel.add(ch2);

        // ==== Load Data from Database ====
        try {
            ConnectionClass obj = new ConnectionClass();
            String q1 = "SELECT DISTINCT source FROM booking";
            ResultSet res1 = obj.stm.executeQuery(q1);
            while (res1.next()) {
                ch1.add(res1.getString("source"));
            }

            String q2 = "SELECT DISTINCT destination FROM booking";
            ResultSet res2 = obj.stm.executeQuery(q2);
            while (res2.next()) {
                ch2.add(res2.getString("destination"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + ex.getMessage());
        }

        // ==== Buttons ====
        // --- Search Button ---
        bt1 = new PillButton("Search");
        bt1.setFont(buttonFont);
        bt1.setForeground(Color.WHITE);
        bt1.setBaseColor(new Color(0, 123, 255)); // Professional blue
        bt1.setBounds(120, 260, 150, 45);
        bt1.addActionListener(this);
        bt1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gradientPanel.add(bt1);

        // --- Close Button ---
        bt2 = new PillButton("Close");
        bt2.setFont(buttonFont);
        bt2.setForeground(Color.WHITE);
        bt2.setBaseColor(new Color(220, 53, 69)); // Red color
        bt2.setBounds(300, 260, 150, 45);
        bt2.addActionListener(this);
        bt2.setCursor(new Cursor(Cursor.HAND_CURSOR));
        gradientPanel.add(bt2);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt2) {
            setVisible(false);
        } else if (e.getSource() == bt1) {
            String selectedSource = ch1.getSelectedItem();
            String selectedDestination = ch2.getSelectedItem();
            
            if (selectedSource == null || selectedSource.isEmpty() ||
                selectedDestination == null || selectedDestination.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please select both source and destination.", "Selection Error", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            setVisible(false);
            // This assumes FlightJourneyDetails.java exists and has this constructor
            new FlightJourneyDetails(selectedSource, selectedDestination);
        }
    }

    public static void main(String[] args) {
        new FlightJourney().setVisible(true);
    }
}

// #############################################################################
// ##                                                                         ##
// ##                  CUSTOM GRADIENT PANEL                                  ##
// ##                                                                         ##
// #############################################################################

/**
 * A custom JPanel that paints itself with a professional linear gradient background.
 */
class GradientPanel extends JPanel {
    private Color color1 = new Color(41, 128, 185);  // Professional blue
    private Color color2 = new Color(44, 62, 80);    // Professional dark blue

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

        // Create a diagonal linear gradient for modern look
        GradientPaint gp = new GradientPaint(0, 0, color1, w, h, color2);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, w, h);
    }
}

// #############################################################################
// ##                                                                         ##
// ##                  CUSTOM PILLBUTTON CLASS                                ##
// ##                                                                         ##
// #############################################################################

/**
 * A custom JButton class that paints itself as a pill-shaped button
 * with gradients and interaction effects.
 */
class PillButton extends JButton {

    private Color baseColor; // The primary color of the button
    private int borderRadius = 999; // Effectively makes it pill-shaped

    public PillButton(String text) {
        super(text);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);
        setBaseColor(new Color(200, 0, 0)); // Default red
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

        // Calculate arc to make it pill-shaped (half of the height)
        int arc = height;

        // Determine button state colors
        Color startColor = baseColor;
        Color endColor = baseColor.darker().darker(); // Darker shade for gradient
        Color highlightColor = baseColor.brighter().brighter(); // For inner highlight

        if (getModel().isPressed()) {
            // When pressed, make it even darker and shift gradient
            startColor = baseColor.darker().darker();
            endColor = baseColor.darker().darker().darker();
            highlightColor = baseColor.darker();
            g2.translate(0, 1); // Slight downward shift for "pressed" effect
        } else if (getModel().isRollover()) {
            // When hovered, make it slightly brighter
            startColor = baseColor.brighter();
            endColor = baseColor.darker();
            highlightColor = baseColor.brighter().brighter().brighter();
        }

        // 1. Draw the main gradient background
        // Linear gradient from top to bottom
        Paint paint = new LinearGradientPaint(0, 0, 0, height,
                                                new float[]{0f, 1f},
                                                new Color[]{highlightColor, endColor});
        g2.setPaint(paint);
        g2.fill(new RoundRectangle2D.Float(0, 0, width, height, arc, arc));

        // 2. Draw a subtle inner shadow/border (optional, but adds depth)
        g2.setColor(new Color(255, 255, 255, 50)); // Semi-transparent white for highlight
        g2.draw(new RoundRectangle2D.Float(1, 1, width - 2, height - 2, arc, arc));
        g2.setColor(new Color(0, 0, 0, 50)); // Semi-transparent black for shadow
        g2.draw(new RoundRectangle2D.Float(0, 0, width - 1, height - 1, arc, arc));

        // Restore previous translation before drawing text
        if (getModel().isPressed()) {
            g2.translate(0, -1);
        }
        
        // Let the parent class (JButton) paint the text on top
        super.paintComponent(g);

        g2.dispose();
    }
}
