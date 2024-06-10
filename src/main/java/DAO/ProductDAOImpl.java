package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Ultil.DBConnect;
import model.Product;

public class ProductDAOImpl implements ProductDAO {

    @Override
    public void addProduct(Product p) {
        Connection con = DBConnect.getConnecttion();
        String sql = "INSERT INTO product VALUES (?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getMa_san_pham());
            ps.setString(2, p.getTen_san_pham());
            ps.setString(3, p.getHinh_anh());
            ps.setDouble(4, p.getGia_ban());
            ps.setString(5, p.getHang_san_xuat());
            ps.setString(6, p.getThong_tin());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Product> getList() {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM product";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_san_pham = rs.getInt("ma_san_pham");
                int ma_the_loai = rs.getInt("ma_the_loai");
                String ten_san_pham = rs.getString("ten_san_pham");
                String hinh_anh = rs.getString("hinh_anh");
                Double gia_ban = rs.getDouble("gia_ban");
                String hang_san_xuat = rs.getString("hang_san_xuat");
                String thong_tin = rs.getString("thong_tin");
                list.add(new Product(ma_san_pham, ma_the_loai, ten_san_pham, hinh_anh, gia_ban, hang_san_xuat, thong_tin));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<Product> getListByCategory(int id) {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM product WHERE ma_the_loai = ?";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_san_pham = rs.getInt("ma_san_pham");
                int ma_the_loai = rs.getInt("ma_the_loai");
                String ten_san_pham = rs.getString("ten_san_pham");
                String hinh_anh = rs.getString("hinh_anh");
                Double gia_ban = rs.getDouble("gia_ban");
                String hang_san_xuat = rs.getString("hang_san_xuat");
                String thong_tin = rs.getString("thong_tin");
                list.add(new Product(ma_san_pham, ma_the_loai, ten_san_pham, hinh_anh, gia_ban, hang_san_xuat, thong_tin));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product getProduct(int id) {
        Connection con = DBConnect.getConnecttion();
        String sql = "SELECT * FROM product WHERE ma_san_pham = ?";
        Product p = new Product();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int ma_san_pham = rs.getInt("ma_san_pham");
                int ma_the_loai = rs.getInt("ma_the_loai");
                String ten_san_pham = rs.getString("ten_san_pham");
                String hinh_anh = rs.getString("hinh_anh");
                Double gia_ban = rs.getDouble("gia_ban");
                String hang_san_xuat = rs.getString("hang_san_xuat");
                String thong_tin = rs.getString("thong_tin");
                p = new Product(ma_san_pham, ma_the_loai, ten_san_pham, hinh_anh, gia_ban, hang_san_xuat, thong_tin);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Override
    public List<Product> searchList(String ten_san_pham, String ten_the_loai) {
        Connection con = DBConnect.getConnecttion();
        StringBuilder sql = new StringBuilder("SELECT * FROM product, category WHERE product.ma_the_loai = category.ma_the_loai");

        if (!ten_san_pham.isEmpty()) {
            sql.append(" AND ten_san_pham = N'").append(ten_san_pham).append("'");
        }
        if (!ten_the_loai.isEmpty()) {
            sql.append(" AND ten_the_loai = N'").append(ten_the_loai).append("'");
        }

        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int ma_san_pham = rs.getInt("ma_san_pham");
                int ma_the_loai = rs.getInt("ma_the_loai");
                String productName = rs.getString("ten_san_pham");
                String hinh_anh = rs.getString("hinh_anh");
                Double gia_ban = rs.getDouble("gia_ban");
                String hang_san_xuat = rs.getString("hang_san_xuat");
                String thong_tin = rs.getString("thong_tin");
                list.add(new Product(ma_san_pham, ma_the_loai, productName, hinh_anh, gia_ban, hang_san_xuat, thong_tin));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
        Product p = new Product(0, 1, "S6", "da", 500000.0, "", "");
        ProductDAOImpl productDAO = new ProductDAOImpl();
        // productDAO.addProduct(p);
        // System.out.println(productDAO.getList());
        System.out.println(productDAO.getListByCategory(1));
    }
}
