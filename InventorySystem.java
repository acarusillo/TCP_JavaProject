import java.util.*;
import java.text.SimpleDateFormat;

class Product {
    String productId;
    String productName;
    Date expiryDate;
    int timeDurationForMarkDown;

    Product(String productId, String productName, Date expiryDate, int timeDurationForMarkDown) {
        this.productId = productId;
        this.productName = productName;
        this.expiryDate = expiryDate;
        this.timeDurationForMarkDown = timeDurationForMarkDown;
    }
}

public class InventorySystem {
    private static List<Product> products = new ArrayList<>();

    public static void main(String[] args) {
        
        createProduct("P001", "Apples", null, 6);
        createProduct("P002", "Lawn Mower", null, 10);
        displayProduct(null, null);
        displayProductToRefill(null);
        displayProductCount(null);
        displayProductsExpiryDate(null);
        displayExpiredProducts();
        displayProductsInMarkDown();
        displayProductsForMarkDown();

    }

    // Section A
    public static void createProduct(String productId, String productName, Date expiryDate, int timeDurationForMarkDown) {
        try {
            if (productId == null || productName == null) {
                throw new IllegalArgumentException("ProductID and ProductName are required.");
            }

            if (products.stream().anyMatch(p -> p.productId.equals(productId))) {
                throw new IllegalArgumentException("ProductName should have a unique ID, the ProductName already exists with the same unique ID.");
            }

            if (expiryDate == null) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.MONTH, 3);
                expiryDate = cal.getTime();
            }

            if (timeDurationForMarkDown == 0) {
                timeDurationForMarkDown = 6;
            }

            Product product = new Product(productId, productName, expiryDate, timeDurationForMarkDown);
            products.add(product);

            System.out.println("ProductName with the ProductID created successfully.");
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // Section B
    public static void displayProduct(String productName, String productId) {
        try {
            if (productName == null && productId == null) {
                products.forEach(p -> displayProductDetails(p));
            } else {
                Optional<Product> product = products.stream()
                        .filter(p -> (productName != null && p.productName.equals(productName)) ||
                                (productId != null && p.productId.equals(productId)))
                        .findFirst();

                if (product.isPresent()) {
                    displayProductDetails(product.get());
                } else {
                    System.out.println("Productname/ProductID not found");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void displayProductDetails(Product product) {
        System.out.println("Product ID: " + product.productId);
        System.out.println("Product Name: " + product.productName);
        System.out.println("Expiry Date: " + formatDate(product.expiryDate));
        System.out.println("Markdown Date: " + formatDate(getMarkdownDate(product.expiryDate, product.timeDurationForMarkDown)));
        System.out.println();
    }

    private static String formatDate(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    private static Date getMarkdownDate(Date expiryDate, int timeDurationForMarkDown) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(expiryDate);
        cal.add(Calendar.DAY_OF_MONTH, -timeDurationForMarkDown);
        return cal.getTime();
    }

    // Section A
    public static void displayProductToRefill(String productId) {
        try {
            if (productId == null) {
                products.stream()
                        .filter(p -> isReplenishmentNeeded(p))
                        .forEach(p -> displayReplenishmentDetails(p));
            } else {
                Optional<Product> product = products.stream()
                        .filter(p -> p.productId.equals(productId))
                        .findFirst();

                if (product.isPresent()) {
                    if (isReplenishmentNeeded(product.get())) {
                        displayReplenishmentDetails(product.get());
                    } else {
                        System.out.println("No replenishment needed for ProductID: " + productId);
                    }
                } else {
                    System.out.println("ProductID not found");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static boolean isReplenishmentNeeded(Product product) {
        
        return false;
    }

    private static void displayReplenishmentDetails(Product product) {
        System.out.println("Replenishment needed for ProductID: " + product.productId);
        System.out.println("Product Name: " + product.productName);
    
        System.out.println();
    }

    // Section A
    public static void displayProductCount(String productId) {
        try {
            if (productId == null) {
                products.forEach(p -> displayProductCountDetails(p));
            } else {
                Optional<Product> product = products.stream()
                        .filter(p -> p.productId.equals(productId))
                        .findFirst();

                if (product.isPresent()) {
                    displayProductCountDetails(product.get());
                } else {
                    System.out.println("ProductID not found");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void displayProductCountDetails(Product product) {
        System.out.println("Product ID: " + product.productId);
        System.out.println("Product Name: " + product.productName);
        
        System.out.println();
    }

    // Section B
    public static void displayProductsExpiryDate(String productId) {
        try {
            if (productId == null) {
                products.forEach(p -> displayExpiryDateDetails(p));
            } else {
                Optional<Product> product = products.stream()
                        .filter(p -> p.productId.equals(productId))
                        .findFirst();

                if (product.isPresent()) {
                    displayExpiryDateDetails(product.get());
                } else {
                    System.out.println("ProductID not found");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private static void displayExpiryDateDetails(Product product) {
        System.out.println("Product ID: " + product.productId);
        System.out.println("Product Name: " + product.productName);
        System.out.println("Expiry Date: " + formatDate(product.expiryDate));
        
        System.out.println();
    }

    // Section B
    public static void displayExpiredProducts() {
        try {
            List<Product> expiredProducts = products.stream()
                    .filter(p -> p.expiryDate.before(new Date()))
                    .toList();

            if (!
