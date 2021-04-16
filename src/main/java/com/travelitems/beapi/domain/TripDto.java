package com.travelitems.beapi.domain;

import lombok.Data;

@Data
public class TripDto {
    private String tripUrl;
    private String name;
    private String location;

    public TripDto(String tripUrl, String name, String location) {
        this.tripUrl = tripUrl;
        this.name = name;
        this.location = location;
    }
}
