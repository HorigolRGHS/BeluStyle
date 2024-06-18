package dao;

import Util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Brand;

public class BrandDAO {

    public void addBrand(Brand b) {
        Connection con = DBConnect.getConnection();
        String sql = "INSERT INTO Brand (Name) VALUES (?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Brand> getList() {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM Brand";
        List<Brand> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                list.add(new Brand(brandID, name));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void delBrand(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "DELETE FROM Brand WHERE BrandID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Brand getBrand(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM Brand WHERE BrandID = ?";
        Brand b = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                b = new Brand(brandID, name);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return b;
    }

    public void updateBrand(Brand b) {
        Connection con = DBConnect.getConnection();
        String sql = "UPDATE Brand SET Name = ? WHERE BrandID = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, b.getName());
            ps.setInt(2, b.getBrandID());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
