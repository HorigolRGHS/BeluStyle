package dao;

import Util.DBConnect;
import Util.Encrypt;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.User;

public class UserDAO {

    public void addUser(User u) {
        Connection con = DBConnect.getConnection();
        String sql = "INSERT INTO [user] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getUsername());
            Encrypt en = new Encrypt();
            String hashedPassword = en.hashSHA256(u.getPassword());
            ps.setString(2, hashedPassword);
            ps.setString(3, u.getFullName());
            ps.setDate(4, u.getDob());
            ps.setString(5, u.getSex());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getPhoneNumber());
            ps.setString(8, u.getAddress());
            ps.setString(9, u.getRole());
            ps.setDouble(10, u.getWallet());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(String username) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM [user] WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean login(String username, String password) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM [user] WHERE username = ? AND password = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            Encrypt en = new Encrypt();
            String hashedPassword = en.hashSHA256(password);
            ps.setString(2, hashedPassword);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                con.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void updateUser(User u) {
        Connection con = DBConnect.getConnection();
        String sql = "UPDATE [user] SET  fullname = ?, dob = ?, sex = ?, email = ?, phoneNumber = ?, address = ? WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
//            ps.setString(1, u.getPassword());
            ps.setString(1, u.getFullName());
            ps.setDate(2, u.getDob());
            ps.setString(3, u.getSex());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getPhoneNumber());
            ps.setString(6, u.getAddress());
            ps.setString(7, u.getUsername());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String name) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM [user] WHERE username = ?";
        User u = new User();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {

                String username = rs.getString("Username");
                String password = rs.getString("Password");
                String fullName = rs.getString("FullName");
                Date dob = rs.getDate("DOB");
                String sex = rs.getString("Sex");
                String email = rs.getString("Email");
                String phoneNumber = rs.getString("PhoneNumber");
                String address = rs.getString("Address");
                String role = rs.getString("Role");
                double wallet = rs.getDouble("Wallet");
                u = new User(username, password, fullName, dob, sex, email, phoneNumber, address, role, wallet);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return u;
    }

    public double getWallet(String username) {
        int temp = 0;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("Select Wallet from [User] where Username = '" + username + "'");
                if (rs.next()) {
                    temp = rs.getInt("Wallet");
                }
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println("Error from getWallet: " + e.getMessage());
            }
        }
        return temp;
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
        // dao.addUser(new User(0, "admin", "12345", "admin", "1"));
        // System.out.println(dao.checkUser("admin"));
        System.out.println(dao.login("admin", "12345"));
    }

    public String getUserRole(String username) {
        String role = null;
        // Your JDBC code to query the database and get the user role based on username
        // Example:
        String sql = "SELECT [Role] FROM [User] WHERE Username = ?"; // Assuming you have a 'Role' column
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("Role");
                }
            }
        } catch (SQLException e) {
            // Handle exception (log it)
            e.printStackTrace();
        }
        return role;
    }
    public ArrayList<String> getAllFullnameUser(){
        ArrayList<String> temp = new ArrayList<>();
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("Select FullName from [User] where [Role] != 'Admin'");
                while (rs.next()) {                    
                    temp.add(rs.getString("FullName"));
                }
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return temp;
    }
}
