package com.travelitems.beapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripNewDto {
    private String name;
    private String location;
}
