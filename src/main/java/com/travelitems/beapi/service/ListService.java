package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.*;
import com.travelitems.beapi.repo.*;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ListService {
    private final ListRepository listRepository;
    private final TripRepository tripRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityService;

    // could be public
    public List addList(List list) throws AttributeNotFoundException {
        checkIfTripAvailable(list.getTripUrl());
        list.setTime(LocalDateTime.now());
        return listRepository.save(list);
    }

    public Iterable<List> findLists(String url) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        return listRepository.findByTripUrlOrderByIdAsc(url);
    }

    public void deleteList(String url, Long id) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        if (listRepository.existsById(id)) {
            taskRepository.deleteByTripUrlAndListId(url, id);
            listRepository.deleteById(id);
        } else {
            throw new AttributeNotFoundException();
        }
    }

    private void checkIfTripAvailable(String url) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(url).orElseThrow(AttributeNotFoundException::new);
        Optional<User> user = userRepository.findByUsername(securityService.findLoggedInUsername());

        if (trip.isPublic() || (user.isPresent() && trip.getUserId() == user.get().getId())) {
            return;
        } else {
            throw new AttributeNotFoundException();
        }
    }
}
