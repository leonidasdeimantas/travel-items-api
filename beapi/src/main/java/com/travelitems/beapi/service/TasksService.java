package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;

@Service
@RequiredArgsConstructor
public class TasksService {
    private final TasksRepository tasksRepository;
    private final TripRepository tripRepository;

    public Tasks addTask(Tasks task) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(task.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return tasksRepository.save(task);
    }

    public Iterable<Tasks> findTasks(String url) {
        return tasksRepository.findByTripUrl(url);
    }

    public Iterable<Tasks> findTasks(String url, Long id) {
        return tasksRepository.findByTripUrlAndAssigneeId(url, id);
    }
}
