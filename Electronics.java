// Constructing the Electronics class which is a subclass of Product class.
class Electronics extends Product {

    // Initializing the instance variables.
    private String electronicBrand;
    private int productsWarrantyPeriod;

    // Making the constructor for Electronics class.
    public Electronics(String productIdNumber, String productName, int numOfAvailableItems, double productPrice, String electronicBrand, int productsWarrantyPeriod) {
        super(productIdNumber, productName, numOfAvailableItems, productPrice);
        this.electronicBrand = electronicBrand;
        this.productsWarrantyPeriod = productsWarrantyPeriod;
    }

    // Making getters and setters for the Electronics class and also constructing void methods needed.
    public static void clear() {

    }

    public static void add(Product product) {
    }

    public void setElectronicBrand(String electronicBrand) {
        this.electronicBrand = electronicBrand;
    }

    public void setProductsWarrantyPeriod(int productWarrantyPeriod) {
        this.productsWarrantyPeriod = productWarrantyPeriod;
    }

    public String getElectronicBrand() {
        return electronicBrand;
    }

    public int getProductsWarrantyPeriod() {
        return productsWarrantyPeriod;
    }
}