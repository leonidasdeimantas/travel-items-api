package com.travelitems.beapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripDto {
    private String tripUrl;
    private String name;
    private String location;
    private boolean isPublic;
}
