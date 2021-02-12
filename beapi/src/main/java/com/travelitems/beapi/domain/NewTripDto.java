package com.travelitems.beapi.domain;

import lombok.Data;

@Data
public class NewTripDto {
    private String name;
    private String location;

    public NewTripDto(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
