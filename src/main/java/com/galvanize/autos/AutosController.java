package com.galvanize.autos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController

public class AutosController {
 //GET
    AutoService autoService;

    public AutosController(AutoService autoService) {
        this.autoService = autoService;
    }
    // GET:/api/autos  returns list of all autos in db
    @GetMapping("/api/autos")
    public List<Auto> getAll(){
        return autoService.getAllCars();
    }
    // GET: /api/autos no autos in db returns 204 no content

    // GET: /api/autos?color=RED  returns red cars
    // GET: /api/autos?make=Ford  returns all fords cars
    // GET: / api/autos?make=Ford&color=GREEN returns green fords
    // GET: / api/autos?make=Ford&color=GREEN  no autos returns 204

 //POST
    // POST: /api/autos   returns created car
    // POST: / api/autos returns error message due to bad request (400)

 // GET: /api/autos/{vin}
    // GET: /api/autos/{vin}  returns the requested automobile
    // GET: /api/autos/{vin}  return no content 204, car not found

// Patch: /api/autos/{vin}
    // PATCH: /api/autos/{vin}  returns patched auto
    // PATCH: /api/autos/{vin}  no autos in db, returns 204 no content
    // PATCH: /api/autos/{vin}  returns 400 bad request when no changes done

 // DELETE: /api/autos/{vin}
    // DELETE: /api/autos/{vin}  returns 202 , car deleted
    // DELETE: /api/autos/{vin}   returns 204 not found
}
