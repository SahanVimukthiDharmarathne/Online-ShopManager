import java.util.ArrayList;
import java.util.List;

// Constructing the ShoppingCart class.
class ShoppingCart {
    private List<Product> listOfProducts; // Creating the array list for the products.

    public ShoppingCart() {
        this.listOfProducts = new ArrayList<>(); // Creating a new array list for the Shopping cart method.
    }

    public void addProductsToSystem(Product product) { // Making the method for adding products to the list.
        listOfProducts.add(product);
    }

    public void removeProductsFromSystem(Product product) { // Making the method for removing products from the list.
        listOfProducts.remove(product);
    }

    public double calculateTotalCostOfProducts() { // Constructing the method to calculate the total cost of products.
        double TotalCost = 0;
        for (Product product : listOfProducts) {
            TotalCost += product.getProductPrice();
        }
        return TotalCost;
    }

    public List<Product> getProducts() {
        return new ArrayList<>(listOfProducts); // Return a copy to prevent external modifications
    }
}
