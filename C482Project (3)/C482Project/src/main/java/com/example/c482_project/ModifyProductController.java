package com.example.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * Modify product controller
 */
public class ModifyProductController {
    public ObservableList<Part> addProductList = FXCollections.observableArrayList();
    public TableColumn ProdPartIDColumn;
    public TableColumn ProdPartName;
    public TableColumn ProdInventoryLevelColumn;
    public TextField minTextField;
    public TableColumn PriCosColumn;
    public TableColumn inventoryLevelColumn;
    public TableColumn partNameColumn;
    public TableColumn partIDColumn;
    public TableView<Part> PartsTableView;
    public TextField nameTextField;
    public TextField invTextField;
    public TextField priceTextField;
    public TextField maxTextField;
    public TableView<Part> ProductParts;
    public Text ExceptionText;
    public TextField prodIDTextField;
    public TextField searchPartTextField;
    Stage stage;
    Parent scene;
    public Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
    public Alert error= new Alert(Alert.AlertType.ERROR);
    /**
     * FUTURE ENHANCEMENT - I would allow for multiple products to be sent to allow for multiple windows at once.<br>
     * RUNTIME ERROR - Canceling the screen cause parts to be deleted from the Product so I created a new product list
     */
    @FXML
    public void sendProduct(Product product){
        prodIDTextField.setText(String.valueOf(product.getId()));
        nameTextField.setText(product.getName());
        prodIDTextField.setText(String.valueOf(product.getId()));
        maxTextField.setText(String.valueOf(product.getMax()));
        minTextField.setText(String.valueOf(product.getMin()));
        priceTextField.setText(String.valueOf(product.getPrice()));
        invTextField.setText(String.valueOf(product.getStock()));
        addProductList = product.getAllAssociatedParts(); // runtime error
        ProductParts.setItems(this.addProductList);
    }
    /**
     * FUTURE ENHANCEMENT - I would make the search data previously used on this page load here.<br>
     * RUNTIME ERROR - I didn't have a runtime error here.
     */
    @FXML
    public void initialize(){
        PartsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriCosColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProdPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));;
    }
    /**
     * FUTURE ENHANCEMENT - I would allow you to select multiple parts at a time.<br>
     * RUNTIME ERROR - I didn't have a runtime error here.
     */
    @FXML
    void OnActionAdd(ActionEvent event) {
        Part part = (Part) PartsTableView.getSelectionModel().getSelectedItem();
        this.addProductList.add(part);
    }
    /**
     * FUTURE ENHANCEMENT - I would allow you to select multiple parts at a time.<br>
     * RUNTIME ERROR - When parts were deleted and then you canceled they were removed from the product.
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((InventorySystem.class.getResource("MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - I would allow for multiple parts to be deleted at the same time.<br>
     * RUNTIME ERROR - I didn't have a runtime error here.
     */
    @FXML
    void OnActionRemoveAssociated(ActionEvent event) {
        Part part = (Part) ProductParts.getSelectionModel().getSelectedItem();
        if (ProductParts.getSelectionModel().getSelectedItem() == null) {
            error.setContentText("You didn't select anything to delete");
            error.showAndWait();
        } else {
            Part part1 = ProductParts.getSelectionModel().getSelectedItem();
            confirm.setContentText("Would you like to delete " + part1.getName() + "?");
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                addProductList.remove(part1);
            }
        }
    }
    /**
     * FUTURE ENHANCEMENT - If you try to save a product with no associated parts a dialog box would ask if you would like to delete the Product.<br>
     * RUNTIME ERROR - I made Inventory.updateProduct only effect the second space of the array so I could test it and forgot to change it so when I tried to save a part it would only save 1.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
        Product product = Inventory.lookupProduct(Integer.parseInt(prodIDTextField.getText()));
        int id = product.getId();
        String errorMessage="Exception:\n";
        String name = nameTextField.getText().toString();
        double price=0;
        boolean maxIsInt=false;
        boolean minIsInt=false;
        boolean stockIsInt=false;
        int max=0;
        int min=0;
        int stock=0;
        if (name.isEmpty()){
            errorMessage+="Name must be non-null\n";
        }
        try{
            price = Double.parseDouble(priceTextField.getText());
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
            stock = Integer.parseInt(invTextField.getText());
            stockIsInt = true;
        }
        catch (Exception e){
            errorMessage += "Inventory must be an Integer\n";
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
            Product product1=new Product(id,name,price,stock,min,max);
            for (Part each:this.addProductList) {
                product1.addAssociatedPart(each);
            }
            Inventory.updateProduct(Inventory.getAllProducts().indexOf(product),product1);
            Inventory.deleteProduct(product);
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
     * FUTURE ENHANCEMENT - I would add additional search parameters.<br>
     * RUNTIME ERROR - I was accidentally selecting parts from the table below.
     */
    @FXML
    void onEnterPressedSearchPart(KeyEvent event)
    {
        if (event.getCode()== KeyCode.ENTER) {
            PartsTableView.setItems(Inventory.getAllParts());
            PartsTableView.getSelectionModel().select(null);
            try {
                PartsTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(searchPartTextField.getText())));
            } catch (Exception e) {
                PartsTableView.setItems(Inventory.lookupPart(searchPartTextField.getText()));
            }
        }
    }
}
