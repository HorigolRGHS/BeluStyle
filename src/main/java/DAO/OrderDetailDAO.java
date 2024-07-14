package dao;

import Util.DBConnect;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;

public class OrderDetailDAO {

    public void addOrderDetail(OrderDetail orderDetail) {
        String sql = "INSERT INTO OrderDetail (OrderID, ProductID, Quantity, Price) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getOrderID());
            ps.setInt(2, orderDetail.getProductID());
            ps.setInt(3, orderDetail.getQuantity());
            ps.setDouble(4, orderDetail.getPrice());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<OrderDetail> getAllOrderDetails() {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT * FROM OrderDetail";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                int productId = rs.getInt("ProductID");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                orderDetails.add(new OrderDetail(orderId, productId, quantity, price));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }

    public OrderDetail getOrderDetailById(int orderId, int productId) {
        OrderDetail orderDetail = null;
        String sql = "SELECT * FROM OrderDetail WHERE OrderID = ? AND ProductID = ?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int quantity = rs.getInt("Quantity");
                    double price = rs.getDouble("Price");
                    orderDetail = new OrderDetail(orderId, productId, quantity, price);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetail;
    }

    public void updateOrderDetail(OrderDetail orderDetail) {
        String sql = "UPDATE OrderDetail SET Quantity = ?, Price = ? WHERE OrderID = ? AND ProductID = ?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderDetail.getQuantity());
            ps.setDouble(2, orderDetail.getPrice());
            ps.setInt(3, orderDetail.getOrderID());
            ps.setInt(4, orderDetail.getProductID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrderDetail(int orderId, int productId) {
        String sql = "DELETE FROM OrderDetail WHERE OrderID = ? AND ProductID = ?";
        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<OrderDetail> getOrderDetailsByOrderId(int orderId) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        String sql = "SELECT od.OrderID, od.OrderID, od.ProductID, od.Quantity, od.Price, p.Name AS ProductName "
                   + "FROM OrderDetail od "
                   + "JOIN Product p ON od.ProductID = p.ProductID "
                   + "WHERE od.OrderID = ?"; // Filter by the given order ID

        try (Connection con = DBConnect.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ps.setInt(1, orderId); // Set the order ID parameter

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderDetailId = rs.getInt("OrderID"); 
                    int productId = rs.getInt("ProductID");
                    int quantity = rs.getInt("Quantity");
                    double price = rs.getDouble("Price");
                    orderDetails.add(new OrderDetail(orderDetailId, productId, quantity, price));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    
    public double calTotalOrder(int orderId){
        double temp = 0.0;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                PreparedStatement ptmt = DBConnect.prepareStatement("SELECT SUM(od.Quantity * od.Price) AS TotalMoney FROM OrderDetail od GROUP BY od.OrderID HAVING OrderID = ?");
                ptmt.setInt(1, orderId);
                ResultSet rs = ptmt.executeQuery();
                if (rs.next()) {
                    temp = rs.getDouble("TotalMoney");
                }
                return temp;
            } catch (Exception e) {
                e.getStackTrace();
                
            }
        }
        return temp;
    }
    
     public List<OrderDetail> getOrderDetailsListByOrderID(int orderID) {
        List<OrderDetail> orderDetails = new ArrayList<>();
        try (Connection con = DBConnect.getConnection()) {
            String query = "SELECT * FROM OrderDetail WHERE OrderID = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, orderID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrderID(rs.getInt("OrderID"));
                orderDetail.setProductID(rs.getInt("ProductID"));
                orderDetail.setQuantity(rs.getInt("Quantity"));
                orderDetail.setPrice(rs.getDouble("Price"));
                orderDetails.add(orderDetail);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orderDetails;
    }
    
}
