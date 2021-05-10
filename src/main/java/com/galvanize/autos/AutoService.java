package com.galvanize.autos;

import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AutoService {
    List<Auto> autoList = new ArrayList<>();

    public List<Auto> getAllCars(){
        return autoList;
    }

}
