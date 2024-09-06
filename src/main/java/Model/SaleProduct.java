/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Le Khac Huy - CE180311
 */
public class SaleProduct {
     private int saleId;
    private int productId;

    public SaleProduct() {
    }

    public SaleProduct(int saleId, int productId) {
        this.saleId = saleId;
        this.productId = productId;
    }

    public int getSaleId() {
        return saleId;
    }

    public void setSaleId(int saleId) {
        this.saleId = saleId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "SaleProduct{" + "saleId=" + saleId + ", productId=" + productId + '}';
    }
    
    
}
