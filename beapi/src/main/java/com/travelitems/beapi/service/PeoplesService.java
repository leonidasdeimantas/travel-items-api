package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.repo.PeoplesRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;

@Service
@RequiredArgsConstructor
public class PeoplesService {
    private final PeoplesRepository peoplesRepository;
    private final TripRepository tripRepository;

    public Peoples addAssignee(Peoples assignee) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(assignee.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return peoplesRepository.save(assignee);
    }

    public Iterable<Peoples> findAssignees(String url) {
        return peoplesRepository.findByTripUrl(url);
    }
}
