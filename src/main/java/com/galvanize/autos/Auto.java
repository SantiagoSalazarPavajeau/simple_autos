package com.galvanize.autos;

public class Auto {
    private String make;
    private String color;
    private int vin;

    public Auto() {
    }

    public Auto(String make, String color) {
        this.make = make;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setVin(int vin) {
        this.vin = vin;
    }
    public int getVin() {
        return vin;
    }
}
