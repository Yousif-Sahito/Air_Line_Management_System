package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class SignupClass extends JFrame implements ActionListener {
    
    JTextField tfUsername, tfName, tfEmail, tfPhone, tfSecurity;
    JPasswordField pfPassword, pfConfirmPassword;
    JComboBox<String> securityQuestion;
    PillButton btnSignup, btnBack;
    
    SignupClass() {
        super("Create New Account");
        
        // Set up the frame
        setSize(600, 600); // Increased height to accommodate all fields
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        
        // Create gradient background panel
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 600, 600);
        gradientPanel.setLayout(null);
        add(gradientPanel);
        
        // Title
        ShadowLabel title = new ShadowLabel("Create New Account");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        title.setBounds(150, 20, 300, 30);
        gradientPanel.add(title);
        
        int y = 70;
        
        // Username
        addLabel("Username:", 50, y, gradientPanel);
        tfUsername = createTextField(200, y);
        gradientPanel.add(tfUsername);
        y += 40;
        
        // Name
        addLabel("Full Name:", 50, y, gradientPanel);
        tfName = createTextField(200, y);
        gradientPanel.add(tfName);
        y += 40;
        
        // Email
        addLabel("Email:", 50, y, gradientPanel);
        tfEmail = createTextField(200, y);
        gradientPanel.add(tfEmail);
        y += 40;
        
        // Phone - ADDED PHONE FIELD
        addLabel("Phone:", 50, y, gradientPanel);
        tfPhone = createTextField(200, y);
        gradientPanel.add(tfPhone);
        y += 40;
        
        // Password
        addLabel("Password:", 50, y, gradientPanel);
        pfPassword = createPasswordField(200, y);
        gradientPanel.add(pfPassword);
        y += 40;
        
        // Confirm Password
        addLabel("Confirm Password:", 50, y, gradientPanel);
        pfConfirmPassword = createPasswordField(200, y);
        gradientPanel.add(pfConfirmPassword);
        y += 40;
        
        // Security Question
        addLabel("Security Question:", 50, y, gradientPanel);
        String[] questions = {
            "What is your mother's maiden name?",
            "What was your first pet's name?",
            "What elementary school did you attend?",
            "What city were you born in?"
        };
        securityQuestion = new JComboBox<>(questions);
        securityQuestion.setBounds(200, y, 300, 30);
        securityQuestion.setBackground(new Color(255, 255, 255, 30));
        securityQuestion.setForeground(Color.WHITE);
        securityQuestion.setFont(new Font("Arial", Font.PLAIN, 14));
        gradientPanel.add(securityQuestion);
        y += 40;
        
        // Security Answer
        addLabel("Security Answer:", 50, y, gradientPanel);
        tfSecurity = createTextField(200, y);
        gradientPanel.add(tfSecurity);
        y += 50;
        
        // Buttons
        btnSignup = new PillButton("Sign Up");
        btnSignup.setFont(new Font("Arial", Font.BOLD, 14));
        btnSignup.setForeground(Color.WHITE);
        btnSignup.setBaseColor(new Color(0, 102, 204));
        btnSignup.setBounds(150, y, 120, 35);
        btnSignup.addActionListener(this);
        gradientPanel.add(btnSignup);
        
        btnBack = new PillButton("Back to Login");
        btnBack.setFont(new Font("Arial", Font.BOLD, 14));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBaseColor(new Color(220, 53, 69));
        btnBack.setBounds(300, y, 150, 35);
        btnBack.addActionListener(this);
        gradientPanel.add(btnBack);
        
        setVisible(true);
    }
    
    private void addLabel(String text, int x, int y, JPanel panel) {
        ShadowLabel label = new ShadowLabel(text);
        label.setBounds(x, y, 140, 30);
        label.setForeground(Color.WHITE);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(label);
    }
    
    private JTextField createTextField(int x, int y) {
        JTextField field = new JTextField();
        field.setBounds(x, y, 300, 30);
        field.setOpaque(false);
        field.setBackground(new Color(255, 255, 255, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return field;
    }
    
    private JPasswordField createPasswordField(int x, int y) {
        JPasswordField field = new JPasswordField();
        field.setBounds(x, y, 300, 30);
        field.setOpaque(false);
        field.setBackground(new Color(255, 255, 255, 30));
        field.setForeground(Color.WHITE);
        field.setCaretColor(Color.WHITE);
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.WHITE));
        return field;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnSignup) {
            signupUser();
        } else if (e.getSource() == btnBack) {
            this.dispose();
            new Login().setVisible(true);
        }
    }
    
    private void signupUser() {
        String username = tfUsername.getText().trim();
        String name = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        String phone = tfPhone.getText().trim(); // ADDED PHONE
        String password = String.valueOf(pfPassword.getPassword());
        String confirmPassword = String.valueOf(pfConfirmPassword.getPassword());
        String securityQ = (String) securityQuestion.getSelectedItem();
        String securityA = tfSecurity.getText().trim();
        
        // Validation - INCLUDED PHONE
        if (username.isEmpty() || name.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || securityA.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Email validation
        if (!isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid email address!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Phone validation
        if (!isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Please enter a valid phone number (10-15 digits)!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            JOptionPane.showMessageDialog(this, "Passwords do not match!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (password.length() < 6) {
            JOptionPane.showMessageDialog(this, "Password must be at least 6 characters long!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            ConnectionClass obj = new ConnectionClass();
            
            // Check if username or email already exists
            String checkQuery = "SELECT * FROM signup WHERE username = ? OR email = ? OR phone = ?";
            PreparedStatement checkStmt = obj.con.prepareStatement(checkQuery);
            checkStmt.setString(1, username);
            checkStmt.setString(2, email);
            checkStmt.setString(3, phone);
            ResultSet rs = checkStmt.executeQuery();
            
            if (rs.next()) {
                if (rs.getString("username").equals(username)) {
                    JOptionPane.showMessageDialog(this, "Username already exists! Please choose another one.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (rs.getString("email").equals(email)) {
                    JOptionPane.showMessageDialog(this, "Email already exists! Please use a different email.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Phone number already exists! Please use a different phone number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                return;
            }
            
            // Insert new user - INCLUDING ALL FIELDS
            String insertQuery = "INSERT INTO signup (username, name, email, phone, password, security_question, security_answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = obj.con.prepareStatement(insertQuery);
            pst.setString(1, username);
            pst.setString(2, name);
            pst.setString(3, email);
            pst.setString(4, phone);
            pst.setString(5, password);
            pst.setString(6, securityQ);
            pst.setString(7, securityA);
            
            int rowsAffected = pst.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Account created successfully! You can now login.", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new Login().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create account. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
            
            pst.close();
            checkStmt.close();
            
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Email validation method
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
    
    // Phone validation method
    private boolean isValidPhone(String phone) {
        // Remove any non-digit characters
        String cleanPhone = phone.replaceAll("[^0-9]", "");
        // Check if phone has 10-15 digits
        return cleanPhone.matches("^[0-9]{10,15}$");
    }
    
    public static void main(String[] args) {
        new SignupClass().setVisible(true);
    }
}