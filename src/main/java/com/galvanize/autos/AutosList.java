package com.galvanize.autos;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class AutosList {
    private List<Automobile> autos;

    public AutosList() {
        this.autos = new ArrayList<>();
    }

    public AutosList(List<Automobile> automobiles) {
        this.autos = automobiles;
    }

    public List<Automobile> getAutomobiles() {
        return autos;
    }

    public void setAutomobiles(List<Automobile> automobiles) {
        this.autos = automobiles;
    }

    @JsonIgnore
    public boolean isEmpty(){
        return this.autos.isEmpty();
    }

    @Override
    public String toString() {
        return "AutoList{" + "automobiles=" + autos + '}';
    }
}
