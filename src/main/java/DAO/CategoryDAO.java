package dao;

import Util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

public class CategoryDAO {

    // Add a new Category
    public void addCategory(Category c) {
        Connection con = DBConnect.getConnection();
        String sql = "INSERT INTO Category ([Name]) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve all Categories
    public List<Category> getList() {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM Category";
        List<Category> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String name = rs.getString("Name");
                list.add(new Category(categoryID, name));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Delete a Category by ID
    public void delCategory(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "DELETE FROM Category WHERE CategoryID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Retrieve a single Category by ID
    public Category getCategory(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM Category WHERE CategoryID = ?";
        Category c = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int categoryID = rs.getInt("CategoryID");
                String name = rs.getString("Name");
                c = new Category(categoryID, name);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    // Update a Category
    public void updateCategory(Category c) {
        Connection con = DBConnect.getConnection();
        String sql = "UPDATE Category SET Name = ? WHERE CategoryID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getName());
            ps.setInt(2, c.getCategoryID());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
