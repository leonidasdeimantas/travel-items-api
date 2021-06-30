package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.*;
import com.travelitems.beapi.domain.dto.TripDto;
import com.travelitems.beapi.domain.dto.TripLocationDto;
import com.travelitems.beapi.domain.dto.TripNewDto;
import com.travelitems.beapi.domain.dto.TripPublicDto;
import com.travelitems.beapi.repo.*;
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
    private final NotesRepository notesRepository;

    // could be public
    public TripDto getTrip(String tripUrl) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(tripUrl).orElseThrow(() -> new AttributeNotFoundException());
        if (trip.isPublic() || trip.getUserId() == getUser().getId()) {
            return trip.tripToDto();
        } else {
            throw new AttributeNotFoundException();
        }
    }

    public Trip changeTripLocation(TripLocationDto tripLocationDto) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(tripLocationDto.getUrl()).orElseThrow(() -> new AttributeNotFoundException());
        if (!trip.isPublic() && !(trip.getUserId() == getUser().getId())) {
            throw new AttributeNotFoundException();
        }
        trip.setLocation(tripLocationDto.getLocation());
        return tripRepository.save(trip);
    }

    // only with auth
    public TripDto createTrip(TripNewDto tripNewDtoData) {
        boolean uniqueUrlFound = false;
        String tempUrl = "";

        while (!uniqueUrlFound) {
            tempUrl = RandomString.get(8);
            if (!tripRepository.existsByTripUrl(tempUrl)) {
                uniqueUrlFound = true;
            }
        }

        return tripRepository.save(new Trip(tempUrl, tripNewDtoData.getName(),
                tripNewDtoData.getLocation(), getUser().getId())).tripToDto();
    }

    public Trip changePublicTrip(TripPublicDto tripPublicDto) throws AttributeNotFoundException {
        Trip tripEntity = tripRepository.findByTripUrlAndUserId(tripPublicDto.getUrl(), getUser().getId()).orElseThrow(AttributeNotFoundException::new);

        tripEntity.setPublic(tripPublicDto.isPublic());
        return tripRepository.save(tripEntity);
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
        notesRepository.deleteByTripUrl(url);
    }

    // private
    private User getUser() {
        return userRepository.findByUsername(securityService.findLoggedInUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with this username"));
    }
}
