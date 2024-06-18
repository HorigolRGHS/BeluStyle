/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.sql.*;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class DBConnect {

    private static String cnnString = "jdbc:sqlserver://localhost:1433;databaseName=ContactDB;user=sa;password=***;encrypt=true;trustServerCertificate=true;";
    private static Connection conn = null;


    public static Connection getConnection() {
        Connection cons = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cons = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=BeluStyle;user=sa;password=anhyeuemsuzune;encrypt=true;trustServerCertificate=true;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cons;
    }

    public static boolean isConnected() {
        try {
            return conn != null && !conn.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void Connect() {
        try {
            // Load the JDBC driver
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // Establish the connection
            conn = DriverManager.getConnection(cnnString);
            if (conn != null) {
                DatabaseMetaData dm = (DatabaseMetaData) conn.getMetaData();
                System.out.println("Driver name: " + dm.getDriverName());
                System.out.println("Driver version: " + dm.getDriverVersion());
                System.out.println("Product name: " + dm.getDatabaseProductName());
                System.out.println("Product version: " + dm.getDatabaseProductVersion());
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static void Disconnect() {
        try {
            if (isConnected()) {
                conn.close();
//                conn = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static ResultSet ExecuteQuery(String query) {
        ResultSet result = null;
        try {
            if (isConnected()) {
                Statement st = conn.createStatement();
                result = st.executeQuery(query);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Disconnect();
        }
        return result;
    }

    public static int ExecuteUpdate(String query) {
        try {
            if (isConnected()) {
                Statement st = conn.createStatement();
                return st.executeUpdate(query);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            Disconnect();
        }
//        } finally {
//            Disconnect();
//        }
        return 0;
    }

}
