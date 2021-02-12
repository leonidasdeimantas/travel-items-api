package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.NewTripDto;
import com.travelitems.beapi.service.TripsService;
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
    private final TripsService tripsService;

    @GetMapping
    public TripDto getTripExist(@RequestParam String tripUrl) {
        try {
            return tripsService.getTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public TripDto addTrip(@RequestBody NewTripDto newTripDtoData) {
        return tripsService.createTrip(newTripDtoData);
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTrip(@RequestParam String tripUrl){
        try {
            tripsService.deleteTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(tripUrl, HttpStatus.OK);
    }
}
