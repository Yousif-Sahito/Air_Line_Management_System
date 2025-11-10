package AMS;

import java.sql.*;

public class ConnectionClass {
    Connection con;
    Statement stm;

    public ConnectionClass() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to your database
            con = DriverManager.getConnection(
                "jdbc:mysql://127.0.0.1:3306/airlinemanagementsystem",  // ✅ database name must exist
                "root",                                                 // ✅ username
                "yousif08112006"                                       // ✅ your MySQL password
            );

            // ✅ Create a statement object
            stm = con.createStatement();

            System.out.println("✅ Connected successfully to MySQL database!");

        } catch (Exception ex) {
            System.out.println("❌ Database connection failed!");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ConnectionClass();
    }
}
