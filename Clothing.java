// Constructing the Clothing class which is a subclass of Product class.
class Clothing extends Product {

    // Initializing the instance variables.
    private String clothSize;
    private String clothColor;

    // Making the constructor for Clothing class.
    public Clothing(String productIdNumber, String productName, int numOfAvailableItems, double productPrice, String clothSize, String clothColor) {
        super(productIdNumber, productName, numOfAvailableItems, productPrice);
        this.clothSize = clothSize;
        this.clothColor = clothColor;
    }

    // Making getters and setters for the Clothing class and also constructing void methods needed.
    public static void clear() {
    }

    public static void add(Product product) {
    }

    // Getters and setters for Clothing-specific attributes
    public void setClothSize(String clothSize) {
        this.clothSize = clothSize;
    }

    public void setClothColor(String clothColor) {
        this.clothColor = clothColor;
    }

    public String getClothSize() {
        return clothSize;
    }

    public String getClothColor() {
        return clothColor;
    }
}