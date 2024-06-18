package model;

public class Product {
    private int productID;
    private int categoryID;
    private int brandID;
    private String name;
    private int quantity;
    private String image;
    private double price;
    private String description;

    public Product() {}

    public Product(int productID, int categoryID, int brandID, String name, int quantity, String image, double price, String description) {
        this.productID = productID;
        this.categoryID = categoryID;
        this.brandID = brandID;
        this.name = name;
        this.quantity = quantity;
        this.image = image;
        this.price = price;
        this.description = description;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(int categoryID) {
        this.categoryID = categoryID;
    }

    public int getBrandID() {
        return brandID;
    }

    public void setBrandID(int brandID) {
        this.brandID = brandID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
}
