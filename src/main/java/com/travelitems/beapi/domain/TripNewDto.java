package com.travelitems.beapi.domain;

import lombok.Data;

@Data
public class TripNewDto {
    private String name;
    private String location;

    public TripNewDto(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
