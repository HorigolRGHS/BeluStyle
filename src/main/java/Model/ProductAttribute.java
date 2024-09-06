/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class ProductAttribute {
    private int productAttributeId;
    private int productId;
    private String productSize;
    private String productColor;
    private int productQuantity;

    public ProductAttribute() {
    }

    public ProductAttribute(int productAttributeId, int productId, String productSize, String productColor, int productQuantity) {
        this.productAttributeId = productAttributeId;
        this.productId = productId;
        this.productSize = productSize;
        this.productColor = productColor;
        this.productQuantity = productQuantity;
    }

    public int getProductAttributeId() {
        return productAttributeId;
    }

    public void setProductAttributeId(int productAttributeId) {
        this.productAttributeId = productAttributeId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductSize() {
        return productSize;
    }

    public void setProductSize(String productSize) {
        this.productSize = productSize;
    }

    public String getProductColor() {
        return productColor;
    }

    public void setProductColor(String productColor) {
        this.productColor = productColor;
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    @Override
    public String toString() {
        return "ProductAttribute{" + "productAttributeId=" + productAttributeId + ", productId=" + productId + ", productSize=" + productSize + ", productColor=" + productColor + ", productQuantity=" + productQuantity + '}';
    }
    
    
    
    
    
}
