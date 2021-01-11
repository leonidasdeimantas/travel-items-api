package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.PeoplesRepository;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {
    private final TasksRepository tasksRepository;
    private final PeoplesRepository peoplesRepository;
    private final TripRepository tripRepository;

    @GetMapping(value = "/tasks")
    public Iterable<Tasks> getAllTripTasks(@RequestParam String tripUrl) {
        return tasksRepository.findAll();
    }

    @GetMapping(value = "/tasks")
    public Iterable<Tasks> getAllTripTasksAssignedToId(@RequestParam String tripUrl, @RequestParam Long assigneeId) {
        return tasksRepository.findAll();
    }

    @GetMapping(value = "/peoples")
    public Iterable<Peoples> getAllTripPeoples(@RequestParam String tripUrl) {
        return peoplesRepository.findAll();
    }

    @PostMapping(value = "/task")
    public Tasks addTask(@RequestBody Tasks task) {
        tasksRepository.save(task);
        return task;
    }

    @PostMapping(value = "/people")
    public Peoples addPeople(@RequestBody Peoples people) {
        peoplesRepository.save(people);
        return people;
    }

    @PostMapping(value = "/trip")
    public Trip addTrip(@RequestBody Trip trip) {
        tripRepository.save(trip);
        return trip;
    }
}
