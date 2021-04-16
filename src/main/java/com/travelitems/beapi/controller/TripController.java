package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public TripDto getTripExist(@RequestParam String tripUrl) {
        try {
            return tripService.getTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public TripDto addTrip(@RequestBody TripNewDto tripNewDtoData) {
        return tripService.createTrip(tripNewDtoData);
    }

    @DeleteMapping
    public ResponseEntity deleteTrip(@RequestParam String tripUrl){
        try {
            tripService.deleteTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
