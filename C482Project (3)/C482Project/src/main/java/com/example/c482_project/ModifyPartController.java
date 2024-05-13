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
 * Modify part controller
 */
public class ModifyPartController {
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
     * FUTURE ENHANCEMENT - I would allow for partially completed parts to be sent.<br>
     * RUNTIME ERROR - I didn't use value of for the integers and doubles.
     */
    @FXML
    public void sendPart(Part part){
        nameTextField.setText(part.getName());
        IDTextField.setText(String.valueOf(part.getId()));//runtime error
        maxTextField.setText(String.valueOf(part.getMax()));
        minTextField.setText(String.valueOf(part.getMin()));
        priCosTextField.setText(String.valueOf(part.getPrice()));
        if (part instanceof Outsourced) {
            CompMacLBL.setText("Company Name");
            outsourceRBN.setSelected(true);
            MachineTextField.setText(((Outsourced) part).getCompanyName());
        }
        else if (part instanceof InHouse){
            InhouseRBN.setSelected(true);
            int macId=((InHouse) part).getMachineId();
            MachineTextField.setText(String.valueOf(macId));
        }
        inventortyTextField.setText(String.valueOf(part.getStock()));
    }
    /**
     * FUTURE ENHANCEMENT -I would add a confirmation message to cancel to make sure you don't accidentally leave when you are trying to save.<br>
     * RUNTIME ERROR - I didn't experience any logical errors here.
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((InventorySystem.class.getResource("MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - I would add the option to create a new part with the new information.<br>
     * RUNTIME ERROR - I had the logical error of trying to save Outsourced part to inhouse and vice-versa, so I just deleted the part and created a new one.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
        Part part = Inventory.lookupPart(Integer.parseInt(IDTextField.getText()));
        int id = part.getId();
        String errorMessage="Exception:\n";
        String name = nameTextField.getText();
        double price=0;
        boolean maxIsInt=false;
        boolean minIsInt=false;
        boolean stockIsInt=false;
        int max=0;
        int min=0;
        int stock=0;
        int machID=0;
        String CompanyName= MachineTextField.getText();
        if (name.isEmpty()){
            errorMessage+="Name must be non-null\n";
        }
        if (outsourceRBN.isSelected()&&CompanyName.isEmpty()){
            errorMessage+="Company Name must be non-null\n";
        }
        try{
            price = Double.parseDouble(priCosTextField.getText());
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
            Inventory.deletePart(part); //Runtime
            if (InhouseRBN.isSelected() ) {
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
     * FUTURE ENHANCEMENT - I would a confirmation box to tell the user when they are changing from inHouse to outSourced on save with an option to disable it.<br>
     * RUNTIME ERROR - I didn't experience a logical error here.
     */
    public void selectInHouse(ActionEvent actionEvent) {
        CompMacLBL.setText("Machine ID");
    }
    /**
     * FUTURE ENHANCEMENT - I would a confirmation box to tell the user when they are changing from outSourced to inHouse on save with an option to disable it.<br>
     * RUNTIME ERROR - I didn't experience a logical error here.
     */
    public void selectOutsourced(ActionEvent actionEvent) {

        CompMacLBL.setText("Company Name");
    }
}
