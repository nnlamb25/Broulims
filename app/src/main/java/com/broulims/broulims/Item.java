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

    public float getPrice() {
        return price;
    }

    public Item(String location, String name, String code, float price) {
        this.location = location;
        this.name = name;
        this.code = code;
        this.price = price;
    }

    private String location;
    private String name;
    private String code;
    private float price;


}
