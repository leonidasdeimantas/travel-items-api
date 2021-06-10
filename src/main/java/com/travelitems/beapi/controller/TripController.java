package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.TripPublicDto;
import com.travelitems.beapi.security.jwt.JwtUtils;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import com.travelitems.beapi.service.TripService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;
import java.util.List;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@Slf4j
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;

    @GetMapping
    public TripDto getTrip(@RequestParam String tripUrl) {
        try {
            return tripService.getTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/all")
    public List<TripDto> getAllTrips() {
        return tripService.getAllTrips();
    }

    @PostMapping
    public TripDto addTrip(@RequestBody TripNewDto tripNewDtoData) {
        try {
            return tripService.createTrip(tripNewDtoData);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping
    public ResponseEntity changePublicTrip(@RequestBody TripPublicDto tripPublicDto) {
        try {
            tripService.changePublicTrip(tripPublicDto);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteTrip(@RequestParam String tripUrl){
        try {
            tripService.deleteTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
