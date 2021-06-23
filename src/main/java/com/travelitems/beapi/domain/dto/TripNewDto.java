package com.travelitems.beapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripNewDto {
    private String name;
    private String location;
}
