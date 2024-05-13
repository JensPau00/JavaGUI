package com.example.c482_project;
/**
 *
 * @author Paul Jensen
 */
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Scene;
        import javafx.scene.Parent;
        import javafx.scene.control.*;
        import javafx.scene.control.cell.PropertyValueFactory;
        import javafx.scene.input.KeyCode;
        import javafx.scene.input.KeyEvent;
        import javafx.stage.Stage;

        import java.io.IOException;

/**
 *Main Screen Controller
 */
public class MainScreenController {
    public Alert confirm= new Alert(Alert.AlertType.CONFIRMATION);
    public Alert error= new Alert(Alert.AlertType.ERROR);
    public TableView<Product> ProductTableView;
    public TableColumn<Product,Integer> productIDColumn;
    public TableColumn<Product,String> productNameColumn;
    public TableColumn<Product,Double> productPriCosColumn;
    public TableColumn<Product,Integer> productInventoryColumn;
    public TableView<Part> partsTableView;
    public TableColumn partIDcolumn;
    public TableColumn partNameColumn;
    public TableColumn partPriCosColumn;
    public TableColumn partInventoryColumn;
    Stage stage;
    Parent scene;
    @FXML
    private TextField searchParts;

    @FXML
    private TextField searchProducts;
    /**
     * FUTURE ENHANCEMENT - I would make the program retain whatever was searched when you return to this page.<br>
     * RUNTIME ERROR - I used @Overide instead of @FMXL
     */
    @FXML //RUNTIME ERROR for initialize
    public void initialize(){
        partsTableView.setItems(Inventory.getAllParts());
        partIDcolumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriCosColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        ProductTableView.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriCosColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    /**
     * <p>FUTURE ENHANCEMENT - I would allow you to select products that use the Part that is being entered into the system.<br>
     * RUNTIME ERROR - I didn't correctly cast the event to a stage causing an error.</p>
     */
    @FXML
    void OnActionAddPart(ActionEvent event) throws IOException {
        stage = (Stage)((Button)event.getSource()).getScene().getWindow(); //Runtime error
        scene = FXMLLoader.load((InventorySystem.class.getResource("Add Part.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - I would have the program display all Products that use the part that was deleted.<br>
     * RUNTIME ERROR - I just used show and didn't compare the results of confirm with anything so parts were always deleted.
     */
    @FXML
    void OnActionDeletePart(ActionEvent event) {
        if (partsTableView.getSelectionModel().getSelectedItem()==null){
            error.setContentText("You didn't select anything to delete");
            error.showAndWait();
        }
        else{
            Part part = partsTableView.getSelectionModel().getSelectedItem();
            confirm.setContentText("Would you like to delete "+part.getName()+"?");
            confirm.showAndWait(); //Runtime error
            if (confirm.getResult()==ButtonType.OK){
                Inventory.deletePart(part);}
        }
    }
    /**
     * FUTURE ENHANCEMENT - I would allow delete to clear the assossited parts instead of just giving an error.<br>
     * RUNTIME ERROR - I copy and pasted the code from delete and I forgot to change the partsTableVeiw to ProductTableView. This made it so I needed to select a part to delete a product.
     */
    @FXML
    void OnActionDeleteProduct(ActionEvent event) {
        if (ProductTableView.getSelectionModel().getSelectedItem()==null){ //Runtime error
            error.setContentText("You didn't select anything to delete");
            error.showAndWait();
        }
        else {
            Product product = ProductTableView.getSelectionModel().getSelectedItem();
            confirm.setContentText("Would you like to delete " + product.getName()+"?");
            confirm.showAndWait();
            if (confirm.getResult() == ButtonType.OK) {
                if (product.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(product);
                }
                else{
                    error.setContentText("This Product still has accosiated Parts");
                    error.showAndWait();
                }
            }
        }

    }
    /**
     * FUTURE ENHANCEMENT - I would save the information for future use on exit.<br>
     * RUNTIME ERROR - I didn't use show and wait the first time so it got the last results of confirm requiring you to press the button twice
     */
    @FXML
    void OnActionExitApplication(ActionEvent event) {
        confirm.setContentText("Would you like to exit?");
        confirm.showAndWait();
        if (confirm.getResult()==ButtonType.OK){ // Runtime Error
            System.exit(0);
        }


    }
    /**
     * FUTURE ENHANCEMENT - I would allow for modifying a product to save the old product if you wish to allow for easier additions such as if you want to offer a bike with different tires but everything else the same.<br>
     * RUNTIME ERROR - I didn't have a error alert causing a null product to be sent
     */
    @FXML
    void OnActionModifyProduct(ActionEvent event) throws IOException{
        if (ProductTableView.getSelectionModel().getSelectedItem()==null){ //runtime error
            error.setContentText("You didn't select anything to modify");
            error.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((InventorySystem.class.getResource("Modify Product.fxml")));
            loader.load();
            ModifyProductController MPController = loader.getController();
            MPController.sendProduct(ProductTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * FUTURE ENHANCEMENT - I would allow send data for searched parts to the addproduct page.<br>
     * RUNTIME ERROR - I didn't have a runtime error here.
     */
    @FXML
    void onActionAddProduct(ActionEvent event) throws IOException{
        stage = (Stage)((Button)event.getSource()).getScene().getWindow();
        scene = FXMLLoader.load((InventorySystem.class.getResource("Add Product.fxml")));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    /**
     * FUTURE ENHANCEMENT - I would allow for multiple parts to be selected allowing you to modify multiple parts back to back.<br>
     * RUNTIME ERROR - I copyed the code from the modifyProduct method and forgot to change the name of the fmxl document causing an error.
     */
    @FXML
    void onActionModifyPart(ActionEvent event) throws IOException{
        if (partsTableView.getSelectionModel().getSelectedItem()==null){
            error.setContentText("You didn't select anything to modify");
            error.showAndWait();
        }
        else {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation((InventorySystem.class.getResource("Modify Part.fxml"))); //runtime error
            loader.load();
            ModifyPartController MPController = loader.getController();
            MPController.sendPart(partsTableView.getSelectionModel().getSelectedItem());
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.show();
        }
    }
    /**
     * FUTURE ENHANCEMENT - I would add a filter to allow a search by any varible.<br>
     * RUNTIME ERROR - I was comparing two strings using == and it didn't return true when they were equal. This was fixed using String.equals
     */
    @FXML
    void onEnterPressedSearchPart(KeyEvent event)
    {
        if (event.getCode()== KeyCode.ENTER) {
            partsTableView.setItems(Inventory.getAllParts());
            partsTableView.getSelectionModel().select(null);
            try {
                partsTableView.getSelectionModel().select(Inventory.lookupPart(Integer.parseInt(searchParts.getText())));
            } catch (Exception e) {
                partsTableView.setItems(Inventory.lookupPart(searchParts.getText())); //RUNTIME ERROR
            }
        }
    }
    /**
     * FUTURE ENHANCEMENT - I would all for products to be searched by their associated parts.<br>
     * RUNTIME ERROR - While Implementing the integer search I didn't wrap it in a try catch statement, causing an error when attempting to look up a string
     */
    @FXML
    void onEnterPressedSearchProduct(KeyEvent event)
    {
        if (event.getCode()== KeyCode.ENTER){
        ProductTableView.setItems(Inventory.getAllProducts());
        ProductTableView.getSelectionModel().select(null);
            try {
                ProductTableView.getSelectionModel().select(Inventory.lookupProduct(Integer.parseInt(searchProducts.getText()))); //runtime error
            }
            catch (Exception e) {
                ProductTableView.setItems(Inventory.lookupProduct(searchProducts.getText()));
            }
        }}

}