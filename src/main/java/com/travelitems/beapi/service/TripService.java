package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private final TripRepository tripRepository;
    private final TaskRepository taskRepository;
    private final AssigneeRepository assigneeRepository;

    public TripDto createTrip(TripNewDto tripNewDtoData) {
        return tripRepository.save(new Trip(RandomString.get(8), tripNewDtoData.getName(),
                tripNewDtoData.getLocation())).tripToDto();
    }

    public TripDto getTrip(String tripUrl) throws AttributeNotFoundException {
        return tripRepository.findByTripUrl(tripUrl).map(trip -> { return trip.tripToDto(); })
                .orElseThrow(() -> new AttributeNotFoundException());
    }

    public void deleteTrip(String url) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent()) {
            throw new AttributeNotFoundException();
        }
        taskRepository.deleteByTripUrl(url);
        assigneeRepository.deleteByTripUrl(url);
        tripRepository.deleteByTripUrl(url);
    }
}
