package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import com.travelitems.beapi.repo.PeoplesRepository;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;

@Service
@RequiredArgsConstructor
public class PeoplesService {
    private final PeoplesRepository peoplesRepository;
    private final TripRepository tripRepository;
    private final TasksRepository tasksRepository;

    public Peoples addAssignee(Peoples assignee) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(assignee.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return peoplesRepository.save(assignee);
    }

    public Iterable<Peoples> findAssignees(String url) {
        return peoplesRepository.findByTripUrl(url);
    }

    public void deleteAssignee(String url, Long id) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent() || !peoplesRepository.findById(id).isPresent()) {
            throw new AttributeNotFoundException();
        }
        Iterable<Tasks> tasks = tasksRepository.findByAssigneeId(id);
        for (Tasks task : tasks) {
            task.setAssigneeId(null);
            tasksRepository.save(task);
        }
        peoplesRepository.deleteById(id);
    }
}
