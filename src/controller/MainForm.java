package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

//Javadoc documentation inclosed in the proviced Javadoc folder.

/**
 * RUNTIME ERROR: When pressing modify without first clicking on a part or product, a runtime exception would come up.
 * Used try/catch to bring up a message in the UI asking the user to select a part or product before clicking modify.
 */

/**
 * FUTURE ENHANCEMENT: Develop a pop up window to be able to remove associated parts on main screen, if the product
 * itself did not need to be modified. This would make it easier for the user to remove associated parts at will.
 */

public class MainForm implements Initializable {

    /**
     * These attributes (stage/scene) are declared to be able to use the UI.
     */
    Stage stage;
    Parent scene;

    @FXML
    private TableColumn<Part, Integer> partIdCol;

    @FXML
    private TableColumn<Part, Integer> partLevCol;

    @FXML
    private TableColumn<Part, String> partNameCol;

    @FXML
    private TableColumn<Part, Double> partPriceCol;

    @FXML
    private TableView<Part> partsTableView;

    @FXML
    private TableColumn<Product, Integer> productIdCol;

    @FXML
    private TableColumn<Product, Integer> productLevCol;

    @FXML
    private TableColumn<Product, String> productNameCol;

    @FXML
    private TableColumn<Product, Double> productPriceCol;

    @FXML
    private TableView<Product> productsTableView;

    @FXML
    private TextField searchPartTxt;

    @FXML
    private TextField searchProductTxt;

    @FXML
    private Label GeneralMessageLbl;

    /**
     * This method deletes a hightlighted part, also calls a confirmation window before deleting.
     * @param event The deletion of a part.
     */

    @FXML
    void onActionDeletePart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ok to Delete?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() ==  ButtonType.OK) {
            Inventory.deletePart(partsTableView.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * This method deletes a hightlighted product, also calls a confirmation window before deleting. If a product has
     * associated parts, the program will reject the deletion of product because of said parts.
     * @param event The deletion of a product
     */

    @FXML
    void onActionDeleteProduct(ActionEvent event) {
        if(productsTableView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ok to Delete?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                Inventory.deleteProduct(productsTableView.getSelectionModel().getSelectedItem());
            }
        }
        else{
            GeneralMessageLbl.setText("Cannot delete - product has associated parts.");
        }
    }

    /**
     * This is where the program will close when exit button is pressed.
     * @param event The exiting of the program.
     */

    @FXML
    void onActionExitApp(ActionEvent event) {
        System.exit(0);

    }

    /**
     * This is where the Add Part form is opened.
     * @param event The opening of the Add Part form.
     * @throws IOException
     */

    @FXML
    void onActionOpenAddPartForm(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * This is where the Add Product form is opened.
     * @param event The opening of the Add Product form.
     * @throws IOException
     */

    @FXML
    void onActionOpenAddProductForm(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @FXML

    /**
     * his method is where the Modify Part form is opened. If no part is highlighted in the Parts Table View, the UI
     * asks the user to highlight a part before going on to the Modify Part form.
     */

    void onActionOpenModifyPartForm(ActionEvent event) throws IOException {

        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartForm.fxml"));
            loader.load();

            ModifyPartForm MPFController = loader.getController();
            MPFController.sendPart(partsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

        catch (NullPointerException e) {
            GeneralMessageLbl.setText("Please select a Part to Modify");

        }

    }

    /**
     * This method is where the Modify Product form is opened. If no part is highlighted in the Parts Table View, the UI
     * asks the user to highlight a part before going on to the Modify Product form.
     * @param event The opening of the Modify Product form.
     * @throws IOException
     */

    @FXML
    void onActionOpenModifyProductForm(ActionEvent event) throws IOException{

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductForm.fxml"));
            loader.load();

            ModifyProductForm MPrFController = loader.getController();
            MPrFController.sendProduct(productsTableView.getSelectionModel().getSelectedItem());

            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();

        }

        catch (NullPointerException e) {
            GeneralMessageLbl.setText("Please select a Product to Modify");
        }

    }

    /**
     * This is where parts are searched in the Parts View Table.
     * @param event Search the parts view table.
     */

    @FXML
    void onActionSearchParts(ActionEvent event) {

        try {
            int id = Integer.parseInt(searchPartTxt.getText());
            Part part = Inventory.lookupPart(id);
            partsTableView.getSelectionModel().select(part);

        } catch(NumberFormatException e) {
            String q = searchPartTxt.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            partsTableView.setItems(parts);

        }
    }

    /**
     * This is where parts are searched in the Products View Table.
     * @param event Search the product view table.
     */

    @FXML
    void onActionSearchProduct(ActionEvent event) {
        try {
            int id = Integer.parseInt(searchProductTxt.getText());
            Product product = Inventory.lookupProduct(id);
            productsTableView.getSelectionModel().select(product);

        } catch(NumberFormatException e) {
            String q = searchProductTxt.getText();
            ObservableList<Product> products = Inventory.lookupProduct(q);
            productsTableView.setItems(products);

        }

    }

    /**
     * Clears the parts search box upon clicking on the search box.
     * @param event Clears the parts search box.
     */

    @FXML
    void partSearchclicked(MouseEvent event) {
        searchPartTxt.setText("");


    }

    /**
     * Clears the products search box upon clicking on the search box.
     * @param event Clears the products search box.
     */

    @FXML
    void productSearchclicked(MouseEvent event) {
        searchProductTxt.setText("");
    }

    /**
     * The method initializes with both view tables pointing to getAllParts and getAllProducts respectively.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partsTableView.setItems(Inventory.getAllParts());
        partIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partLevCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTableView.setItems(Inventory.getAllProducts());
        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productLevCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
