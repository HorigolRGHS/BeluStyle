/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class Order {
     private int orderId;
    private String userId;
    private Timestamp orderDate;
    private double totalAmount;
    private String orderStatus;
    private String shippingAddress;
    private String paymentMethod;
    private String shippingMethod;
    private String trackingNumber;
    private String notes;
    private String couponCode;
    private double discountAmount;
    private String billingAddress;
    private Date expectedDeliveryDate;
    private String transactionReference;
    private String bankCode;

    public Order() {
    }

    public Order(int orderId, String userId, Timestamp orderDate, double totalAmount, String orderStatus, String shippingAddress, String paymentMethod, String shippingMethod, String trackingNumber, String notes, String couponCode, double discountAmount, String billingAddress, Date expectedDeliveryDate, String transactionReference, String bankCode) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderDate = orderDate;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.shippingAddress = shippingAddress;
        this.paymentMethod = paymentMethod;
        this.shippingMethod = shippingMethod;
        this.trackingNumber = trackingNumber;
        this.notes = notes;
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
        this.billingAddress = billingAddress;
        this.expectedDeliveryDate = expectedDeliveryDate;
        this.transactionReference = transactionReference;
        this.bankCode = bankCode;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Timestamp getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Timestamp orderDate) {
        this.orderDate = orderDate;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(String shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    @Override
    public String toString() {
        return "Order{" + "orderId=" + orderId + ", userId=" + userId + ", orderDate=" + orderDate + ", totalAmount=" + totalAmount + ", orderStatus=" + orderStatus + ", shippingAddress=" + shippingAddress + ", paymentMethod=" + paymentMethod + ", shippingMethod=" + shippingMethod + ", trackingNumber=" + trackingNumber + ", notes=" + notes + ", couponCode=" + couponCode + ", discountAmount=" + discountAmount + ", billingAddress=" + billingAddress + ", expectedDeliveryDate=" + expectedDeliveryDate + ", transactionReference=" + transactionReference + ", bankCode=" + bankCode + '}';
    }
    
    
}
