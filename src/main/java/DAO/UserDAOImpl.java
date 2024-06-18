package dao;

import Util.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;

public class UserDAOImpl{


    public void addUser(User u) {
        Connection con = DBConnect.getConnection();
        String sql = "INSERT INTO [user] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, u.getUsername());
            ps.setString(2, u.getPassword());
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
            ps.setString(2, password);
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
        String sql = "UPDATE [user] SET password = ?, fullname = ?, dob = ?, sex = ?, email = ?, phoneNumber = ?, address = ?, role = ?, wallet = ? WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getPassword());
            ps.setDate(2, u.getDob());
            ps.setString(3, u.getFullName());
            ps.setString(4, u.getSex());
            ps.setString(5, u.getEmail());
            ps.setString(6, u.getPhoneNumber());
            ps.setString(7, u.getAddress());
            ps.setString(8, u.getRole());
            ps.setDouble(9, u.getWallet());
            ps.setString(10, u.getUsername());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User getUser(String name) {
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

    public static void main(String[] args) {
        UserDAOImpl dao = new UserDAOImpl();
        // dao.addUser(new User(0, "admin", "12345", "admin", "1"));
        // System.out.println(dao.checkUser("admin"));
        System.out.println(dao.login("admin", "12345"));
    }
}
