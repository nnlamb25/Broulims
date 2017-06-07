package com.broulims.broulims;

/**
 * Created by Daniel on 6/5/2017.
 */

public class Item {
    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public double getPrice() {
        return price;
    }


    public Item(String name, String code, String location, double price) {
        this.location = location;
        this.name = name;
        this.code = code;
        this.price = price;
    }

    public Item() {
        location = null;
        name = null;
        code = null;
        price = 0;
    }

    private String location;
    private String name;
    private String code;
    private double price;


}
