import java.util.ArrayList;
import java.util.List;

// Creating the super class Product which is an abstract class.
abstract class Product {

    // Initializing the instance variables.
    private String productIdNumber;
    private String productName;
    private int numOfAvailableItems;
    private double productPrice;

    // Making the constructor for Product class.

    public Product(String productIdNumber, String productName, int numOfAvailableItems, double productPrice) {
        this.productIdNumber = productIdNumber;
        this.productName = productName;
        this.numOfAvailableItems = numOfAvailableItems;
        this.productPrice = productPrice;
    }

    // Making getters and setters for the Product class and constructing needed void methods.

    public String getProductIdNumber() {
        return productIdNumber;
    }

    public void setProductIdNumber(String productId) {
        this.productIdNumber = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getNumOfAvailableItems() {
        return numOfAvailableItems;
    }

    public void setNumOfAvailableItems(int numOfAvailableItems) {
        this.numOfAvailableItems = numOfAvailableItems;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public void decreaseAvailableItems() {

    }
}