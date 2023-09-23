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

/** RUNTIME ERROR - received error when trying to populate the Added Associate Part table on the bottom when entering
 * the modify product page. FIX: had to move the actual table declaration inside the "SendProduct" method in order to
 * get the information to present in the table.
 */

public class ModifyProductForm implements Initializable {

    /**
     * These attributes (stage/scene) are declared to be able to use the UI.
     */

    Stage stage;
    Parent scene;

    /**
     * This attribute aids in the adding of a product to Inventory.
     */
    Product modifiedProduct = null;

    @FXML
    private TableColumn<Part, Integer> modAsInvCol;

    @FXML
    private TableColumn<Part, String> modAsPartCol;

    @FXML
    private TableColumn<Part, Integer> modAsPartIdCol;

    @FXML
    private TableView<Part> modAsPartTableView;

    @FXML
    private TableColumn<Part, Integer> modAsPriceCol;

    @FXML
    private TextField modIdTxt;

    @FXML
    private TableColumn<Part, Integer> modInvCol;

    @FXML
    private TextField modInvTxt;

    @FXML
    private TextField modMaxTxt;

    @FXML
    private TextField modMinTxt;

    @FXML
    private TextField modNameTxt;

    @FXML
    private TableColumn<Part, String > modPartCol;

    @FXML
    private TableColumn<Part, Integer> modPartIdCol;

    @FXML
    private TableView<Part> modPartTableView;

    @FXML
    private TableColumn<Part, Integer> modPriceCol;

    @FXML
    private TextField modPriceTxt;

    @FXML
    private TextField modsearchPartTxt;

    @FXML
    private Label ProductErrorMessageLbl;

    /**
     * This method searches the top Parts View Table for specific parts.
     * @param event Parts View Table is searched.
     */

    @FXML
    void onActionModSSearch(ActionEvent event) {
        try {
            int id = Integer.parseInt(modsearchPartTxt.getText());
            Part part = Inventory.lookupPart(id);
            modPartTableView.getSelectionModel().select(part);

        } catch(NumberFormatException e) {
            String q = modsearchPartTxt.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            modPartTableView.setItems(parts);
        }

    }

    /**
     * This method clears the search box when clicked on.
     * @param event The search box is cleared when clicked on.
     */

    @FXML
    void onClickedModSearchPart(MouseEvent event) {
        modsearchPartTxt.setText("");

    }

    /**
     * This method returns screen to Main Form when clicked.
     * @param event Returns to Main Form.
     * @throws IOException
     */

    @FXML
    void onActionModMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method modifies an old part into a new part in Inventory.  The method also check for invalid
     * values in fields and checks if min is less than max and if stock is between min and max.
     * @param event Add product to Inventory.
     * @throws IOException
     */

    @FXML
    void onActionModProduct(ActionEvent event) throws IOException {

        try {

            modifiedProduct.setName(modNameTxt.getText());
            modifiedProduct.setPrice(Double.parseDouble(modPriceTxt.getText()));
            modifiedProduct.setStock(Integer.parseInt(modInvTxt.getText()));
            modifiedProduct.setMin(Integer.parseInt(modMinTxt.getText()));
            modifiedProduct.setMax(Integer.parseInt(modMaxTxt.getText()));

            if (modifiedProduct.getMin() < modifiedProduct.getMax() && modifiedProduct.getStock() >=
                    modifiedProduct.getMin() && modifiedProduct.getStock() <= modifiedProduct.getMax()) {

                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            }

            else if (modifiedProduct.getMin() > modifiedProduct.getMax()) {
                ProductErrorMessageLbl.setText("Please set Min amount less than Max amount!");
            }

            else if (modifiedProduct.getStock() < modifiedProduct.getMin() || modifiedProduct.getStock() >
                             modifiedProduct.getMax()) {
                ProductErrorMessageLbl.setText("Please set Inv level between Min and Max!");
            }
        }

        catch (NumberFormatException e) {
            ProductErrorMessageLbl.setText("Please enter valid values in text fields!");
        }

    }

    /**
     * This methods adds an associated part to the product.
     * @param event Add associated part.
     */

    @FXML
    void onActionModAddAsPart(ActionEvent event) {
        modifiedProduct.addAssociatedPart(modPartTableView.getSelectionModel().getSelectedItem());

    }

    /**
     * This method removes an associated part from product and confirms that user wants to do this by clicking ok on
     * box.
     * @param event Remove associated part from product.
     */

    @FXML
    void onActionModRemoveAsPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ok to remove Associated Part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            modifiedProduct.deleteAssociatedPart(modAsPartTableView.getSelectionModel().getSelectedItem());
        }

    }

    /**
     * This method sends the product from Products Table View on Main Page to Modify Product form.
     * @param product The product sent from Products Table View on Main Page to Modify Product form.
     */

    public void sendProduct(Product product) {
        modifiedProduct = product;
        modIdTxt.setText(String.valueOf(product.getId()));
        modNameTxt.setText(product.getName());
        modInvTxt.setText(String.valueOf(product.getStock()));
        modPriceTxt.setText(String.valueOf(product.getPrice()));
        modMaxTxt.setText(String.valueOf(product.getMax()));
        modMinTxt.setText(String.valueOf(product.getMin()));

        modAsPartTableView.setItems(modifiedProduct.getAllAssociatedParts());
        modAsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modAsPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modAsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modAsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * The method initializes with view table pointing to getAllParts.
     * @param url
     * @param resourceBundle
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        modPartTableView.setItems(Inventory.getAllParts());
        modPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        modPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        modInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

    }
}
