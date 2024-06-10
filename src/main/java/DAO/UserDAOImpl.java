package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Ultil.DBConnect;
import model.User;

public class UserDAOImpl implements UserDAO {

    @Override
    public void addUser(User u) {
        Connection con = DBConnect.getConnecttion();
        String sql = "INSERT INTO [user] VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, u.getUser_id());
            ps.setString(2, u.getUsername());
            ps.setString(3, u.getPassword());
            ps.setDate(4, u.getNgaysinh());
            ps.setString(5, u.getGioitinh());
            ps.setString(6, u.getEmail());
            ps.setString(7, u.getSdt());
            ps.setString(8, u.getDiachi());
            ps.setString(9, u.getRole());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkUser(String username) {
        Connection con = DBConnect.getConnecttion();
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

    @Override
    public boolean login(String username, String password) {
        Connection con = DBConnect.getConnecttion();
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

    @Override
    public void updateUser(User u) {
        Connection con = DBConnect.getConnecttion();
        String sql = "UPDATE [user] SET password = ?, ngaysinh = ?, gioitinh = ?, email = ?, sdt = ?, diachi = ?, role = ? WHERE username = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, u.getPassword());
            ps.setDate(2, u.getNgaysinh());
            ps.setString(3, u.getGioitinh());
            ps.setString(4, u.getEmail());
            ps.setString(5, u.getSdt());
            ps.setString(6, u.getDiachi());
            ps.setString(7, u.getRole());
            ps.setString(8, u.getUsername());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(String name) {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM [user] WHERE username = ?";
        User u = new User();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int user_id = rs.getInt("user_id");
                String username = rs.getString("username");
                String password = rs.getString("password");
                Date ngaysinh = rs.getDate("ngaysinh");
                String gioitinh = rs.getString("gioitinh");
                String email = rs.getString("email");
                String sdt = rs.getString("sdt");
                String diachi = rs.getString("diachi");
                String role = rs.getString("role");
                u = new User(user_id, username, password, ngaysinh, gioitinh, email, sdt, diachi, role);
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
