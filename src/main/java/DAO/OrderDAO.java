package dao;

import Model.OrderHistory;
import Util.DBConnect;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDAO {

    public void addOrder(Order order) {
        String sql = "INSERT INTO [Order] (OrderID, Username, OrderDate) VALUES (?, ?, ?)";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, order.getOrderID());
            ps.setString(2, order.getUsername());
            ps.setDate(3, new Date(order.getOrderDate().getTime()));
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
        String sql = "SELECT o.OrderID, o.OrderDate, od.ProductID, od.Quantity, od.Price, p.Name AS ProductName "
                + "FROM [Order] o "
                + "JOIN OrderDetail od ON o.OrderID = od.OrderID "
                + "JOIN Product p ON od.ProductID = p.ProductID "
                + "WHERE o.Username = ?";

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

    public String getFullnameOrder(String username) {
        String fullName = null;
        String sql = "SELECT FullName FROM [User] WHERE Username = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, username);

            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    fullName = rs.getString("FullName");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // You might want to throw a custom exception here to signal an error
        }
        return fullName;
    }

    public int getCountOrder() {
        int temp = 0;
        DBConnect.Connect();
        if (DBConnect.isConnected()) {
            try {
                ResultSet rs = DBConnect.ExecuteQuery("select COUNT(OrderID) as OrderNumber from [Order]");
                if (rs.next()) {
                    temp = rs.getInt("OrderNumber");
                }
                DBConnect.Disconnect();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return temp;
    }

    public List<Order> getRecentOrders(int limit) {  // New method
        List<Order> recentOrders = new ArrayList<>();
        String sql = "SELECT Top(?) * FROM [Order] ORDER BY OrderDate DESC"; // Note the ORDER BY and LIMIT

        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, limit);  // Set the limit dynamically
            try ( ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int orderId = rs.getInt("OrderID");
                    String username = rs.getString("Username");
                    Timestamp orderDate = rs.getTimestamp("OrderDate");
                    recentOrders.add(new Order(orderId, username, orderDate));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return recentOrders;
    }

    public String getOrderStatus(int orderId) {
        String status = "pending";
        String sql = "SELECT OrderDate FROM [Order] WHERE OrderID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp orderDate = rs.getTimestamp("OrderDate");
                    LocalDate orderLocalDate = orderDate.toLocalDateTime().toLocalDate();
                    LocalDate currentDate = LocalDate.now();

                    long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(orderLocalDate, currentDate);
                    System.out.println("DayBetween:" + daysBetween);

                    if (daysBetween < -15) {
                        status = "delivered";
                    } else if (daysBetween < -1) {
                        status = "inProgress";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }
    
    public String getOrderStatusText(int orderId) {
        String status = "Pending";
        String sql = "SELECT OrderDate FROM [Order] WHERE OrderID = ?";
        try ( Connection con = DBConnect.getConnection();  PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try ( ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Timestamp orderDate = rs.getTimestamp("OrderDate");
                    LocalDate orderLocalDate = orderDate.toLocalDateTime().toLocalDate();
                    LocalDate currentDate = LocalDate.now();

                    long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(orderLocalDate, currentDate);
                    System.out.println("DayBetween:" + daysBetween);

                    if (daysBetween < -15) {
                        status = "Delivered";
                    } else if (daysBetween < -1) {
                        status = "In progress";
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public List<Order> getOrdersByUsername(String username) {
        List<Order> orders = new ArrayList<>();
        try ( Connection con = DBConnect.getConnection()) {
            String query = "SELECT * FROM [Order] WHERE Username = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setOrderID(rs.getInt("OrderID"));
                order.setUsername(rs.getString("Username"));
                order.setOrderDate(rs.getTimestamp("OrderDate"));
                orders.add(order);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return orders;
    }
}
