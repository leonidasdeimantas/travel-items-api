package com.travelitems.beapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TripPublicDto {
	private String url;
	private boolean isPublic;
}
