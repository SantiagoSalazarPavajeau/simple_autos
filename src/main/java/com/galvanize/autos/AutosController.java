package com.galvanize.autos;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AutosController {
 //GET
    AutosService autosService;

    public AutosController(AutosService autosService) {
        this.autosService = autosService;
    }
    // GET:/api/autos  returns list of all autos in db
    // GET: /api/autos no autos in db returns 204 no content
    // GET: /api/autos?color=RED  returns red cars
    // GET: /api/autos?make=Ford  returns all fords cars
    // GET: / api/autos?make=Ford&color=GREEN returns green fords
    // GET: / api/autos?make=Ford&color=GREEN  no autos returns 204
    @GetMapping("/api/autos")
    public ResponseEntity<AutosList> getAutos(@RequestParam (required = false) String color,
                                              @RequestParam ( required = false) String make) {
        AutosList autosList;

        if(color == null && make == null){
            autosList = autosService.getAutos();
        }else{
            autosList = autosService.getAutos(color, make);
        }
        return autosList.isEmpty() ? ResponseEntity.noContent().build() :
                ResponseEntity.ok(autosList);
    }
 //POST
    // POST: /api/autos   returns created car
    @PostMapping("/api/autos")
    public Automobile addAuto(@RequestBody Automobile auto){
        return autosService.addAuto(auto);
    }

    @GetMapping("/api/autos/{vin}")
    public Automobile getAuto(@PathVariable String vin) {
        return autosService.getAuto(vin);
    }
//    public ResponseEntity<Automobile> getAuto(@PathVariable String vin) {
//        try{
//            autosService.getAuto(vin);
//        } catch (AutoNotFoundException ex){
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.ok(autosService.getAuto(vin));
//    }

    @PatchMapping("/api/autos/{vin}")
    public ResponseEntity<Automobile> updateAuto(@PathVariable String vin,
                                                 @RequestBody UpdateOwnerRequest update) {
        Automobile auto = autosService.updateAuto(vin, update.getColor(), update.getOwner());
//        try{
//            auto = autosService.updateAuto(vin, update.getColor(), update.getOwner());
//
//        } catch (AutoNotFoundException ex){
//            return ResponseEntity.noContent().build(); //stops if no
//        }
        auto.setColor(update.getColor());
        auto.setOwner(update.getOwner());

       return ResponseEntity.ok(auto);
    }

    // DELETE: /api/autos/{vin}
    // DELETE: /api/autos/{vin}  returns 202 , car deleted
    @DeleteMapping("/api/autos/{vin}")
    public ResponseEntity deleteAutoByVin(@PathVariable String vin){
        try{
            autosService.deleteAuto(vin);
        } catch (AutoNotFoundException ex){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.accepted().build(); //202 instead of 200
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public void invalidAuto(InvalidAutoException e){

    }








}
