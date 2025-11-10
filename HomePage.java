package AMS;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HomePage extends JFrame implements ActionListener {

    JPanel headerPanel, sidebarPanel, mainPanel;
    JButton btnAddPassenger, btnViewPassenger, btnUpdatePassenger;
    JButton btnBookFlight, btnViewBooked, btnJourneyDetails;
    JButton btnCancelTicket, btnViewCanceled, btnCheckPayment;
    JButton btnFlightZone, btnLogout;

    // === Base Colors ===
    Color darkSolid = new Color(0, 25, 60);
    Color textWhite = Color.WHITE;
    Color buttonBlue = new Color(0, 102, 204); // Base color for sidebar buttons
    Color buttonHover = new Color(0, 122, 234); // Brighter blue for hover

    public HomePage() {
        super("Pakistan Airlines Management System");

        // === Frame Settings ===
        setSize(1300, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // === Header ===
        headerPanel = new JPanel();
        headerPanel.setBackground(darkSolid);
        headerPanel.setBounds(0, 0, 1300, 60);
        headerPanel.setLayout(null);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(255, 255, 255, 40)));
        add(headerPanel);

        // STYLING CHANGE: Centered title
        JLabel title = new JLabel("Pakistan Airlines Management System");
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(textWhite);
        // Set bounds to span the whole header and center the text
        title.setBounds(0, 15, 1300, 30); 
        title.setHorizontalAlignment(SwingConstants.CENTER); 
        headerPanel.add(title);

        btnLogout = new RoundButton("Logout"); // This is the red logout button
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnLogout.setBackground(new Color(220, 53, 69));
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setBounds(1150, 15, 100, 30);
        btnLogout.addActionListener(this);
        headerPanel.add(btnLogout);

        // === Sidebar ===
        sidebarPanel = new JPanel();
        sidebarPanel.setBackground(darkSolid);
        sidebarPanel.setBounds(0, 60, 250, 690);
        sidebarPanel.setLayout(null);
        sidebarPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(255, 255, 255, 40)));
        add(sidebarPanel);

        int y = 30;

        JLabel passengerLabel = new JLabel("Passenger");
        passengerLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passengerLabel.setForeground(textWhite);
        passengerLabel.setBounds(20, y, 200, 25);
        sidebarPanel.add(passengerLabel);
        y += 35;

        // STYLING CHANGE: Using new createSidebarButton helper
        btnAddPassenger = createSidebarButton("Add Passenger", y);
        sidebarPanel.add(btnAddPassenger);
        y += 40; // 35 height + 5 gap

        btnViewPassenger = createSidebarButton("View Passenger", y);
        sidebarPanel.add(btnViewPassenger);
        y += 40;

        btnUpdatePassenger = createSidebarButton("Update Passenger", y);
        sidebarPanel.add(btnUpdatePassenger);
        y += 50; // Extra gap for next section

        JLabel flightLabel = new JLabel("Flights");
        flightLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        flightLabel.setForeground(textWhite);
        flightLabel.setBounds(20, y, 200, 25);
        sidebarPanel.add(flightLabel);
        y += 35;

        btnBookFlight = createSidebarButton("Book Flight", y);
        sidebarPanel.add(btnBookFlight);
        y += 40;

        btnViewBooked = createSidebarButton("View Booked Flights", y);
        sidebarPanel.add(btnViewBooked);
        y += 40;

        btnJourneyDetails = createSidebarButton("Journey Details", y);
        sidebarPanel.add(btnJourneyDetails);
        y += 40;

        btnFlightZone = createSidebarButton("Flight Zone", y);
        sidebarPanel.add(btnFlightZone);
        y += 50; // Extra gap for next section

        JLabel manageLabel = new JLabel("Management");
        manageLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        manageLabel.setForeground(textWhite);
        manageLabel.setBounds(20, y, 200, 25);
        sidebarPanel.add(manageLabel);
        y += 35;

        btnCancelTicket = createSidebarButton("Cancel Ticket", y);
        sidebarPanel.add(btnCancelTicket);
        y += 40;

        btnViewCanceled = createSidebarButton("View Canceled", y);
        sidebarPanel.add(btnViewCanceled);
        y += 40;

        btnCheckPayment = createSidebarButton("Check Payment", y);
        sidebarPanel.add(btnCheckPayment);

        // === Main Panel with Background Image and Overlay ===
        mainPanel = new JPanel();
        mainPanel.setBounds(250, 60, 1050, 690);
        mainPanel.setLayout(null);

        try {
            ImageIcon img = new ImageIcon(ClassLoader.getSystemResource("AMS/Icons/plane.jpg"));
            Image i1 = img.getImage().getScaledInstance(1050, 690, Image.SCALE_SMOOTH);
            Color darkOverlay = new Color(0, 25, 60, 200); // translucent navy tone

            JPanel backgroundPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.drawImage(i1, 0, 0, getWidth(), getHeight(), this);
                    g2d.setColor(darkOverlay);
                    g2d.fillRect(0, 0, getWidth(), getHeight());
                }
            };
            backgroundPanel.setBounds(0, 0, 1050, 690);
            backgroundPanel.setLayout(null);
            mainPanel.add(backgroundPanel);

            JLabel welcomeLabel = new JLabel("Welcome to Pakistan Airlines Management System", SwingConstants.CENTER);
            welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 34));
            welcomeLabel.setForeground(Color.WHITE);
            welcomeLabel.setBounds(50, 220, 950, 50);
            backgroundPanel.add(welcomeLabel);

            JLabel subtitleLabel = new JLabel("Manage Passengers, Flights and Payments Easily", SwingConstants.CENTER);
            subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 22));
            subtitleLabel.setForeground(new Color(220, 220, 220));
            subtitleLabel.setBounds(50, 280, 950, 40);
            backgroundPanel.add(subtitleLabel);

            JSeparator separator = new JSeparator(SwingConstants.HORIZONTAL);
            separator.setBackground(new Color(255, 255, 255, 180));
            separator.setForeground(new Color(255, 255, 255, 180));
            separator.setBounds(200, 330, 650, 2);
            backgroundPanel.add(separator);

        } catch (Exception e) {
            mainPanel.setBackground(darkSolid);
            System.out.println("Error loading background: " + e.getMessage());
        }

        add(mainPanel);
        setVisible(true);
    }

    // STYLING CHANGE: Helper function for NEW sidebar buttons
    private JButton createSidebarButton(String text, int y) {
        // We now use SidebarButton instead of JButton
        SidebarButton btn = new SidebarButton(text); 
        btn.setBounds(20, y, 210, 35); // A bit wider and taller
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.addActionListener(this);
        return btn;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // NO CHANGES HERE! All your logic is the same.
        Object src = e.getSource();

        if (src == btnAddPassenger) new AddPassengerDetails().setVisible(true);
        else if (src == btnViewPassenger) new ViewPassenger().setVisible(true);
        else if (src == btnUpdatePassenger) new UpdatePassenger().setVisible(true);
        else if (src == btnBookFlight) new BookFlight().setVisible(true);
        else if (src == btnViewBooked) new ViewBookedFlight().setVisible(true);
        else if (src == btnJourneyDetails) new FlightJourney().setVisible(true);
        else if (src == btnFlightZone) new FlightZone().setVisible(true);
        else if (src == btnCancelTicket) new CancelFlight();
        else if (src == btnViewCanceled) new ViewCanceledTicket().setVisible(true);
        else if (src == btnCheckPayment) new CheckPaymentsDetails().setVisible(true);
        else if (src == btnLogout) {
            this.dispose();
            new Login();
        }
    }

    // STYLING CHANGE: This is the new class for our rounded sidebar buttons
    class SidebarButton extends JButton {
        private boolean hovered = false;

        public SidebarButton(String text) {
            super(text);
            setContentAreaFilled(false); // We will paint it ourselves
            setFocusPainted(false);
            setBorderPainted(false);
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    hovered = true;
                    repaint(); // Redraw the button
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    hovered = false;
                    repaint(); // Redraw the button
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Set color based on hover state
            if (hovered) {
                g2.setColor(buttonHover);
            } else {
                g2.setColor(buttonBlue);
            }

            // Draw the rounded rectangle shape
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // 15px corner radius

            // Let the button draw its text (label) on top
            super.paintComponent(g); 
            g2.dispose();
        }
    }

    // Rounded button for Logout (No changes here)
    class RoundButton extends JButton {
        public RoundButton(String text) {
            super(text);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {}
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomePage::new);
    }
}