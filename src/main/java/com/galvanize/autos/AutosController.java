package com.galvanize.autos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class AutosController {
 //GET
    AutoService autoService;

    public AutosController(AutoService autoService) {
        this.autoService = autoService;
    }
    // GET:/api/autos  returns list of all autos in db
    // GET: /api/autos no autos in db returns 204 no content
    // GET: /api/autos?color=RED  returns red cars
    // GET: /api/autos?make=Ford  returns all fords cars
    // GET: / api/autos?make=Ford&color=GREEN returns green fords
    // GET: / api/autos?make=Ford&color=GREEN  no autos returns 204
    @GetMapping("/api/autos")
    public Object searchForCars(@RequestParam(required = false) String color,
                                @RequestParam(required = false) String make){

        if(color != null & make != null && (autoService.getAllByMakeAndColor(color, make).size() > 0)) {
            return autoService.getAllByMakeAndColor(color, make);
        } else if (color != null && (autoService.getAllByColor(color).size() > 0) ) {
            return autoService.getAllByColor(color);
        } else if (make != null && (autoService.getAllByMake(make).size() > 0)) {
            return autoService.getAllByMake(make);
        } else if(autoService.getAllCars().size() > 0) {
            return autoService.getAllCars();
        }else{
            return ResponseEntity.noContent().build();
        }

    }




 //POST
    // POST: /api/autos   returns created car
    @PostMapping("/api/autos")
    public Auto addAuto(@RequestBody Auto auto){
        return autoService.addAuto(auto);
    }
    // POST: / api/autos returns error message due to bad request (400)

    @GetMapping("/api/autos/{vin}")
    public Auto getAuto(@PathVariable int vin) {
        return autoService.getAuto(vin);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAuto(InvalidAutoException exception){

    }


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
