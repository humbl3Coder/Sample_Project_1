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
import model.Part;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ModifyPartForm implements Initializable {

    /**
     * These attributes (stage/scene) are declared to be able to use the UI.
     */

    Stage stage;
    Parent scene;

    /**
     * This attribute aids in the adding of a product to Inventory.
     */

    Part modifiedPart = null;

    @FXML
    private TextField modIdTxt;

    @FXML
    private RadioButton modInRBtn;

    @FXML
    private TextField modInvTxt;

    @FXML
    private TextField modMacIdTxt;

    @FXML
    private TextField modMaxTxt;

    @FXML
    private TextField modMinTxt;

    @FXML
    private TextField modNameTxt;

    @FXML
    private RadioButton modOutRBtn;

    @FXML
    private TextField modPriceTxt;

    @FXML
    private Label modInorOutLbl;

    @FXML
    private Label ModPartErrorMessageLbl;

    /**
     * Label on bottom field is changed to "Machine ID" if the corresponding radial button is pressed.
     * @param event Label changes to "Machine ID."
     */

    @FXML
    void OnInHouse(ActionEvent event) {
        modInorOutLbl.setText("Machine ID");

    }

    /**
     * Label on bottom field is changed to "Company Name" if the corresponding radial button is pressed.
     * @param event Label changes to "Company Name."
     */

    @FXML
    void OnOutsourced(ActionEvent event) {
        modInorOutLbl.setText("Company Name");
    }

    /**
     * When button is press, program returns to the Main Menu form.
     * @param event Button pressed.
     * @throws IOException
     */

    @FXML
    void onActionCancelButton(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();

    }

    /**
     * This method modifies the part in Inventory depending on which radial button is selected (InHouse or Outsourced).
     * It will also check for exceptions with if there are invalid values in fields, or if min is less than max, or if
     * the stock level is between the min and max.
     * @param event Modifies the part in Inventory (InHouse or Outsourced).
     * @throws IOException
     */

    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException {

        try {

            if (modInRBtn.isSelected()) {
                int id = modifiedPart.getId();
                String name = modNameTxt.getText();
                int stock = Integer.parseInt(modInvTxt.getText());
                double price = Double.parseDouble(modPriceTxt.getText());
                int max = Integer.parseInt(modMaxTxt.getText());
                int min = Integer.parseInt(modMinTxt.getText());
                int machineID = Integer.parseInt(modMacIdTxt.getText());

                if (min < max && stock <= max && stock >= min) {

                    Inventory.addPart(new InHouse(id, name, price, stock, min, max, machineID));
                    Inventory.deletePart(modifiedPart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else if (min > max){
                    ModPartErrorMessageLbl.setText("Please set Min amount less than Max amount!");
                }
                else if (stock > max || stock < min) {
                    ModPartErrorMessageLbl.setText("Please set Inv level between Min and Max!");
                }

            } else if (modOutRBtn.isSelected()) {
                int id = modifiedPart.getId();
                String name = modNameTxt.getText();
                int stock = Integer.parseInt(modInvTxt.getText());
                double price = Double.parseDouble(modPriceTxt.getText());
                int max = Integer.parseInt(modMaxTxt.getText());
                int min = Integer.parseInt(modMinTxt.getText());
                String companyName = modMacIdTxt.getText();

                if (min < max && stock <= max && stock >= min) {

                    Inventory.addPart(new Outsourced(id, name, price, stock, min, max, companyName));
                    Inventory.deletePart(modifiedPart);

                    stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
                    scene = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
                    stage.setScene(new Scene(scene));
                    stage.show();
                }
                else if (min > max){
                    ModPartErrorMessageLbl.setText("Please set Min amount less than Max amount!");
                }
                else if (stock > max || stock < min) {
                    ModPartErrorMessageLbl.setText("Please set Inv level between Min and Max!");
                }
            }

        }

        catch (NumberFormatException e) {
            ModPartErrorMessageLbl.setText("Please enter valid values in text fields!");
        }

    }

    /**
     * This method sends the part from Parts Table View on Main Page to Modify Part form.
     * @param part The part sent from Parts Table View on Main Page to Modify Part form.
     */

    public void sendPart(Part part) {
        modifiedPart = part;
        modIdTxt.setText(String.valueOf(part.getId()));
        modNameTxt.setText(part.getName());
        modInvTxt.setText(String.valueOf(part.getStock()));
        modPriceTxt.setText(String.valueOf(part.getPrice()));
        modMaxTxt.setText(String.valueOf(part.getMax()));
        modMinTxt.setText(String.valueOf(part.getMin()));

        if (part instanceof InHouse) {
            modInRBtn.fire();
            modInorOutLbl.setText("Machine ID");
            modMacIdTxt.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else if (part instanceof Outsourced) {
            modOutRBtn.fire();;
            modInorOutLbl.setText("Company Name");
            modMacIdTxt.setText(((Outsourced) part).getCompanyName());
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
