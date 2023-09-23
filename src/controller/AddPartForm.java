package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddPartForm implements Initializable {

    /**
     * These attributes (stage/scene) are declared to be able to use the UI.
     */

    Stage stage;
    Parent scene;

    /* @FXML
    private TextField partIdTxt;
    */

    @FXML
    private RadioButton partInRBtn;

    @FXML
    private TextField partInvTxt;

    @FXML
    private TextField partMacIdTxt;

    @FXML
    private TextField partMaxTxt;

    @FXML
    private TextField partMinTxt;

    @FXML
    private TextField partNameTxt;

    @FXML
    private RadioButton partOutRBtn;

    @FXML
    private TextField partPriceTxt;

    @FXML
    private Label macOrcomLbl;

    @FXML
    private Label PartErrorMessageLbl;

    /**
     * This method adds the part to Inventory depending on which radial button is selected (InHouse or Outsourced).
     * It will also check for exceptions with if there are invalid values in fields, or if min is less than max, or if
     * the stock level is between the min and max.
     * @param event Adds the part to Inventory (InHouse or Outsourced).
     * @throws IOException
     */

    @FXML
    void onActionAddPart(ActionEvent event) throws IOException {

        try {

            if (partInRBtn.isSelected()) {
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                int machineID = Integer.parseInt(partMacIdTxt.getText());

                if (min < max && stock <= max && stock >= min) {
                    Inventory.addPart(new InHouse(Inventory.generateID(), name, price, stock, min, max, machineID));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else if (min > max){
                    PartErrorMessageLbl.setText("Please set Min amount less than Max amount!");
                }
                else if (stock > max || stock < min) {
                    PartErrorMessageLbl.setText("Please set Inv level between Min and Max!");
                }

            } else if (partOutRBtn.isSelected()) {
                String name = partNameTxt.getText();
                int stock = Integer.parseInt(partInvTxt.getText());
                double price = Double.parseDouble(partPriceTxt.getText());
                int max = Integer.parseInt(partMaxTxt.getText());
                int min = Integer.parseInt(partMinTxt.getText());
                String companyName = partMacIdTxt.getText();

                if (min < max && stock <= max && stock >= min) {
                    Inventory.addPart(new Outsourced(Inventory.generateID(), name, price, stock, min, max, companyName));

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }

                else if (min > max){
                    PartErrorMessageLbl.setText("Please set Min amount less than Max amount!");
                }
                else if (stock > max || stock < min) {
                    PartErrorMessageLbl.setText("Please set Inv level between Min and Max!");
                }
            }

        }

        catch (NumberFormatException e) {
            PartErrorMessageLbl.setText("Please enter valid values in text fields!");
        }


    }

    /**
     * Label on bottom field is changed to "Company Name" if the corresponding radial button is pressed.
     * @param event Label changes to "Company Name."
     */

    @FXML
    void OnOutsourced(ActionEvent event) {
        macOrcomLbl.setText("Company Name");

    }

    /**
     * Label on bottom field is changed to "Machine ID" if the corresponding radial button is pressed.
     * @param event Label changes to "Machine ID."
     */

    @FXML
    void onInHouse(ActionEvent event) {
        macOrcomLbl.setText("Machine ID");

    }

    /**
     * When button is press, program returns to the Main Menu form.
     * @param event Button pressed.
     * @throws IOException
     */

    @FXML
    void onActionReturntoMain(ActionEvent event) throws IOException {

        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
