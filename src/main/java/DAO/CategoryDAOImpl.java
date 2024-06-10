package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Ultil.DBConnect;

import model.Category;

public class CategoryDAOImpl implements CategoryDAO {

    @Override
    public void addCategory(Category c) {
        Connection con = DBConnect.getConnecttion();
        String sql = "INSERT INTO category VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, c.getMa_the_loai());
            ps.setString(2, c.getTen_the_loai());
            ps.setString(3, c.getMo_ta());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Category> getList() {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM category";
        List<Category> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_the_loai = rs.getInt("ma_the_loai");
                String ten_the_loai = rs.getString("ten_the_loai");
                String mo_ta = rs.getString("mo_ta");
                list.add(new Category(ma_the_loai, ten_the_loai, mo_ta));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        CategoryDAOImpl dao = new CategoryDAOImpl();
        Category c = new Category(8, "Samsung", "DT");
        // dao.addCategory(c);
        // System.out.println(dao.getList());
        // dao.delCategory(10);
        dao.updateCategory(c);
    }

    @Override
    public void delCategory(int ma_the_loai) {
        Connection con = DBConnect.getConnecttion();
        String sql = "DELETE FROM category WHERE ma_the_loai = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, ma_the_loai);
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategory(int id) {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM category WHERE ma_the_loai = ?";
        Category c = null;
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ma_the_loai = rs.getInt("ma_the_loai");
                String ten_the_loai = rs.getString("ten_the_loai");
                String mo_ta = rs.getString("mo_ta");
                c = new Category(ma_the_loai, ten_the_loai, mo_ta);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    @Override
    public void updateCategory(Category c) {
        Connection con = DBConnect.getConnecttion();
        String sql = "UPDATE category SET ten_the_loai = ?, mo_ta = ? WHERE ma_the_loai = ?";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, c.getTen_the_loai());
            ps.setString(2, c.getMo_ta());
            ps.setInt(3, c.getMa_the_loai());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
