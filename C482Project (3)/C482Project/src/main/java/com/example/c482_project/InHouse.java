package com.example.c482_project;

public class InHouse extends Part{
    int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max,int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId=machineId;
    }

    public void setMachineId(final int machineId) {
        this.machineId = machineId;
    }

    public int getMachineId() {
        return this.machineId;
    }
}
