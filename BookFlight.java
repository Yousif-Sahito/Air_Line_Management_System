package AMS;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
// Imports needed for custom components
import java.awt.geom.RoundRectangle2D;
import java.awt.LinearGradientPaint;

public class BookFlight extends JFrame implements ActionListener, ItemListener {

    // --- Modern Color Palette ---
    private final Color COLOR_PRIMARY = new Color(0, 102, 204);
    private final Color COLOR_SUCCESS = new Color(40, 167, 69);
    private final Color COLOR_DANGER = new Color(220, 53, 69);
    private final Color COLOR_WHITE_TRANSLUCENT = new Color(255, 255, 255, 30);

    // --- Components ---
    ShadowLabel l_title, l_ticket, l_departure, l_arrival, l_class, l_price, l_flightCode, l_flightName, l_journeyTime, l_journeyDate, l_username, l_passengerName;
    PillButton bt_book, bt_back, bt_search_user, bt_date_picker;
    JTextField tf_ticket, tf_flightName, tf_journeyTime, tf_journeyDate, tf_passengerName, tf_price, tf_flightCode, tf_username;
    JComboBox<String> ch_departure, ch_arrival, ch_class;
    JLabel l1;

    private Random randomGenerator = new Random();

    public BookFlight() {
        super("Book Pakistan Airlines");
        setSize(1000, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(null);

        // === Custom Gradient Background Panel ===
        GradientPanel gradientPanel = new GradientPanel();
        gradientPanel.setBounds(0, 0, 1000, 650);
        add(gradientPanel);

        // === Main Content Panel ===
        l1 = new JLabel();
        l1.setBounds(0, 0, 1000, 650);
        l1.setLayout(null);
        l1.setOpaque(false);
        gradientPanel.add(l1);

        // --- Fonts ---
        Font f_title = new Font("Segoe UI", Font.BOLD, 32);
        Font f_label = new Font("Segoe UI", Font.BOLD, 16);
        Font f_field = new Font("Segoe UI", Font.PLAIN, 15);
        Font f_ticket_id = new Font("Segoe UI", Font.BOLD, 18);

        // ==== TITLE SECTION ====
        l_title = new ShadowLabel("Book Pakistan Airlines");
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
        
        // Username with search icon button
        l_username = createLabel("Username:");
        l_username.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_username);
        tf_username = createTextField();
        tf_username.setBounds(col1_x + label_w, y_pos, field_w - 45, field_h);
        tf_username.setEditable(true);
        l1.add(tf_username);
        
        // Search button with icon
        bt_search_user = new PillButton("🔍");
        bt_search_user.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_search_user.setForeground(Color.WHITE);
        bt_search_user.setBaseColor(COLOR_PRIMARY);
        bt_search_user.setBounds(col1_x + label_w + field_w - 40, y_pos, 40, field_h);
        bt_search_user.setToolTipText("Search passenger by username");
        bt_search_user.addActionListener(this);
        l1.add(bt_search_user);
        y_pos += gap;
        
        // Passenger Name
        l_passengerName = createLabel("Passenger Name:");
        l_passengerName.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_passengerName);
        tf_passengerName = createTextField();
        tf_passengerName.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_passengerName.setEditable(false);
        l1.add(tf_passengerName);
        y_pos += gap;
        
