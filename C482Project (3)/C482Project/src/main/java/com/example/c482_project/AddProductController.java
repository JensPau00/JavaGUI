package com.example.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * add product controller
 */
public class   AddProductController {
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
    Stage stage;
    Parent scene;
    /**
     * FUTURE ENHANCEMENT - I would save the text that you type into search as well.<br>
     * RUNTIME ERROR - I didn't experience a logical error here.
     */
    @FXML
    public void initialize(){
        PartsTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        PriCosColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        inventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductParts.setItems(addProductList);
        ProdPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        ProdPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        ProdInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));;
    }
    @FXML
    /**
     * FUTURE ENHANCEMENT - I would allow you to add multiple products at the same time by selecting more than one.<br>
     * RUNTIME ERROR - I didn't experience a logical error here.
     */
    void OnActionAdd(ActionEvent event) {
        Part part = (Part) PartsTableView.getSelectionModel().getSelectedItem();
        addProductList.add(part);
    }
    /**
     * FUTURE ENHANCEMENT - I would add a confirmation pop-up to prevent miss clicks.<br>
     * RUNTIME ERROR - I didn't experience a logical error here.
     */
    @FXML
    void OnActionCancel(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((InventorySystem.class.getResource("MainMenu.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - I would add a confirmation pop-up to prevent miss clicks.<br>
     * RUNTIME ERROR - I accidentally made associatedParts static resulting in it appearing like this method removed that part from all products it was associated with.
     */
    @FXML
    void OnActionRemoveAssociated(ActionEvent event) {
        Part part = (Part)ProductParts.getSelectionModel().getSelectedItem();
        addProductList.remove(part);
    }
    /**
     * FUTURE ENHANCEMENT - I would add a confirmation pop-up to prevent miss clicks.<br>
     * RUNTIME ERROR - When I saved parts it looked like random parts were added when I went to save it. This was due to a static associatedParts field.
     */
    @FXML
    void OnActionSave(ActionEvent event) throws IOException {
        int id=0;
        for (Product each:Inventory.getAllProducts()) {
            if (each.getId()>id){
                id = each.getId();
            }
            id+=1;
        }
        String errorMessage="Exception:\n";
        String name = nameTextField.getText();
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
            Product product=new Product(id,name,price,stock,min,max);
            Inventory.AddProduct(product);
            for (Part each:addProductList) {
                product.addAssociatedPart(each); //Run time error
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

}
