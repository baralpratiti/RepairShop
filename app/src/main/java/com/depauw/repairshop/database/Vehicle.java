package com.depauw.repairshop.database;

public class Vehicle
{

    private int vid;
    private int year;
    private float price;
    private String makeModel;
    private int isNew;

    public Vehicle(int year, float price, String makeModel, int isNew)
    {
        this.year = year;
        this.price = price;
        this.makeModel = makeModel;
        this.isNew = isNew;
    }

    public Vehicle(int vid, int year, String makeModel, float price, int isNew)
    {
        this.vid = vid;
        this.year = year;
        this.makeModel = makeModel;
        this.price = price;
        this.isNew = isNew;
    }


    public int getId()
    {
        return vid;
    }

    public int getYear()
    {
        return year;
    }

    public float getPrice()
    {
        return price;
    }

    public String getMakeModel()
    {
        return makeModel;
    }

    public int isNew()
    {
        return isNew;
    }

    @Override
    public String toString() {
        return year + " " + makeModel;
    }
}
