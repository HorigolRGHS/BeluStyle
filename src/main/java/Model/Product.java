/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class Product {
    private int productId;
    private String productName;
    private int categoryId;
    private int brandId;
    private double productPrice;
    private double productSaleprice;
    private String productDescription;
    private int productTotalQuantity;
    private String productImage;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Product() {
    }

    public Product(int productId, String productName, int categoryId, int brandId, double productPrice, double productSaleprice, String productDescription, int productTotalQuantity, String productImage, Timestamp createdAt, Timestamp updatedAt) {
        this.productId = productId;
        this.productName = productName;
        this.categoryId = categoryId;
        this.brandId = brandId;
        this.productPrice = productPrice;
        this.productSaleprice = productSaleprice;
        this.productDescription = productDescription;
        this.productTotalQuantity = productTotalQuantity;
        this.productImage = productImage;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getBrandId() {
        return brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public double getProductSaleprice() {
        return productSaleprice;
    }

    public void setProductSaleprice(double productSaleprice) {
        this.productSaleprice = productSaleprice;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    public int getProductTotalQuantity() {
        return productTotalQuantity;
    }

    public void setProductTotalQuantity(int productTotalQuantity) {
        this.productTotalQuantity = productTotalQuantity;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
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
        return "Product{" + "productId=" + productId + ", productName=" + productName + ", categoryId=" + categoryId + ", brandId=" + brandId + ", productPrice=" + productPrice + ", productSaleprice=" + productSaleprice + ", productDescription=" + productDescription + ", productTotalQuantity=" + productTotalQuantity + ", productImage=" + productImage + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + '}';
    }
    
    
}
