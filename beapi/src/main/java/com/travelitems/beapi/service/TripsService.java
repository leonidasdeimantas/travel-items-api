package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TripsService {
    private final TripRepository tripRepository;

    public TripDto createTrip(TripNewDto tripNewDtoData) {
        Trip newTrip = new Trip(RandomString.get(8), tripNewDtoData.getName(), tripNewDtoData.getLocation());
        TripDto tripDto = new TripDto(newTrip.getTripUrl(), newTrip.getName(), newTrip.getLocation());
        tripRepository.save(newTrip);
        return tripDto;
    }
}
