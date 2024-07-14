package model;

public class Cart {

    private Product p;
    private int quantity;
    private String username;
    public Cart() {
    }

    public Cart(Product p, int quantity, String username) {
        this.p = p;
        this.quantity = quantity;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Product getP() {
        return p;
    }

    public void setP(Product p) {
        this.p = p;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
 
    
}
