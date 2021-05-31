package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class TripService {
    private final TripRepository tripRepository;
    private final TaskRepository taskRepository;
    private final AssigneeRepository assigneeRepository;
    private final UserRepository userRepository;

    public TripDto createTrip(TripNewDto tripNewDtoData, String username) throws AttributeNotFoundException {
        Long userId = userRepository.findByUsername(username).map(user -> { return user.getId(); })
                .orElseThrow(() -> new AttributeNotFoundException());

        return tripRepository.save(new Trip(RandomString.get(8), tripNewDtoData.getName(),
                tripNewDtoData.getLocation(), userId)).tripToDto();
    }

    public TripDto getTrip(String tripUrl, String username) throws AttributeNotFoundException {
        Long userId = userRepository.findByUsername(username).map(user -> { return user.getId(); })
                .orElseThrow(() -> new AttributeNotFoundException());

        return tripRepository.findByTripUrlAndUserId(tripUrl, userId).map(trip -> { return trip.tripToDto(); })
                .orElseThrow(() -> new AttributeNotFoundException());
    }

    public List<TripDto> getAllTrips(String username) throws AttributeNotFoundException {
        Long userId = userRepository.findByUsername(username).map(user -> { return user.getId(); })
                .orElseThrow(() -> new AttributeNotFoundException());

        List tripDtoList = new ArrayList();
        tripRepository.findByUserId(userId).forEach(trip -> {
            tripDtoList.add(trip.tripToDto());
        });

        return tripDtoList;
    }

    public void deleteTrip(String url, String username) throws AttributeNotFoundException {
        Long userId = userRepository.findByUsername(username).map(user -> { return user.getId(); })
                .orElseThrow(() -> new AttributeNotFoundException());

        if (!tripRepository.findByTripUrlAndUserId(url, userId).isPresent()) {
            throw new AttributeNotFoundException();
        }
        taskRepository.deleteByTripUrl(url);
        assigneeRepository.deleteByTripUrl(url);
        tripRepository.deleteByTripUrl(url);
    }
}
