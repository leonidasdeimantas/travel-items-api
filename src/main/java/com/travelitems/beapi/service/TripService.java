package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.*;
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

    // could be public
    public TripDto getTrip(String tripUrl) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(tripUrl).orElseThrow(() -> new AttributeNotFoundException());
        if (trip.isPublic() || trip.getUserId() == getUser().getId()) {
            return trip.tripToDto();
        } else {
            throw new AttributeNotFoundException();
        }
    }

    // only with auth
    public TripDto createTrip(TripNewDto tripNewDtoData) throws AttributeNotFoundException {
        return tripRepository.save(new Trip(RandomString.get(8), tripNewDtoData.getName(),
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
    }

    // private
    private User getUser() {
        return userRepository.findByUsername(securityService.findLoggedInUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with this username"));
    }
}
