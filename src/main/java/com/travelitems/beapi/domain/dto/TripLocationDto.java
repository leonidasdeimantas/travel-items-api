package com.travelitems.beapi.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripLocationDto {
	private String url;
	private String location;
}