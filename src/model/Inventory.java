package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;



public class Inventory {

    private static int ID = 0;
    private static int prID = 1000;

    /**
     * This generates ID for parts
     * @return returns the ID for part
     */

    public static int generateID() {
        ID++;
        return ID;
    }

    /**
     * This generates ID for products
     * @return returns the ID for product
     */
    public static int generatePrID() {
        prID++;
        return prID;
    }

    /**
     * this declares the List for allParts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();

    /**
     * this declares the List allProducts
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     *
     * @param newPart adds a new part to allParts.
     */

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     *
     * @param newProduct adds a new product to allProducts.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     *
     * @param partID looks up a part by partID
     * @return returns the part
     */
    public static Part lookupPart(int partID){
        for (Part p : allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        return null;
    }

    /**
     *
     * @param productID looks up a product by productID
     * @return returns the product
     */
    public static Product lookupProduct(int productID) {
        for (Product id : allProducts) {
            if(id.getId() == productID) {
                return id;
            }
        }
        return null;
    }

    /**
     *
     * @param partName looks up a part with a String
     * @return returns the part
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchedPart = FXCollections.observableArrayList();
        ObservableList<Part> searchParts = Inventory.getAllParts();

        for(Part p : searchParts) {
            if(p.getName().contains(partName)){
                searchedPart.add(p);
            }
        }
        return searchedPart;
    }

    /**
     *
     * @param productName looks up a product with a String
     * @return returns the product
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchedProduct = FXCollections.observableArrayList();
        ObservableList<Product> searchProducts = Inventory.getAllProducts();

        for(Product pr : searchProducts) {
            if(pr.getName().contains(productName)){
                searchedProduct.add(pr);
            }
        }
        return searchedProduct;
    }

    /**
     *
     * @param index index of part in list
     * @param selectedPart name of part selected to be updated
     */
    public static void updatePart(int index, Part selectedPart) {
            allParts.set(index, selectedPart);

    }

    /**
     *
     * @param index index of product in list
     * @param selectedProduct name of product to be updated
     */
    public static void updateProduct(int index, Product selectedProduct) {
            allProducts.set(index, selectedProduct);

    }

    /**
     *
     * @param selectedPart selected part to delete
     * @return deletes selected part from getAllParts
     */
    public static boolean deletePart(Part selectedPart) {
        return getAllParts().remove(selectedPart);
    }

    /**
     *
     * @param selectedProduct select product to delete
     * @return deletes selected product from getAllProducts
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return getAllProducts().remove(selectedProduct);
    }

    /**
     *
     * @return return allParts list
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     *
     * @return return allProducts list
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }



}
