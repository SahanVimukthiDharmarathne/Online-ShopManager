import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ShoppingCartGUI extends JFrame {
    private JComboBox<String> product_Type_ComboBox;
    private JTable table_Of_Products;
    private JTextArea details_Of_Product_TextArea;
    private JButton adding_Products_To_Cart_Button;
    private JButton viewing_Shop_Cart_Button;

    private DefaultTableModel TABLE_MODEL;
    private List<Product> list_Of_Products;
    private ShoppingCart SHOPPING_CART;

    private JFrame Shopping_Cart_Frame;




    private void load_Data_From_File() {
        try (Scanner input = new Scanner(new File("Westminster_shopping.csv"))) {
            // Skip the header
            input.nextLine();

            while (input.hasNextLine()) {
                String line = input.nextLine();
                String[] fields = line.split(",");

                // Extract product information and create instances of Electronics or Clothing
                if (fields.length >= 6) {
                    String productIdNumber = fields[0];
                    String productName = fields[1];
                    int numOfAvailableItems = Integer.parseInt(fields[2]);
                    double productPrice = Double.parseDouble(fields[3]);
                    String Type = fields[4];

                    if ("Electronics".equals(Type)) {
                        String productBrand = fields[5];
                        int productsWarrantyPeriod = Integer.parseInt(fields[6]);
                        list_Of_Products.add(new Electronics(productIdNumber, productName, numOfAvailableItems, productPrice, productBrand, productsWarrantyPeriod));
                    } else if ("Clothing".equals(Type)) {
                        String clothSize = fields[5];
                        String clothColor = fields[6];
                        list_Of_Products.add(new Clothing(productIdNumber, productName, numOfAvailableItems, productPrice, clothSize, clothColor));
                    }
                }
            }

            System.out.println("The product list has been loaded from the file successfully.");
        } catch (IOException e) {
            System.err.println("Error detected and loading product list from the file is not complete: " + e.getMessage());
        }
    }

    public ShoppingCartGUI() {
        // Initialize data
        list_Of_Products = new ArrayList<>();
        load_Data_From_File();
        SHOPPING_CART = new ShoppingCart();

        // Create UI components
        product_Type_ComboBox = new JComboBox<>(new String[]{"All", "Electronics", "Clothes"});
        table_Of_Products = new JTable();
        details_Of_Product_TextArea = new JTextArea();
        adding_Products_To_Cart_Button = new JButton("Adding products to Cart");
        viewing_Shop_Cart_Button = new JButton("Shopping Cart");
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(viewing_Shop_Cart_Button);

        // Add the buttons panel to the frame
        add(buttonPanel, BorderLayout.NORTH);


        // Set up table model
        TABLE_MODEL = new DefaultTableModel();
        TABLE_MODEL.addColumn("Product ID Number");
        TABLE_MODEL.addColumn("Product Name");
        TABLE_MODEL.addColumn("Number Of Available Items");
        TABLE_MODEL.addColumn("Product Price");
        TABLE_MODEL.addColumn("Information");
        table_Of_Products.setModel(TABLE_MODEL);

        // Set up layout
        setLayout(new BorderLayout());

        // Create a split pane to separate the table and details panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, new JScrollPane(table_Of_Products), Creating_Information_Panel());

        // Add the split pane to the frame
        add(splitPane, BorderLayout.CENTER);

        JPanel topPanel = new JPanel();
        topPanel.add(new JLabel("Type of Product (Electronics or Clothing): "));
        topPanel.add(product_Type_ComboBox);
        add(topPanel, BorderLayout.NORTH);

        add(viewing_Shop_Cart_Button, BorderLayout.SOUTH);

        // Set up event handlers
        product_Type_ComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Filtering_Products_ByType((String) product_Type_ComboBox.getSelectedItem());
            }
        });

        table_Of_Products.getSelectionModel().addListSelectionListener(e -> {
            List<Product> electronics = new ArrayList<>();
            List<Product> clothing = new ArrayList<>();
            for (Product product : list_Of_Products) {
                if (product instanceof Electronics) {
                    electronics.add(product);
                } else {
                    clothing.add(product);
                }
            }

            if ("All".equals((String) product_Type_ComboBox.getSelectedItem())) {
                int selectedRow = table_Of_Products.getSelectedRow();
                if (selectedRow >= 0) {
                    Displaying_Product_Details(list_Of_Products.get(selectedRow));
                }
            } else if ("Electronics".equals((String) product_Type_ComboBox.getSelectedItem())) {
                int selectedRow = table_Of_Products.getSelectedRow();
                if (selectedRow >= 0) {
                    Displaying_Product_Details(electronics.get(selectedRow));
                }
            } else {
                int selectedRow = table_Of_Products.getSelectedRow();
                if (selectedRow >= 0) {
                    Displaying_Product_Details(clothing.get(selectedRow));
                }
            }
        });

        adding_Products_To_Cart_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Product> electronics = new ArrayList<>();
                List<Product> clothing = new ArrayList<>();
                for (Product product : list_Of_Products) {
                    if (product instanceof Electronics) {
                        electronics.add(product);
                    } else {
                        clothing.add(product);
                    }
                }

                if ("All".equals((String) product_Type_ComboBox.getSelectedItem())) {
                    int selectedRow = table_Of_Products.getSelectedRow();
                    if (selectedRow >= 0) {
                        Adding_Product_To_Cart(list_Of_Products.get(selectedRow));
                    }
                } else if ("Electronics".equals((String) product_Type_ComboBox.getSelectedItem())) {
                    int selectedRow = table_Of_Products.getSelectedRow();
                    if (selectedRow >= 0) {
                        Adding_Product_To_Cart(electronics.get(selectedRow));
                    }
                } else {
                    int selectedRow = table_Of_Products.getSelectedRow();
                    if (selectedRow >= 0) {
                        Adding_Product_To_Cart(clothing.get(selectedRow));
                    }
                }
            }
        });



        viewing_Shop_Cart_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Displaying_Shopping_Cart();
            }
        });
    }

    private JPanel Creating_Information_Panel() {
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.add(new JLabel("Information of Product:"), BorderLayout.NORTH);
        detailsPanel.add(new JScrollPane(details_Of_Product_TextArea), BorderLayout.CENTER);
        detailsPanel.add(adding_Products_To_Cart_Button, BorderLayout.SOUTH);
        return detailsPanel;
    }

    private void Filtering_Products_ByType(String productType) {
        // Clear existing rows in the table model
        TABLE_MODEL.setRowCount(0);

        if ("All".equals(productType)) {
            // Add all products to the table
            for (Product product : list_Of_Products) {
                Add_Product_To_TableModel(product, 1);
            }
        } else {
            // Add only products of the selected type to the table
            for (Product product : list_Of_Products) {
                if (("Electronics".equals(productType) && product instanceof Electronics) ||
                        ("Clothes".equals(productType) && product instanceof Clothing)) {
                    Add_Product_To_TableModel(product, 1);
                }
            }
        }
    }

    private void Add_Product_To_TableModel(Product product, int i) {
        // Add a row to the table model with product information
        Object[] rowData = new Object[]{product.getProductIdNumber(), product.getProductName(),
                product.getNumOfAvailableItems(), product.getProductPrice(), getProductInfo(product)};
        TABLE_MODEL.addRow(rowData);

    }
    private String getProductInfo(Product product) {
        // Return additional information based on the product type
        if (product instanceof Electronics) {
            Electronics electronicsCart = (Electronics) product;
            return "Product Brand: " + electronicsCart.getElectronicBrand() + ", Warranty Period: " + electronicsCart.getProductsWarrantyPeriod() + " Months";
        } else if (product instanceof Clothing) {
            Clothing clothingCart = (Clothing) product;
            return "Cloth Size: " + clothingCart.getClothSize() + ", Cloth Color: " + clothingCart.getClothColor();
        } else {
            return ""; // Empty string for products without additional information
        }
    }



    private void Displaying_Product_Details(Product selectedProduct) {
        if (selectedProduct != null) {
            StringBuilder details = new StringBuilder();
            details.append("Product ID Number: ").append(selectedProduct.getProductIdNumber()).append("\n");
            details.append("Product Name: ").append(selectedProduct.getProductName()).append("\n");
            details.append("Number Of Available Items: ").append(selectedProduct.getNumOfAvailableItems()).append("\n");
            details.append("Product Price: RS.").append(selectedProduct.getProductPrice()).append("\n");

            if (selectedProduct instanceof Electronics) {
                details.append("Product Brand: ").append(((Electronics) selectedProduct).getElectronicBrand()).append("\n");
                details.append("Warranty Period: ").append(((Electronics) selectedProduct).getProductsWarrantyPeriod()).append(" Months\n");
            } else if (selectedProduct instanceof Clothing) {
                details.append("Cloth Size: ").append(((Clothing) selectedProduct).getClothSize()).append("\n");
                details.append("Cloth Color: ").append(((Clothing) selectedProduct).getClothColor()).append("\n");
            }

            details_Of_Product_TextArea.setText(details.toString());
        } else {
            details_Of_Product_TextArea.setText(""); // Clear the text area if no product is selected
        }
    }




    private void Adding_Product_To_Cart(Product selectedProduct) {
        if (selectedProduct != null) {
            // Check if the selected product is available
            if (selectedProduct.getNumOfAvailableItems() > 0) {
                // Decrease the available items and add the product to the shopping cart
                selectedProduct.decreaseAvailableItems();
                SHOPPING_CART.addProductsToSystem(selectedProduct);

                // Add the product to the table model with quantity 1
                // addProductToTableModel(selectedProduct,1);

                // Display a message or perform any other actions as needed
                double finalPrice = SHOPPING_CART.calculateTotalCostOfProducts();
                // Update the UI or display the final price somewhere
            } else {
                JOptionPane.showMessageDialog(this, "That selected product is OUT OF STOCK.", "OUT OF STOCK", JOptionPane.WARNING_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a Product Please.", "Error Detected", JOptionPane.WARNING_MESSAGE);
        }
    }



    private void Displaying_Shopping_Cart() {
        List<Product> cartProducts = SHOPPING_CART.getProducts();
        double finalPrice = SHOPPING_CART.calculateTotalCostOfProducts();
        double totalCost = finalPrice;

        int electroCount = Counting_Electronics_Type_Products();
        int clothCount = Counting_Clothing_Type_Products();

        double discount = 0;
        if (electroCount >= 3 || clothCount >= 3) {
            finalPrice *= 0.8;
            discount = finalPrice * 0.2;
        }

        // Create a DecimalFormat object with a pattern for two decimal places
        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        // Format the total cost, discount, and final cost to a string with two decimal places
        String totalCostString = decimalFormat.format(totalCost);
        String discountString = decimalFormat.format(discount);
        String finalPriceString = decimalFormat.format(finalPrice);

        // If shoppingCartFrame is null or not visible, create a new one
        if (Shopping_Cart_Frame == null || !Shopping_Cart_Frame.isVisible()) {
            Shopping_Cart_Frame = new JFrame("Shopping Cart Informations");
            Shopping_Cart_Frame.setSize(600, 400);
        }

        // Create a table model for the details table
        DefaultTableModel detailsTableModel = new DefaultTableModel();
        detailsTableModel.setColumnIdentifiers(new Object[]{"Product Name", "Product Price", "Quantity of Items", "Total"});
        JTable detailsTable = new JTable(detailsTableModel);

        // Populate the details table with cart products
        for (Product product : cartProducts) {
            detailsTableModel.addRow(new Object[]{product.getProductName(), product.getProductPrice(), 1, product.getProductPrice()});
        }

        // Add the details table to a scroll pane
        JScrollPane detailsScrollPane = new JScrollPane(detailsTable);

        // Create a panel for total cost and discount
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total Cost of Products:");
        JTextField totalField = new JTextField(10);
        JLabel discountLabel = new JLabel("Discount:");
        JTextField discountField = new JTextField(10);

        // Set the values in the text fields
        totalField.setText(totalCostString);
        discountField.setText(discountString);

        // Add components to the total panel
        totalPanel.add(totalLabel);
        totalPanel.add(totalField);
        totalPanel.add(discountLabel);
        totalPanel.add(discountField);

        // Set up layout for the shopping cart frame
        Shopping_Cart_Frame.setLayout(new BorderLayout());
        Shopping_Cart_Frame.add(detailsScrollPane, BorderLayout.CENTER);
        Shopping_Cart_Frame.add(totalPanel, BorderLayout.SOUTH);

        // Make the shopping cart frame visible
        Shopping_Cart_Frame.setVisible(true);
    }
    private int Counting_Electronics_Type_Products() {
        int electroCount = 0;
        for (Product product : SHOPPING_CART.getProducts()) {
            if (product instanceof Electronics) {
                electroCount++;
            }
        }
        return electroCount;
    }

    private int Counting_Clothing_Type_Products() {
        int clothCount = 0;
        for (Product product : SHOPPING_CART.getProducts()) {
            if (product instanceof Clothing) {
                clothCount++;
            }
        }
        return clothCount;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ShoppingCartGUI shoppingCartGUI = new ShoppingCartGUI();
                shoppingCartGUI.setSize(900, 700);
                shoppingCartGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                shoppingCartGUI.setVisible(true);
            }
        });
    }


}

