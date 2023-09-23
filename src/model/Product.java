package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {

    /**
     * the is the list for the associatedParts
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * this is the constructor of the Product
     * @param id this is the id of the Product
     * @param name this is the name of the Product
     * @param price this is the price of the Product
     * @param stock this is the stock level of the Product
     * @param min this is the minimum of the Product
     * @param max this is the maximum of the Product
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     *
     * @param price the price to set
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     *
     * @param stock the stock to set
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     *
     * @return the min
     */
    public int getMin() {
        return min;
    }

    /**
     *
     * @param min the min to set
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     *
     * @return the max
     */
    public int getMax() {
        return max;
    }

    /**
     *
     * @param max the max to set
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     *
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     *
     * @param selectedAssociatedPart to delete
     * @return whether to delete or not
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        return getAllAssociatedParts().remove(selectedAssociatedPart);
    }

    /**
     *
     * @return associatedParts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;

    }

}
