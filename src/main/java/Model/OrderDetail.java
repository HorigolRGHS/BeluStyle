/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class OrderDetail {
    private int orderDetailId;
    private int orderId;
    private int productId;
    private int orderQuantity;
    private double unitPrice;
    private double discountAmount;
    private double totalPrice;

    public OrderDetail() {
    }

    public OrderDetail(int orderDetailId, int orderId, int productId, int orderQuantity, double unitPrice, double discountAmount, double totalPrice) {
        this.orderDetailId = orderDetailId;
        this.orderId = orderId;
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.unitPrice = unitPrice;
        this.discountAmount = discountAmount;
        this.totalPrice = totalPrice;
    }

    public int getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(int orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "OrderDetail{" + "orderDetailId=" + orderDetailId + ", orderId=" + orderId + ", productId=" + productId + ", orderQuantity=" + orderQuantity + ", unitPrice=" + unitPrice + ", discountAmount=" + discountAmount + ", totalPrice=" + totalPrice + '}';
    }

    
}
