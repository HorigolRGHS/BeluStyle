package model;

import java.sql.Timestamp;

public class Order {
    private int orderID;
    private String username;
    private Timestamp orderDate;

    public Order() {
    }

    public Order(int orderID, String username, Timestamp orderDate) {
        this.orderID = orderID;
        this.username = username;
        this.orderDate = orderDate;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }
 
    
}
