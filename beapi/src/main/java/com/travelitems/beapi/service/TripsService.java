package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.NewTripDto;
import com.travelitems.beapi.domain.TripEntity;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TripsService {
    private final TripRepository tripRepository;
    private final TasksRepository tasksRepository;
    private final AssigneeRepository assigneeRepository;

    public TripDto createTrip(NewTripDto newTripDtoData) {
        return tripRepository.save(new TripEntity(RandomString.get(8), newTripDtoData.getName(),
                newTripDtoData.getLocation())).tripToDto();
    }

    public TripDto getTrip(String tripUrl) throws AttributeNotFoundException {
        return tripRepository.findByTripUrl(tripUrl).map(tripEntity -> { return tripEntity.tripToDto(); })
                .orElseThrow(() -> new AttributeNotFoundException());
    }

    public void deleteTrip(String url) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent()) {
            throw new AttributeNotFoundException();
        }
        tasksRepository.deleteByTripUrl(url);
        assigneeRepository.deleteByTripUrl(url);
        tripRepository.deleteByTripUrl(url);
    }
}
