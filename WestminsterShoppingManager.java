import java.io.*;
import java.util.*;
import javax.swing.*;

class WestminsterShoppingManager implements ShoppingManager {
    private List<Product> products;

    public WestminsterShoppingManager() {
        this.products = new ArrayList<>();
    }

    // Other methods for managing the product list and system operations

    public void Menu() {

        Scanner scanner = new Scanner(System.in);
        int choose;
        do {
            System.out.println("");
            System.out.println("");
            System.out.println("================================================================");
            System.out.println("=============WELCOME TO WESTMINSTER SHOPPING CART===============");
            System.out.println("================================================================");
            System.out.println("1. Adding a new product to the system.                          ");
            System.out.println("2. Delete a product from the system.                            ");
            System.out.println("3. Printing the list of products that is in the system.         ");
            System.out.println("4. Saving details of the products in a file                     ");
            System.out.println("5. Graphical User InterFace (GUI)                               ");
            System.out.println("6. Exit from the system                                         ");
            System.out.println("Enter your choice :                                             ");

            choose = scanner.nextInt();

            switch (choose) {
                case 1:
                    //ShoppingCartGUI r = new ShoppingCartGUI();
                    ProductAdding();
                    break;
                case 2:
                    ProductDelete();
                    break;
                case 3:
                    ProductPrinting();
                    break;
                case 4:
                    SavingFile();
                    break;

                case 5:
                    // Load ShoppingCartGUI
                    loadShoppingCartGUI();
                    break;
                case 6:
                    System.out.println("Exiting Westminster Online Shopping Manager. Have a nice day come again!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choise option.");
                    break;
            }
        } while (choose != 0);
    }

    @Override
    public void ProductAdding() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Add product:");
        System.out.println("1. Electronics");
        System.out.println("2. Clothing");
        System.out.print("Choose the product type you wish to add (1 or 2): ");

        int productchoose = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String Id, productName;
        int availableItems;
        double price;

        switch (productchoose) {
            case 1:
                // Add Electronics
                System.out.print("Enter the product brand name: ");
                String brand = scanner.nextLine();
                System.out.print("Enter warranty period in months: ");
                int warrantyPeriod = scanner.nextInt();

                while (true) {
                    System.out.print("Enter product ID number: ");
                    Id = scanner.next();
                    if (isProductIdExists(Id)) {
                        System.out.println("Product ID is already exist in the system. Please TRY AGAIN");
                    } else {
                        break;
                    }
                }

                System.out.print("Enter product name: ");
                productName = scanner.next();
                System.out.print("Enter the quantity of products: ");
                availableItems = scanner.nextInt();
                System.out.print("Enter price of the product: ");
                price = scanner.nextDouble();

                // Create and add Electronics to the product list
                Electronics electronics = new Electronics(Id, productName, availableItems, price, brand, warrantyPeriod);
                products.add(electronics);

                System.out.println("Electronic product added successfully!");
                break;

            case 2:
                // Add Clothing
                System.out.print("Enter the size of cloth: ");
                String size = scanner.next();
                System.out.print("Enter the color of cloth: ");
                String color = scanner.next();

                System.out.print("Enter the ID number: ");
                Id = scanner.next();
                System.out.print("Enter product name: ");
                productName = scanner.next();
                System.out.print("Enter the quantity of products: ");
                availableItems = scanner.nextInt();
                System.out.print("Enter price of the product: ");
                price = scanner.nextDouble();

                // Create and add Clothing to the product list
                Clothing clothing = new Clothing(Id, productName, availableItems, price, size, color);
                products.add(clothing);

                System.out.println("Clothing product added successfully!");
                break;

            default:
                System.out.println("Invalid Choice");
                break;
        }
    }

    private boolean isProductIdExists(String productId) {
        for (Product product : products) {
            if (product.getProductIdNumber().equals(productId)) {
                return true; // Product ID already exists
            }
        }
        return false; // Product ID does not exist
    }


    @Override
    public void ProductDelete() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the product Id Number of the product that you want to delete: ");
        String productIdToDelete = scanner.next();

        // Search for the product with the given ID
        Product productToDelete = null;
        for (Product product : products) {
            if (product.getProductIdNumber().equals(productIdToDelete)) {
                productToDelete = product;
                break;
            }
        }

        if (productToDelete != null) {
            // Display information about the product being deleted
            System.out.println("Product deleting in process:");
            System.out.println(productToString(productToDelete));

            // Remove the product from the list
            products.remove(productToDelete);

            System.out.println("Product deleted completely!");
        } else {
            System.out.println("Product ID number '" + productIdToDelete + "' is not found in the system.");
        }
    }


    @Override
    public void ProductPrinting() {
        // Implement logic to print the list of products in alphabetical order based on the product ID
        Collections.sort(products, Comparator.comparing(Product::getProductIdNumber));

        for (Product product : products) {
            System.out.println(productToString(product));
        }
    }

    @Override
    public void SavingFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter("Westminster_shopping.csv"))) {
            // Write header
            writer.println("ProductID Number,ProductName,Number Of Available Items,Product Price,Type of Product,Product Brand,Warranty Period, Cloth Size,Cloth Color");

            // Write each product
            for (Product product : products) {
                if (product instanceof Electronics) {
                    writer.println(product.getProductIdNumber() + ","
                            + product.getProductName() + "," +
                            product.getNumOfAvailableItems() + ","
                            + product.getProductPrice() + ",Electronics," +
                            ((Electronics) product).getElectronicBrand() + ","
                            + ((Electronics) product).getProductsWarrantyPeriod());
                } else if (product instanceof Clothing) {
                    writer.println(product.getProductIdNumber() + ","
                            + product.getProductName() + "," +
                            product.getNumOfAvailableItems() + ","
                            + product.getProductPrice() + ",Clothing," + "," + "," +
                            ((Clothing) product).getClothSize() + ","
                            + ((Clothing) product).getClothColor());
                }
            }

            System.out.println("Product saved completely in csv file!");
        } catch (IOException e) {
            System.err.println("Error detected: " + e.getMessage());
        }
    }


    private String productToString(Product product) {
        if (product instanceof Electronics) {
            return "Electronics: " + product.getProductIdNumber() + " - " + product.getProductName() +
                    " (Product Brand: " + ((Electronics) product).getElectronicBrand() + ", Warranty Period: " + ((Electronics) product).getProductsWarrantyPeriod() + ")";
        } else if (product instanceof Clothing) {
            return "Clothing: " + product.getProductIdNumber() + " - " + product.getProductName() +
                    " (Cloth Size: " + ((Clothing) product).getClothSize() + ", Cloth Color: " + ((Clothing) product).getClothColor() + ")";
        } else {
            return "Unknown Product Type";
        }
    }

    private static void loadShoppingCartGUI() {
        SwingUtilities.invokeLater(() -> {
            ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI();
            shoppingCartGUI.setSize(800, 600);
            shoppingCartGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            shoppingCartGUI.setVisible(true);
        });
    }
}


