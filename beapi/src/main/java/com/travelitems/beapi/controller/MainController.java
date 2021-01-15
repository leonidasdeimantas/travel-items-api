package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.*;
import com.travelitems.beapi.service.PeoplesService;
import com.travelitems.beapi.service.TasksService;
import com.travelitems.beapi.service.TripsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {
    private final TasksService tasksService;
    private final PeoplesService peoplesService;
    private final TripsService tripsService;

    @GetMapping(value = "/tasks", params = "tripUrl")
    public Iterable<Tasks> getAllTripTasks(@RequestParam String tripUrl) {
        return tasksService.findTasks(tripUrl);
    }

    @GetMapping(value = "/tasks", params = {"tripUrl", "assigneeId"})
    public Iterable<Tasks> getAllTripTasksForAssingee(@RequestParam String tripUrl, @RequestParam Long assigneeId) {
        return tasksService.findTasks(tripUrl, assigneeId);
    }

    @GetMapping(value = "/peoples")
    public Iterable<Peoples> getAllTripPeoples(@RequestParam String tripUrl) {
        return peoplesService.findAssignees(tripUrl);
    }

    @PostMapping(value = "/task")
    public Tasks addTask(@RequestBody Tasks task) {
        try {
            return tasksService.addTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/people")
    public Peoples addPeople(@RequestBody Peoples people) {
        try {
            return peoplesService.addAssignee(people);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/trip")
    public TripDto addTrip(@RequestBody TripNewDto tripNewDtoData) {
        return tripsService.createTrip(tripNewDtoData);
    }
}
