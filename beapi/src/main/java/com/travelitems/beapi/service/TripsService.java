package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripsService {
    private final TripRepository tripRepository;

    public TripDto createTrip(TripNewDto tripNewDtoData) {
        return tripRepository.save(new Trip(RandomString.get(8), tripNewDtoData.getName(),
                tripNewDtoData.getLocation())).tripToDto();
    }

    public TripDto checkTrip(String tripUrl) throws AttributeNotFoundException {
        return tripRepository.findByTripUrl(tripUrl).map(trip -> { return trip.tripToDto(); })
                .orElseThrow(() -> new AttributeNotFoundException());
    }
}