package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.DBConnect;
import model.Product;

public class ProductDAOImpl {

    public void addProduct(Product p) {
        Connection con = DBConnect.getConnection();
        String sql = "INSERT INTO Product (CategoryID, BrandID, [Name], [Image], Price, [Description]) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, p.getCategoryID());
            ps.setInt(2, p.getBrandID());
            ps.setString(3, p.getName());
            ps.setInt(4, p.getQuantity());
            ps.setString(5, p.getImage());
            ps.setDouble(6, p.getPrice());
            ps.setString(7, p.getDescription());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void subtractQuantity(Product p, int quantityToSubtract) {
        Connection con = DBConnect.getConnection();
        String sql = "UPDATE Product SET Quantity = ? WHERE ProductID = ?";

        try {
            int newQuantity = p.getQuantity() - quantityToSubtract;
            if (newQuantity < 0) {
                newQuantity = 0;  // Ensure quantity doesn't go below zero
            }

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, newQuantity);
            ps.setInt(2, p.getProductID());
            ps.executeUpdate();
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getList() {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM product";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                int categoryID = rs.getInt("CategoryID");
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                String image = rs.getString("Image");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");
                list.add(new Product(productID, categoryID, brandID, name, quantity, image, price, description));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Product> getListByCategory(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM product WHERE categoryID = ?";
        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                int categoryID = id;
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("Image");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");
                list.add(new Product(productID, categoryID, brandID, name, quantity, image, price, description));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static Product getProductbyId(int id) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT * FROM product WHERE ProductID = ?";
        Product p = new Product();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int productID = id;
                int categoryID = rs.getInt("CategoryID");
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("quantity");
                String image = rs.getString("Image");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");
                p = new Product(productID, categoryID, brandID, name, quantity, image, price, description);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return p;
    }

    public List<Product> searchList(String productName, String categoryName, String brandName) {
        Connection con = DBConnect.getConnection();
        StringBuilder sql = new StringBuilder("SELECT * FROM product, category, brand WHERE product.CategoryID = category.CategoryID AND product.BrandID = brand.BrandID");

        if (!productName.isEmpty()) {
            sql.append(" AND Product.name like N'%").append(productName).append("%'");
        }
        if (!categoryName.isEmpty()) {
            sql.append(" AND category.Name like N'%").append(categoryName).append("%'");
        }
        if (!brandName.isEmpty()) {
            sql.append(" AND brand.Name like N'%").append(brandName).append("%'");
        }

        List<Product> list = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql.toString());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                int categoryID = rs.getInt("CategoryID");
                int brandID = rs.getInt("BrandID");
                String name = rs.getString("Name");
                int quantity = rs.getInt("Quantity");
                String image = rs.getString("Image");
                double price = rs.getDouble("Price");
                String description = rs.getString("Description");
                list.add(new Product(productID, categoryID, brandID, name, quantity, image, price, description));
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        Product p = new Product(0, 1, 1, "S6", "da", 500000.0, "", "");
//        ProductDAOImpl productDAO = new ProductDAOImpl();
//        // productDAO.addProduct(p);
//        // System.out.println(productDAO.getList());
//        System.out.println(productDAO.getListByCategory(1));
    }
}
