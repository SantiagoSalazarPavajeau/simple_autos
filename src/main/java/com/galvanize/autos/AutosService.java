package com.galvanize.autos;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutosService {

    AutosRepository autosRepository;

    public AutosService(AutosRepository autosRepository) {
        this.autosRepository = autosRepository;
    }

    public AutosList getAutos(){
        // Query: select * from autos;
        // Put that in a list
        // Return a new AutosList with the list
        return new AutosList(autosRepository.findAll());
    }

    public AutosList getAutos(String color, String make){
        List<Automobile> automobiles = autosRepository.findByColorContainsAndMakeContains(color, make);
        if(!automobiles.isEmpty()){
            return new AutosList(automobiles);
        }
        return null;
    }

    public Automobile addAuto(Automobile auto) {
        return autosRepository.save(auto);
    }

    public Automobile getAuto(String vin) {
        return autosRepository.findByVin(vin).orElse(null);
    }

    public Automobile updateAuto(String vin, String color, String owner) {
        return null;
    }

    public void deleteAuto(String vin) {

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
