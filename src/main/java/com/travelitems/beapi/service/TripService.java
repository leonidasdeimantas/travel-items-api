package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import com.travelitems.beapi.utils.RandomString;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
    private final SecurityServiceImpl securityService;

    private User getUser() {
        return userRepository.findByUsername(securityService.findLoggedInUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with this username"));
    }

    public TripDto createTrip(TripNewDto tripNewDtoData) throws AttributeNotFoundException {
        return tripRepository.save(new Trip(RandomString.get(8), tripNewDtoData.getName(),
                tripNewDtoData.getLocation(), getUser().getId())).tripToDto();
    }

    public TripDto getTrip(String tripUrl) throws AttributeNotFoundException {
        return tripRepository.findByTripUrlAndUserId(tripUrl, getUser().getId()).map(trip -> { return trip.tripToDto(); })
                .orElseThrow(() -> new AttributeNotFoundException());
    }

    public List<TripDto> getAllTrips() {
        List<TripDto> tripDtoList = new ArrayList<>();
        tripRepository.findByUserId(getUser().getId()).forEach(trip -> {
            tripDtoList.add(trip.tripToDto());
        });

        return tripDtoList;
    }

    public void deleteTrip(String url) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrlAndUserId(url, getUser().getId()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        taskRepository.deleteByTripUrl(url);
        assigneeRepository.deleteByTripUrl(url);
        tripRepository.deleteByTripUrl(url);
    }
}
