package AMS;

import java.awt.*;
import javax.swing.*;
import java.sql.*;

public class FlightJourneyDetails extends JFrame {
    
    JTable t;
    String x[] = {
        "Username", "Name", "Age", "Date of Birth","Address",
        "Phone", "Email", "Nationality", "Gender", "Passport"
    };
    String y[][] = new String[50][10];
    Font f;
    int i = 0, j = 0;
    
    FlightJourneyDetails(String src, String dest) {
        super("Flight Journey Details");
        setSize(1300, 400);
        setLocation(0, 10);
        f = new Font("MS UI Gothic", Font.BOLD, 17);
        
        try {
            ConnectionClass obj = new ConnectionClass();
            String q = "Select * from booking where source = '" + src + "' and destination = '" + dest + "'";
            ResultSet rest = obj.stm.executeQuery(q);
            
            while (rest.next()) {
               y[i][j++] = rest.getString("username");
               y[i][j++] = rest.getString("name");
               y[i][j++] = rest.getString("age");
               y[i][j++] = rest.getString("dob");
               y[i][j++] = rest.getString("address");
               y[i][j++] = rest.getString("phone");
               y[i][j++] = rest.getString("email");
               y[i][j++] = rest.getString("nationality");
               y[i][j++] = rest.getString("gender");
               y[i][j++] = rest.getString("passport");

                i++;
                j = 0;
            }
            
            t = new JTable(y, x);
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        t.setFont(f);
        t.setBackground(Color.BLACK);
        t.setForeground(Color.WHITE);
        
        JScrollPane js = new JScrollPane(t);
        add(js);
        
        setVisible(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }
}
