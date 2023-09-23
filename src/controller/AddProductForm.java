package controller;

import javafx.collections.FXCollections;
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

public class AddProductForm implements Initializable {

    /**
     * These attributes (stage/scene) are declared to be able to use the UI.
     */

    Stage stage;
    Parent scene;

    /**
     * This attribute aids in the adding of a product to Inventory.
     */
    Product product;

    @FXML
    private TableColumn<Part, Integer> addAsInvCol;

    @FXML
    private TableColumn<Part, String> addAsPartCol;

    @FXML
    private TableColumn<Part, Integer> addAsPartIdCol;

    @FXML
    private TableView<Part> addAsPartTableView;

    @FXML
    private TableColumn<Part, Double> addAsPriceCol;

    @FXML
    private TextField addIdTxt;

    @FXML
    private TableColumn<Part, Integer> addInvCol;

    @FXML
    private TextField addInvTxt;

    @FXML
    private TextField addMaxTxt;

    @FXML
    private TextField addMinTxt;

    @FXML
    private TextField addNameTxt;

    @FXML
    private TableColumn<Part, String> addPartCol;

    @FXML
    private TableColumn<Part, Integer> addPartIdCol;

    @FXML
    private TableView<Part> addPartTableView;

    @FXML
    private TableColumn<Part, Double> addPriceCol;

    @FXML
    private TextField addPriceTxt;

    @FXML
    private TextField addsearchPartTxt;

    @FXML
    private Button RemoveAsPartBtn;

    @FXML
    private Button addAsPartBtn;

    @FXML
    private Button addProductBtn;

    @FXML
    private Button canMainBtn;

    @FXML
    private Label ProductErrorMessageLbl;

    /**
     * This method searches the top Parts View Table for specific parts.
     * @param event Parts View Table is searched.
     */

    @FXML
    void onActionSearchParts(ActionEvent event) {
        try {
            int id = Integer.parseInt(addsearchPartTxt.getText());
            Part part = Inventory.lookupPart(id);
            addPartTableView.getSelectionModel().select(part);

        } catch(NumberFormatException e) {
            String q = addsearchPartTxt.getText();
            ObservableList<Part> parts = Inventory.lookupPart(q);
            addPartTableView.setItems(parts);

        }

    }

    /**
     * This method clears the search box when clicked on.
     * @param event The search box is cleared when clicked on.
     */

    @FXML
    void searchPartClicked(MouseEvent event) {
        addsearchPartTxt.setText("");

    }

    /**
     * This method returns screen to Main Form when clicked.
     * @param event Returns to Main Form.
     * @throws IOException
     */

    @FXML
    void onActionMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method adds a new product to Inventory.  The method also check for invalid
     * values in fields and checks if min is less than max and if stock is between min and max.
     * @param event Add product to Inventory.
     * @throws IOException
     */

    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException {

        try {

            product.setName(addNameTxt.getText());
            product.setStock(Integer.parseInt(addInvTxt.getText()));
            product.setPrice(Double.parseDouble(addPriceTxt.getText()));
            product.setMax(Integer.parseInt(addMaxTxt.getText()));
            product.setMin(Integer.parseInt(addMinTxt.getText()));

            if (product.getMin() < product.getMax() && product.getStock() >= product.getMin() && product.getStock() <=
                    product.getMax()) {

                product.setId(Inventory.generatePrID());
                Inventory.addProduct(product);

                Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();

            } else if (product.getMin() > product.getMax()) {
                ProductErrorMessageLbl.setText("Please set Min amount less than Max amount!");

            } else if (product.getStock() < product.getMin() || product.getStock() > product.getMax()) {
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
    void onActionAddAsPart(ActionEvent event) {
        product.addAssociatedPart(addPartTableView.getSelectionModel().getSelectedItem());



    }

    /**
     * This method removes an associated part from product and confirms that user wants to do this by clicking ok on
     * box.
     * @param event Remove associated part from product.
     */

    @FXML
    void onActionRemoveAsPart(ActionEvent event) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Ok to remove Associated Part?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            product.deleteAssociatedPart(addAsPartTableView.getSelectionModel().getSelectedItem());

        }


    }

    /**
     * The method initializes with both view tables pointing to getAllParts and getAllAssociated parts respectively.
     * @param url
     * @param resourceBundle
     */


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        addPartTableView.setItems(Inventory.getAllParts());
        addPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        product = new Product(0, "", 0, 0, 0, 0);

        addAsPartTableView.setItems(product.getAllAssociatedParts());
        addAsPartIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        addAsPartCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        addAsInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addAsPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));



    }
}
