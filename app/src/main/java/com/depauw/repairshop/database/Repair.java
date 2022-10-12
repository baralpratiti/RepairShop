package com.depauw.repairshop.database;

public class Repair {
    private int rid;
    private int vehicle_id;
    private String date;
    private float price;
    private String description;


    public Repair(String date, float price, String description, int vehicle_id){
        this.vehicle_id = vehicle_id;
        this.date = date;
        this.price = price;
        this.description = description;
    }

    public int getId() {
        return rid;
    }

    public int getVehicle_id() {
        return vehicle_id;
    }

    public String getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
