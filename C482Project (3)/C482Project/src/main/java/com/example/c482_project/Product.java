package com.example.c482_project;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id,String name,double price,int stock,int min,int max){
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    public double getPrice() {
        return this.price;
    }

    public int getId() {
        return this.id;
    }

    public int getMax() {
        return this.max;
    }

    public int getMin() {
        return this.min;
    }

    public int getStock() {
        return this.stock;
    }
    public boolean deleteAssociatedPart(Part selectedAssociatedPart){
        return this.associatedParts.remove(selectedAssociatedPart);
    }
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    public String getName() {
        return this.name;
    }

    public  void addAssociatedPart(Part associatedPart) {
        this.associatedParts.add(associatedPart);
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setMax(final int max) {
        this.max = max;
    }

    public void setMin(final int min) {
        this.min = min;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    public void setStock(final int stock) {
        this.stock = stock;
    }
}
