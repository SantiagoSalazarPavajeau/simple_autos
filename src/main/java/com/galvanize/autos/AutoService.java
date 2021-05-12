package com.galvanize.autos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AutoService {


    public AutoService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    AutosRepository autosRepository;

//    List<Auto> autoList = new ArrayList<>();

    public AutoList getAutos(){
        // Query: select * from autos;
        // Put that in a list
        // Return a new AutosList with the list
        return new AutoList(autosRepository.findAll());
    }

    public AutoList getAutos(String color, String make){
        return null;
    }

    public Auto addAuto(Auto auto) {
        return null;
    }

    public Auto getAuto(String vin) {
        return null;
    }

    public Auto updateAuto(String vin, String color, String owner) {
        return null;
    }

    public void deleteAuto(int vin) {

    }
}


//
//    public List<Auto> getAllByMake(String make){
//        List<Auto> resultsByMake = new ArrayList<>();
//        for(Auto auto : autoList){
//            if (auto.getMake() == make){
//                resultsByMake.add(auto);
//            }
//        }
//        return resultsByMake;
//    }
//
//    public List<Auto> getAllByMakeAndColor(String color, String make) {
//        List<Auto> resultsByMakeAndColor = new ArrayList<>();
//        for(Auto auto : autoList){
//            if (auto.getColor() == color && auto.getMake() == make ){
//                resultsByMakeAndColor.add(auto);
//            }
//        }
//        return resultsByMakeAndColor;
//    }