        // Ticket ID
        l_ticket = createLabel("Ticket ID:");
        l_ticket.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_ticket);
        tf_ticket = new JTextField();
        tf_ticket.setFont(f_ticket_id);
        tf_ticket.setOpaque(false); 
        tf_ticket.setBackground(COLOR_WHITE_TRANSLUCENT); 
        tf_ticket.setForeground(new Color(255, 255, 150));
        tf_ticket.setCaretColor(Color.WHITE); 
        tf_ticket.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 2, 0, Color.YELLOW),
            BorderFactory.createEmptyBorder(0, 8, 0, 8)
        ));
        tf_ticket.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        tf_ticket.setEditable(false);
        l1.add(tf_ticket);
        y_pos += gap;
        
        // Departure
        l_departure = createLabel("Departure:");
        l_departure.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_departure);
        ch_departure = new JComboBox<>();
        ch_departure.setFont(f_field);
        ch_departure.setBackground(new Color(255, 255, 255, 200));
        ch_departure.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        ch_departure.setRenderer(new CustomComboBoxRenderer());
        l1.add(ch_departure);
        y_pos += gap;
        
        // Arrival
        l_arrival = createLabel("Arrival:");
        l_arrival.setBounds(col1_x, y_pos, label_w, 30);
        l1.add(l_arrival);
        ch_arrival = new JComboBox<>();
        ch_arrival.setFont(f_field);
        ch_arrival.setBackground(new Color(255, 255, 255, 200));
        ch_arrival.setBounds(col1_x + label_w, y_pos, field_w, field_h);
        ch_arrival.setRenderer(new CustomComboBoxRenderer());
        l1.add(ch_arrival);
        
        // --- Column 2 (Right) ---
        y_pos = 100; // Reset Y for second column
        int col2_x = formStartX + 500;
        
        // Class
        l_class = createLabel("Class:");
        l_class.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_class);
        ch_class = new JComboBox<>();
        ch_class.setFont(f_field);
        ch_class.setBackground(new Color(255, 255, 255, 200));
        ch_class.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        ch_class.setRenderer(new CustomComboBoxRenderer());
        l1.add(ch_class);
        y_pos += gap;
        
        // Price
        l_price = createLabel("Price:");
        l_price.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_price);
        tf_price = createTextField();
        tf_price.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_price.setEditable(false);
        l1.add(tf_price);
        y_pos += gap;
        
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
        
        // Journey Time
        l_journeyTime = createLabel("Journey Time:");
        l_journeyTime.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_journeyTime);
        tf_journeyTime = createTextField();
        tf_journeyTime.setBounds(col2_x + label_w, y_pos, field_w, field_h);
        tf_journeyTime.setEditable(false);
        l1.add(tf_journeyTime);
        y_pos += gap;
        
        // Journey Date with calendar icon button
        l_journeyDate = createLabel("Journey Date:");
        l_journeyDate.setBounds(col2_x, y_pos, label_w, 30);
        l1.add(l_journeyDate);
        tf_journeyDate = createTextField();
        tf_journeyDate.setBounds(col2_x + label_w, y_pos, field_w - 45, field_h);
        tf_journeyDate.setEditable(false);
        tf_journeyDate.setBackground(new Color(255, 255, 255, 100));
        l1.add(tf_journeyDate);
        
        // Calendar button with icon
        bt_date_picker = new PillButton("📅");
        bt_date_picker.setFont(new Font("Segoe UI", Font.BOLD, 18));
        bt_date_picker.setForeground(Color.WHITE);
        bt_date_picker.setBaseColor(COLOR_PRIMARY);
        bt_date_picker.setBounds(col2_x + label_w + field_w - 40, y_pos, 40, field_h);
        bt_date_picker.setToolTipText("Select journey date");
        bt_date_picker.addActionListener(this);
        l1.add(bt_date_picker);

        // ==== SEPARATOR LINE ====
        JSeparator separator = new JSeparator();
        separator.setBounds(formStartX + 50, 480, formWidth - 100, 2);
        separator.setForeground(new Color(255, 255, 255, 100));
        separator.setBackground(new Color(255, 255, 255, 100));
        l1.add(separator);

        // ==== BUTTONS PANEL - Centered ====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 500, 1000, 60);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
        buttonPanel.setOpaque(false);
        l1.add(buttonPanel);

        bt_book = new PillButton("Book Flight");
        bt_book.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_book.setForeground(Color.WHITE);
        bt_book.setBaseColor(COLOR_SUCCESS);
        bt_book.setPreferredSize(new Dimension(180, 45));
        bt_book.addActionListener(this);
        buttonPanel.add(bt_book);

        bt_back = new PillButton("Back");
        bt_back.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bt_back.setForeground(Color.WHITE);
        bt_back.setBaseColor(COLOR_DANGER);
        bt_back.setPreferredSize(new Dimension(180, 45));
        bt_back.addActionListener(this);
        buttonPanel.add(bt_back);

        // ===== INITIALIZE DATA =====
        tf_ticket.setText("" + Math.abs(randomGenerator.nextInt() % 100000));
        
        // Load data
        loadDepartures();
        loadArrivals();

        // Add listeners
        ch_departure.addItemListener(this);
        ch_arrival.addItemListener(this);
        ch_class.addItemListener(this);

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

    // === Static Loaders ===
    private void loadDepartures() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT DISTINCT source FROM flight";
            ResultSet rs = obj.stm.executeQuery(q);
            ch_departure.addItem("--- Select Departure ---");
            while (rs.next()) {
                ch_departure.addItem(rs.getString("source"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadArrivals() {
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT DISTINCT destination FROM flight";
            ResultSet rs = obj.stm.executeQuery(q);
            ch_arrival.addItem("--- Select Arrival ---");
            while (rs.next()) {
                ch_arrival.addItem(rs.getString("destination"));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // === Search Passenger by Username ===
    private void searchPassenger() {
        String username = tf_username.getText().trim();
        
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a username to search.", 
                "Search Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "SELECT name FROM passenger WHERE username = ?";
            PreparedStatement pst = obj.con.prepareStatement(q);
            pst.setString(1, username);
            
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                tf_passengerName.setText(rs.getString("name"));
                JOptionPane.showMessageDialog(this, "Passenger found!", 
                    "Search Successful", JOptionPane.INFORMATION_MESSAGE);
            } else {
                tf_passengerName.setText("");
                JOptionPane.showMessageDialog(this, "No passenger found with username: " + username, 
                    "Not Found", JOptionPane.WARNING_MESSAGE);
            }
            
            rs.close();
            pst.close();
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error searching passenger: " + ex.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            tf_passengerName.setText("");
        }
    }

    // === Calendar Date Picker ===
    private void showDatePicker() {
        JDialog dateDialog = new JDialog(this, "Select Journey Date", true);
        dateDialog.setSize(320, 350);
        dateDialog.setLocationRelativeTo(this);
        dateDialog.setLayout(new BorderLayout());
        dateDialog.getContentPane().setBackground(Color.WHITE);

        // Create calendar panel
        JPanel calendarPanel = new JPanel(new BorderLayout());
        calendarPanel.setBackground(Color.WHITE);
        calendarPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Month and year selector
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(COLOR_PRIMARY);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JButton prevYear = new JButton("««");
        JButton prevMonth = new JButton("«");
        JLabel monthYearLabel = new JLabel("", SwingConstants.CENTER);
        monthYearLabel.setForeground(Color.WHITE);
        monthYearLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        JButton nextMonth = new JButton("»");
        JButton nextYear = new JButton("»»");
        
        // Style navigation buttons
        Font navFont = new Font("Segoe UI", Font.BOLD, 12);
        prevYear.setFont(navFont);
        prevMonth.setFont(navFont);
        nextMonth.setFont(navFont);
        nextYear.setFont(navFont);
        
        prevYear.setBackground(Color.WHITE);
        prevMonth.setBackground(Color.WHITE);
        nextMonth.setBackground(Color.WHITE);
        nextYear.setBackground(Color.WHITE);
        
        JPanel monthNavPanel = new JPanel(new FlowLayout());
        monthNavPanel.setBackground(COLOR_PRIMARY);
        monthNavPanel.add(prevYear);
        monthNavPanel.add(prevMonth);
        monthNavPanel.add(monthYearLabel);
        monthNavPanel.add(nextMonth);
        monthNavPanel.add(nextYear);
        
        headerPanel.add(monthNavPanel, BorderLayout.CENTER);

        // Calendar days
        JPanel daysPanel = new JPanel(new GridLayout(0, 7, 2, 2));
        daysPanel.setBackground(Color.WHITE);
        daysPanel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        // Initialize calendar
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        
        // Update month year label
        updateMonthYearLabel(monthYearLabel, calendar);
        
        // Add day headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dayLabel.setForeground(COLOR_PRIMARY);
            dayLabel.setOpaque(true);
            dayLabel.setBackground(new Color(240, 240, 240));
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            daysPanel.add(dayLabel);
        }

        // Add day buttons
        updateCalendarDays(daysPanel, calendar, dateDialog);

        // Navigation listeners
        ActionListener navigationListener = e -> {
            if (e.getSource() == prevMonth) {
                calendar.add(Calendar.MONTH, -1);
            } else if (e.getSource() == nextMonth) {
                calendar.add(Calendar.MONTH, 1);
            } else if (e.getSource() == prevYear) {
                calendar.add(Calendar.YEAR, -1);
            } else if (e.getSource() == nextYear) {
                calendar.add(Calendar.YEAR, 1);
            }
            updateMonthYearLabel(monthYearLabel, calendar);
            updateCalendarDays(daysPanel, calendar, dateDialog);
            dateDialog.revalidate();
            dateDialog.repaint();
        };

        prevMonth.addActionListener(navigationListener);
        nextMonth.addActionListener(navigationListener);
        prevYear.addActionListener(navigationListener);
        nextYear.addActionListener(navigationListener);

        calendarPanel.add(headerPanel, BorderLayout.NORTH);
        calendarPanel.add(daysPanel, BorderLayout.CENTER);

        dateDialog.add(calendarPanel);
        dateDialog.setVisible(true);
    }

    private void updateMonthYearLabel(JLabel label, Calendar calendar) {
        String month = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.ENGLISH);
        int year = calendar.get(Calendar.YEAR);
        label.setText(month + " " + year);
    }

    private void updateCalendarDays(JPanel daysPanel, Calendar calendar, JDialog dialog) {
        daysPanel.removeAll();
        
        // Add day headers
        String[] days = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (String day : days) {
            JLabel dayLabel = new JLabel(day, SwingConstants.CENTER);
            dayLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
            dayLabel.setForeground(COLOR_PRIMARY);
            dayLabel.setOpaque(true);
            dayLabel.setBackground(new Color(240, 240, 240));
            dayLabel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            daysPanel.add(dayLabel);
        }

        Calendar temp = (Calendar) calendar.clone();
        temp.set(Calendar.DAY_OF_MONTH, 1);
        
        int firstDayOfWeek = temp.get(Calendar.DAY_OF_WEEK);
        int daysInMonth = temp.getActualMaximum(Calendar.DAY_OF_MONTH);
        
        // Add empty cells for days before the first day of month
        for (int i = 1; i < firstDayOfWeek; i++) {
            JLabel emptyLabel = new JLabel("");
            emptyLabel.setBackground(Color.WHITE);
            emptyLabel.setOpaque(true);
            daysPanel.add(emptyLabel);
        }
        
        // Add day buttons
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        
        for (int day = 1; day <= daysInMonth; day++) {
            final int selectedDay = day;
            JButton dayButton = new JButton(String.valueOf(day));
            dayButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            dayButton.setBackground(Color.WHITE);
            dayButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
            dayButton.setFocusPainted(false);
            
            // Check if date is in the past
            Calendar selectedDate = (Calendar) calendar.clone();
            selectedDate.set(Calendar.DAY_OF_MONTH, day);
            
            if (selectedDate.before(today)) {
                dayButton.setEnabled(false);
                dayButton.setBackground(new Color(240, 240, 240));
                dayButton.setForeground(Color.GRAY);
            } else {
                dayButton.addActionListener(e -> {
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    String selectedDateStr = String.format("%04d-%02d-%02d", year, month, selectedDay);
                    tf_journeyDate.setText(selectedDateStr);
                    dialog.dispose();
                });
            }
            
            daysPanel.add(dayButton);
        }
        
        // Fill remaining cells to maintain grid layout
        int totalCells = 42; // 6 rows x 7 columns
        int currentCells = firstDayOfWeek - 1 + daysInMonth;
        for (int i = currentCells; i < totalCells; i++) {
            JLabel emptyLabel = new JLabel("");
            emptyLabel.setBackground(Color.WHITE);
            emptyLabel.setOpaque(true);
            daysPanel.add(emptyLabel);
        }
    }

    // === Dynamic Loaders ===
    private void clearFlightDetails() {
        tf_price.setText("");
        tf_flightCode.setText("");
        tf_flightName.setText("");
        tf_journeyTime.setText("");
    }

    private void populateClasses() {
        String departure = (String) ch_departure.getSelectedItem();
        String arrival = (String) ch_arrival.getSelectedItem();

        ch_class.removeAllItems();
        clearFlightDetails(); 
        
        if (departure == null || arrival == null || departure.contains("---") || arrival.contains("---")) return; 

        String q = "SELECT DISTINCT class_name FROM flight WHERE source = ? AND destination = ?";
        
        try (Connection con = new ConnectionClass().con;
             PreparedStatement pstmt = con.prepareStatement(q)) {
            
            pstmt.setString(1, departure);
            pstmt.setString(2, arrival);
            ResultSet rs = pstmt.executeQuery();
            
            ch_class.addItem("--- Select Class ---");
            while (rs.next()) {
                ch_class.addItem(rs.getString("class_name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void populateFlightDetails() {
        String departure = (String) ch_departure.getSelectedItem();
        String arrival = (String) ch_arrival.getSelectedItem();
        String className = (String) ch_class.getSelectedItem();

        clearFlightDetails();
        
        if (className == null || className.contains("---")) return;

        String q = "SELECT f_code, f_name, price FROM flight "
                 + "WHERE source = ? AND destination = ? AND class_name = ?";
        
        try (Connection con = new ConnectionClass().con;
             PreparedStatement pstmt = con.prepareStatement(q)) {
            
            pstmt.setString(1, departure);
            pstmt.setString(2, arrival);
            pstmt.setString(3, className);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                tf_flightCode.setText(rs.getString("f_code"));
                tf_flightName.setText(rs.getString("f_name"));
                tf_price.setText(rs.getString("price"));
                
                // Generate random time
                int hour = randomGenerator.nextInt(12) + 1;
                int minute = randomGenerator.nextInt(60);
                String ampm = randomGenerator.nextBoolean() ? "AM" : "PM";
                String journeyTime = String.format("%02d:%02d %s", hour, minute, ampm);
                tf_journeyTime.setText(journeyTime);
            } else {
                JOptionPane.showMessageDialog(this, "Error: No flight details found for this class.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database Error: " + e.getMessage());
        }
    }

    // === Main Event Listener ===
    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        if (e.getSource() == ch_departure || e.getSource() == ch_arrival) {
            populateClasses();
        } 
        else if (e.getSource() == ch_class) {
            populateFlightDetails();
        } 
    }

    // === Button Actions ===
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bt_back) {
            dispose();
        }
        else if (e.getSource() == bt_search_user) {
            searchPassenger();
        }
        else if (e.getSource() == bt_date_picker) {
            showDatePicker();
        }
        else if (e.getSource() == bt_book) {
            bookFlight();
        }
    }

    private void bookFlight() {
        String ticket = tf_ticket.getText();
        String departure = (String) ch_departure.getSelectedItem();
        String arrival = (String) ch_arrival.getSelectedItem();
        String className = (String) ch_class.getSelectedItem();
        String price = tf_price.getText();
        String flightCode = tf_flightCode.getText();
        String username = tf_username.getText();
        String name = tf_passengerName.getText();
        String journeyTime = tf_journeyTime.getText();
        String journeyDate = tf_journeyDate.getText();
        
        // Enhanced validation
        if (ticket.isEmpty() || departure.contains("---") || arrival.contains("---") || 
            className.contains("---") || price.isEmpty() || flightCode.isEmpty() ||
            username.isEmpty() || name.isEmpty() || journeyTime.isEmpty() || journeyDate.isEmpty()) {
            
            JOptionPane.showMessageDialog(this, "❌ Please fill in all fields and make complete selections!");
            return;
        }

        String q = "INSERT INTO booking (ticket_id, source, destination, class_name, price, "
                 + "flight_code, username, name, j_time, j_date) "
                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (Connection con = new ConnectionClass().con;
             PreparedStatement pstmt = con.prepareStatement(q)) {
            
            pstmt.setString(1, ticket);
            pstmt.setString(2, departure);
            pstmt.setString(3, arrival);
            pstmt.setString(4, className);
            pstmt.setString(5, price);
            pstmt.setString(6, flightCode);
            pstmt.setString(7, username);
            pstmt.setString(8, name);
            pstmt.setString(9, journeyTime);
            pstmt.setString(10, journeyDate);
            
            pstmt.executeUpdate();
            
            JOptionPane.showMessageDialog(this, "✅ Flight booked successfully!\nTicket ID: " + ticket + 
                "\nDate: " + journeyDate + "\nTime: " + journeyTime);
            dispose();

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "❌ Error booking flight: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookFlight().setVisible(true));
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

// #############################################################################
// ##                                                                         ##
// ##     HELPER CLASSES (Same as previous forms)                            ##
// ##                                                                         ##
// #############################################################################

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