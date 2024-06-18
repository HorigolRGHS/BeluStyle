package dao;

import Model.OrderHistory;
import Util.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDAO {

    public void addOrder(Order order) {
        String sql = "INSERT INTO [Order] (Username, OrderDate) VALUES (?, ?)";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, order.getUsername());
            ps.setDate(2, new Date(order.getOrderDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

      public static int count() {
        DBConnect.Connect();
        int count = 0;
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("select COUNT(OrderID) as count from Order");
                rs.next();
                count = rs.getInt("count");
                DBConnect.Disconnect();
            } catch (Exception e) {
            }
        }
        return count;
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM [Order]";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql);  ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                String username = rs.getString("Username");
                Timestamp orderDate = rs.getTimestamp("OrderDate");
                orders.add(new Order(orderId, username, orderDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public Order getOrderById(int orderId) {
        Order order = null;
        String sql = "SELECT * FROM [Order] WHERE OrderID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String username = rs.getString("Username");
                    Timestamp orderDate = rs.getTimestamp("OrderDate");
                    order = new Order(orderId, username, orderDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    public void updateOrder(Order order) {
        String sql = "UPDATE [Order] SET Username = ?, OrderDate = ? WHERE OrderID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, order.getUsername());
            ps.setDate(2, new Date(order.getOrderDate().getTime()));
            ps.setInt(3, order.getOrderID());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOrder(int orderId) {
        String sql = "DELETE FROM [Order] WHERE OrderID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<OrderHistory> getOrderHistoryByUsername(String username) {
        Connection con = DBConnect.getConnection();
        String sql = "SELECT o.OrderID, o.OrderDate, od.ProductID, od.Quantity, od.Price, p.Name AS ProductName " +
                     "FROM [Order] o " +
                     "JOIN OrderDetail od ON o.OrderID = od.OrderID " +
                     "JOIN Product p ON od.ProductID = p.ProductID " +
                     "WHERE o.Username = ?";
        
        List<OrderHistory> orderHistoryList = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int orderId = rs.getInt("OrderID");
                String orderDate = rs.getString("OrderDate");
                int productId = rs.getInt("ProductID");
                int quantity = rs.getInt("Quantity");
                double price = rs.getDouble("Price");
                String productName = rs.getString("ProductName");

                OrderHistory orderHistory = new OrderHistory(orderId, orderDate, productId, productName, quantity, price);
                orderHistoryList.add(orderHistory);
            }
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderHistoryList;
    }

}
