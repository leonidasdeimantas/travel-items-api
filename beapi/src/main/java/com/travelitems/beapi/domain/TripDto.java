package com.travelitems.beapi.domain;

import lombok.Data;

@Data
public class TripDto {
    private String url;
    private String name;
    private String location;

    public TripDto(String url, String name, String location) {
        this.url = url;
        this.name = name;
        this.location = location;
    }
}
