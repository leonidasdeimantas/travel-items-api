package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.security.jwt.JwtUtils;
import com.travelitems.beapi.service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;
import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/trip")
public class TripController {
    private final TripService tripService;
    private final JwtUtils jwtUtils;

    @GetMapping
    public TripDto getTripExist(@RequestParam String tripUrl, HttpServletRequest request) {
        try {
            return tripService.getTrip(tripUrl, jwtUtils.getUsernameFromJwt(request));
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public TripDto addTrip(@RequestBody TripNewDto tripNewDtoData, HttpServletRequest request) {
        try {
            return tripService.createTrip(tripNewDtoData, jwtUtils.getUsernameFromJwt(request));
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping
    public ResponseEntity deleteTrip(@RequestParam String tripUrl, HttpServletRequest request){
        try {
            tripService.deleteTrip(tripUrl, jwtUtils.getUsernameFromJwt(request));
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
