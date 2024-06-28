package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Util.DBConnect;
import model.Brand;
import model.Category;
import model.Product;

public class ProductDAO {

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

    public static ArrayList<Product> getAllProduct() {
        ArrayList<Product> productList = new ArrayList<>();
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try ( PreparedStatement ptmt = DBConnect.prepareStatement("SELECT * FROM Product");  ResultSet rs = ptmt.executeQuery()) {

                while (rs.next()) {
                    int productId = rs.getInt("ProductID");
                    int categoryId = rs.getInt("CategoryID");
                    int brandId = rs.getInt("BrandID");
                    String name = rs.getString("Name");
                    int quantity = rs.getInt("Quantity");
                    String image = rs.getString("Image");
                    double price = rs.getDouble("Price"); // Use BigDecimal for currency
                    String description = rs.getString("Description");

                    Product product = new Product(productId, categoryId, brandId, name, quantity, image, price, description);
                    productList.add(product);
                }

            } catch (SQLException e) {
                System.err.println("SQLException at getAllProduct: " + e.getMessage());
                // Consider logging the error or throwing a custom exception for better handling
            }
        }

        return productList;
    }

    public static ArrayList<Category> getAllCategory() {
        ArrayList<Category> temp = new ArrayList<>();
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("Select * from Category");
                while (rs.next()) {
                    temp.add(new Category(rs.getInt("CategoryID"), rs.getString("Name")));
                }
            } catch (Exception e) {
                System.out.println("EXCEPTION AT getAllCategory:" + e.getMessage());
            }
        }
        return temp;
    }

    public static ArrayList<Brand> getAllBrand() {
        ArrayList<Brand> temp = new ArrayList<>();
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("Select * from Brand");
                while (rs.next()) {
                    temp.add(new Brand(rs.getInt("BrandID"), rs.getString("Name")));
                }
            } catch (Exception e) {
                System.out.println("EXCEPTION AT getAllBrand:" + e.getMessage());
            }
        }
        return temp;
    }

    public static boolean addProduct(int categoryID, int BrandID, String name, String image, float price, int quantity, String description) {
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("Insert into Product(CategoryID, BrandID, [Name], Image, Price, Quantity, [Description]) Values(" + categoryID + "," + BrandID + ",'" + name + "','" + image + "'," + price + "," + price + ",'" + description + "')");
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println("EXCEPTION AT addProduct:" + e.getMessage());
            }
        } else {
            return false;
        }
        return true;
    }

    public static Product getProductById(int productId) {
        Product product = null; // Start with null to indicate no product found
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try ( PreparedStatement ptmt = DBConnect.prepareStatement("SELECT * FROM Product WHERE ProductID = ?");) {

                ptmt.setInt(1, productId); // Set the parameter safely

                try ( ResultSet rs = ptmt.executeQuery()) {
                    if (rs.next()) {
                        int fetchedProductId = rs.getInt("ProductID");
                        int categoryName = rs.getInt("CategoryID");
                        int brandName = rs.getInt("BrandID");
                        String name = rs.getString("Name");
                        String image = rs.getString("Image");
                        double price = rs.getDouble("Price");
                        int quantity = rs.getInt("Quantity");
                        String description = rs.getString("Description");

                        // Assuming your Product constructor takes these parameters in this order
                        product = new Product(fetchedProductId, categoryName, brandName, name, quantity, image, price, description);
                    }
                }

            } catch (SQLException e) {
                System.err.println("SQLException at getProductById: " + e.getMessage());
                // Consider logging the error or throwing a custom exception 
            }
        }
        return product; // Return the product (null if not found)
    }

    public static int getIdCategory(String ProductCategory) {
        int temp = 0;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("SELECT CategoryID FROM Category WHERE Name = '" + ProductCategory + "'");
                while (rs.next()) {
                    temp = rs.getInt("CategoryID");
                }
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println("EXCEPTION AT getIdCategory:" + e.getMessage());
            }
        }
        return temp;
    }

    public static int getIdBrand(String ProductBrand) {
        int temp = 0;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("SELECT BrandID FROM Brand WHERE [Name] = '" + ProductBrand + "'");
                while (rs.next()) {
                    temp = rs.getInt("BrandID");
                }
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println("EXCEPTION AT getIdBrand:" + e.getMessage());
            }
        }
        return temp;
    }

    public static String getCategoryNameById(int categoryId) {
        String categoryName = null; // Initialize to null
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try ( PreparedStatement ptmt = DBConnect.prepareStatement("SELECT Name FROM Category WHERE CategoryID = ?")) {
                ptmt.setInt(1, categoryId);

                try ( ResultSet rs = ptmt.executeQuery()) {
                    if (rs.next()) {
                        categoryName = rs.getString("Name");
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQLException at getCategoryNameById: " + e.getMessage());
            }
        }
        return categoryName; // Return the name (null if not found)
    }

    public static String getBrandNameById(int brandId) {
        String brandName = null;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try ( PreparedStatement ptmt = DBConnect.prepareStatement("SELECT [Name] FROM Brand WHERE BrandID = ?")) {
                ptmt.setInt(1, brandId);

                try ( ResultSet rs = ptmt.executeQuery()) {
                    if (rs.next()) {
                        brandName = rs.getString("Name");
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQLException at getBrandNameById: " + e.getMessage());
            }
        }
        return brandName;
    }

    public static boolean updateProduct(int ProductID, int CategoryID, int BrandID, String name, int quantity, String image, float price, String description) {
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                // Prepare the SQL update statement
                String sql = "UPDATE Product SET "
                        + "CategoryID = ?, "
                        + "BrandID = ?, "
                        + "[Name] = ?, "
                        + "Image = ?, "
                        + "Price = ?, "
                        + "Quantity = ?, "
                        + "[Description] = ? "
                        + "WHERE ProductID = ?";

                // Create a PreparedStatement to prevent SQL injection
                try ( PreparedStatement pstmt = DBConnect.prepareStatement(sql)) {
                    pstmt.setInt(1, CategoryID);
                    pstmt.setInt(2, BrandID);
                    pstmt.setString(3, name);
                    pstmt.setString(4, image);
                    pstmt.setDouble(5, price);
                    pstmt.setInt(6, quantity);
                    pstmt.setString(7, description);
                    pstmt.setInt(8, ProductID);

                    // Execute the update
                    int rowsAffected = pstmt.executeUpdate();

                    // Check if the update was successful
                    return rowsAffected > 0;
                }
            } catch (Exception e) {
                System.out.println("EXCEPTION AT updateProduct:" + e.getMessage());
                return false;
            } finally {
                DBConnect.Disconnect(); // Always disconnect, even if an exception occurs
            }
        } else {
            return false; // Database connection failed
        }
    }

    public static void deleteProduct(int productId) {
        String query = "DELETE FROM Product WHERE productId = ?";
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try ( PreparedStatement ps = DBConnect.prepareStatement(query)) {
                ps.setInt(1, productId);
                ps.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Error at deleteProduct : " + e.getMessage());
            }
        }

    }

    public static String getProductNameById(int productId) {
        String productName = "";
        DBConnect.Connect();

        if (DBConnect.isConnected()) {
            String query = "SELECT Name FROM Product WHERE ProductID = ?";
            try ( PreparedStatement preparedStatement = DBConnect.prepareStatement(query)) {
                preparedStatement.setInt(1, productId);

                try ( ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        productName = resultSet.getString("Name");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm có ProductID: " + productId);
                    }
                }
            } catch (SQLException e) {
                System.err.println("SQLException tại getProductNameById: " + e.getMessage());
                e.printStackTrace(); // In ra stack trace để gỡ lỗi chi tiết
            } finally {
                DBConnect.Disconnect();
            }
        } else {
            System.err.println("Lỗi kết nối cơ sở dữ liệu!");
        }

        return productName; // Trả về tên sản phẩm (null nếu không tìm thấy)
    }
    
   public int getCountProduct(){
       int temp = 0;
       DBConnect.Connect();
       if (DBConnect.isConnected()) {
           try {
               ResultSet rs = DBConnect.ExecuteQuery("select COUNT(ProductID) as ProductNumber from Product");
               if (rs.next()) {
                   temp = rs.getInt("ProductNumber");
               }
               DBConnect.Disconnect();
           } catch (Exception e) {
               System.out.println(e.getMessage());
           }
       }
       return temp;
   }

    public static void main(String[] args) {
//        Product p = new Product(0, 1, 1, "S6", "da", 500000.0, "", "");
//        ProductDAO productDAO = new ProductDAO();
//        // productDAO.addProduct(p);
//        // System.out.println(productDAO.getList());
//        System.out.println(productDAO.getListByCategory(1));
    }

}
