package com.example.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts;
    private static ObservableList<Product> allProducts;
    public Inventory(ObservableList<Part> allParts,ObservableList<Product> allProducts){
        this.allParts = allParts;
        this.allProducts = allProducts;
    }
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }
    public static void AddProduct(Product newProduct) {allProducts.add(newProduct);}
    public static Part lookupPart(int partId){
        Part rePart=null;
        for (Part each:allParts) {
         if (each.getId()==partId){
             rePart = each;
         }
        }
        return rePart;
    }
    public static Product lookupProduct(int productId){
        Product reProd=null;
        for (Product each:allProducts) {
            if (each.getId()==productId){
                reProd = each;
            }
        }
        return reProd;
    }
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> retList = FXCollections.observableArrayList();
        System.out.println("executed");
        for(Part each:allParts){
            try {
                if (partName.equals(each.getName().substring(0, partName.length()))) {
                    retList.add(each);
                }
            }
            catch (Exception e){
                //partName too large
            }
        }
        return retList;
    }
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> retList = FXCollections.observableArrayList();
        for(Product each:allProducts){
            try {
            if (productName.equals(each.getName().substring(0,productName.length()))){
                retList.add(each);
            }
            }
            catch (Exception e){
                //ProductName too large
            }
        }
        return retList;
    }
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index,selectedPart);
    }
    public static void updateProduct(int index, Product newProduct){
        allProducts.set(index,newProduct);
    }
    public static boolean deletePart(Part selectedPart){
        return allParts.remove(selectedPart);
    }
    public static boolean deleteProduct(Product selectedProduct){
        return allProducts.remove(selectedProduct);
    }
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }



    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }



}