package Ultil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    public static Connection getConnecttion() {
        Connection cons = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            cons = DriverManager.getConnection("jdbc:sqlserver://localhost:1433;databaseName=Beluda;user=sa;password=****;encrypt=true;trustServerCertificate=true;");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return cons;
    }
}
