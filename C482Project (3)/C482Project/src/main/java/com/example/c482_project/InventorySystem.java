package com.example.c482_project;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/*
Java Doc located in file called Javadoc.ZIP
 */
public class InventorySystem extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        Part tire  = new InHouse(1,"Tire",1.00,5,1,15,130);
        Part BallBearings  = new Outsourced(2,"Ball Bearings",7.00,8,1,45,"BeanINC");
        Product bike= new Product(1,"Bike",4.00,10,1,10);
        bike.addAssociatedPart(tire);
        bike.addAssociatedPart(BallBearings);
        Product trike = new Product(2, "Trike", 4.0, 10, 1, 10);
        trike.addAssociatedPart(tire);
        ObservableList<Part> invenPart= FXCollections.observableArrayList(tire,BallBearings);
        ObservableList<Product> invenProduct = FXCollections.observableArrayList(bike,trike);
        Inventory inventory = new Inventory(invenPart,invenProduct);
        FXMLLoader fxmlLoader = new FXMLLoader(InventorySystem.class.getResource("MainMenu.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 600);
        stage.setTitle("Main Menu!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}