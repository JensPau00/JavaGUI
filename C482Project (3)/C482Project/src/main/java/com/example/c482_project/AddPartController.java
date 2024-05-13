package com.example.c482_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * add part controller
 */
public class AddPartController {
    public RadioButton InhouseRBN;
    public RadioButton outsourceRBN;
    public Text CompMacLBL;
    public TextField maxTextField;
    public TextField MachineTextField;
    public TextField priCosTextField;
    public TextField inventortyTextField;
    public TextField nameTextField;
    public TextField IDTextField;
    public TextField minTextField;
    public Text ExceptionText;
    Stage stage;
    Parent scene;
    /**
     * FUTURE ENHANCEMENT - I would add a confirmation message to cancel to make sure you don't accidentally leave when you are trying to save.<br>
     * RUNTIME ERROR - I didn't experince a runtime error here
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((InventorySystem.class.getResource("MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - If the fields are not complete I would ask of they would like to fill them in with default values so they can modify the part later when they have that information.<br>
     * RUNTIME ERROR - I ran into a logical error here when I left price = Integer.parseInt(). This coused doubles to not be excepted but it displayed that the number must be a double.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
        int id=0;
        for (Part each:Inventory.getAllParts()) {
            if (each.getId()>id){
                id = each.getId();
            }
            id+=1;
        }
        String errorMessage="Exception:\n";
        String name = nameTextField.getText().toString();
        double price=0;
        boolean maxIsInt=false;
        boolean minIsInt=false;
        boolean stockIsInt=false;
        int max=0;
        int min=0;
        int stock=0;
        int machID=0;
        String CompanyName= MachineTextField.getText().toString();
        if (name.isEmpty()){
            errorMessage+="Name must be non-null\n";
        }
        if (outsourceRBN.isSelected()&&CompanyName.isEmpty()){
            errorMessage+="Company Name must be non-null\n";
        }
            try{
                price = Double.parseDouble(priCosTextField.getText());//runtime error
            }
            catch (Exception e){
                errorMessage += "Price must be a Double\n";
            }
            try{
                min = Integer.parseInt(minTextField.getText());
                minIsInt = true;
            }
            catch (Exception e){
                errorMessage += "Min must be an Integer\n";
            }
            try{
                max = Integer.parseInt(maxTextField.getText());
                maxIsInt = true;
            }
            catch (Exception e){
                errorMessage += "Max must be an Integer\n";
            }
            try{
                stock = Integer.parseInt(inventortyTextField.getText());
                stockIsInt = true;
            }
            catch (Exception e){
                errorMessage += "Inventory must be an Integer\n";
            }
            try{
                if (InhouseRBN.isSelected()) {
                    machID = Integer.parseInt(CompanyName);
                }
            }
            catch (Exception e){
                errorMessage += "Machine ID must be an Integer\n";
            }

            if (maxIsInt&&minIsInt){
                boolean maxLessMin=max<min;
                if (maxLessMin){
                    errorMessage += "Min must be smaller than Max\n";
                }
            }
            if (maxIsInt && minIsInt && stockIsInt) {
                if (stock > max) {
                    errorMessage += "Inventory must be smaller than Max\n";
                }
                if (stock < min) {
                    errorMessage += "Inventory must be larger than Min\n";
                }
            }
            if (errorMessage=="Exception:\n"){
                if (InhouseRBN.isSelected()) {
                    Inventory.addPart(new InHouse(id,name,price,stock,min,max,machID));
                }
                else{
                    Inventory.addPart(new Outsourced(id,name,price,stock,min,max,CompanyName));
                }
                stage = (Stage)((Button)event.getSource()).getScene().getWindow();
                scene = FXMLLoader.load((InventorySystem.class.getResource("MainMenu.fxml")));
                stage.setScene(new Scene(scene));
                stage.show();
            }
            else{
                ExceptionText.setText(errorMessage);
            }
        }
    /**
     * FUTURE ENHANCEMENT - I would clear the bottom text field on the selection of the Radio button.<br>
     * RUNTIME ERROR -I did't experince a runtime error here.
     */
    public void selectInHouse(ActionEvent actionEvent) {
        CompMacLBL.setText("Machine ID");
    }
    /**
     * FUTURE ENHANCEMENT - If there is text in the box I would ask to confirm the switch.<br>
     * RUNTIME ERROR - I ran into a logical error here when I left price = Integer.parseInt(). This coused doubles to not be excepted but it displayed that the number must be a double.
     */
    public void selectOutsourced(ActionEvent actionEvent) {

        CompMacLBL.setText("Company Name");
    }
}
