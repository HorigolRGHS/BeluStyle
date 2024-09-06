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
public class Discount {
    private int discountId;
    private String discountCode;
    private String discountType;
    private double discountValue;
    private Date startDate;
    private Date endDate;
    private String discountStatus;
    private String discountDescription;
    private double minimumOrderValue;
    private double maximumDiscountValue;
    private int usageLimit;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Discount() {
    }

    public Discount(int discountId, String discountCode, String discountType, double discountValue, Date startDate, Date endDate, String discountStatus, String discountDescription, double minimumOrderValue, double maximumDiscountValue, int usageLimit, Timestamp createdAt, Timestamp updatedAt) {
        this.discountId = discountId;
        this.discountCode = discountCode;
        this.discountType = discountType;
        this.discountValue = discountValue;
        this.startDate = startDate;
        this.endDate = endDate;
        this.discountStatus = discountStatus;
        this.discountDescription = discountDescription;
        this.minimumOrderValue = minimumOrderValue;
        this.maximumDiscountValue = maximumDiscountValue;
        this.usageLimit = usageLimit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public String getDiscountType() {
        return discountType;
    }

    public void setDiscountType(String discountType) {
        this.discountType = discountType;
    }

    public double getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(double discountValue) {
        this.discountValue = discountValue;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getDiscountStatus() {
        return discountStatus;
    }

    public void setDiscountStatus(String discountStatus) {
        this.discountStatus = discountStatus;
    }

    public String getDiscountDescription() {
        return discountDescription;
    }

    public void setDiscountDescription(String discountDescription) {
        this.discountDescription = discountDescription;
    }

    public double getMinimumOrderValue() {
        return minimumOrderValue;
    }

    public void setMinimumOrderValue(double minimumOrderValue) {
        this.minimumOrderValue = minimumOrderValue;
    }

    public double getMaximumDiscountValue() {
        return maximumDiscountValue;
    }

    public void setMaximumDiscountValue(double maximumDiscountValue) {
        this.maximumDiscountValue = maximumDiscountValue;
    }

    public int getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(int usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Discount{" + "discountId=" + discountId + ", discountCode=" + discountCode + ", discountType=" + discountType + ", discountValue=" + discountValue + ", startDate=" + startDate + ", endDate=" + endDate + ", discountStatus=" + discountStatus + ", discountDescription=" + discountDescription + ", minimumOrderValue=" + minimumOrderValue + ", maximumDiscountValue=" + maximumDiscountValue + ", usageLimit=" + usageLimit + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
}
