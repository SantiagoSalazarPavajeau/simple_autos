package com.galvanize.autos;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AutoService {
    List<Auto> autoList = new ArrayList<>();

    public List<Auto> getAllCars(){
        return autoList;
    }

    public List<Auto> getAllByColor(String color){
        List<Auto> resultsByColor = new ArrayList<>();
        for(Auto auto : autoList){
            if (auto.getColor() == color){
                resultsByColor.add(auto);
            }
        }
        return resultsByColor;
    }

    public List<Auto> getAllByMake(String make){
        List<Auto> resultsByMake = new ArrayList<>();
        for(Auto auto : autoList){
            if (auto.getMake() == make){
                resultsByMake.add(auto);
            }
        }
        return resultsByMake;
    }

    public List<Auto> getAllByMakeAndColor(String color, String make) {
        List<Auto> resultsByMakeAndColor = new ArrayList<>();
        for(Auto auto : autoList){
            if (auto.getColor() == color && auto.getMake() == make ){
                resultsByMakeAndColor.add(auto);
            }
        }
        return resultsByMakeAndColor;
    }
}
