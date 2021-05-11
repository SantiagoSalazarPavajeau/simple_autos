package com.galvanize.autos;

public class Auto {
    private String make;
    private String color;
    private int vin;

    public Auto() {
    }

    public void setMake(String make) {
        this.make = make;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Auto(String make, String color) {
        this.make = make;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public String getMake() {
        return make;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }
    public int getVin() {
        return vin;
    }
}
