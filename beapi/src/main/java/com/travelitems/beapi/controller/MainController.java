package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.*;
import com.travelitems.beapi.service.PeoplesService;
import com.travelitems.beapi.service.TasksService;
import com.travelitems.beapi.service.TripsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/")
public class MainController {
    private final TasksService tasksService;
    private final PeoplesService peoplesService;
    private final TripsService tripsService;

    @GetMapping(value = "/tasks", params = "tripUrl")
    public Iterable<Tasks> getAllTripTasks(@RequestParam String tripUrl) {
        try {
            return tasksService.findTasks(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/tasks", params = {"tripUrl", "assigneeId"})
    public Iterable<Tasks> getAllTripTasksForAssingee(@RequestParam String tripUrl, @RequestParam Long assigneeId) {
        return tasksService.findTasks(tripUrl, assigneeId);
    }

    @GetMapping(value = "/peoples")
    public Iterable<Peoples> getAllTripPeoples(@RequestParam String tripUrl) {
        return peoplesService.findAssignees(tripUrl);
    }

    @GetMapping(value = "/trip")
    public TripDto getTripExist(@RequestParam String tripUrl) {
        try {
            return tripsService.checkTrip(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value = "/task")
    public Tasks addTask(@RequestBody Tasks task) {
        try {
            return tasksService.addTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/task")
    public Tasks updateTask(@RequestBody Tasks task) {
        try {
            return tasksService.updateTask(task);
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

    @DeleteMapping(value = "/task", params = {"tripUrl", "taskId"})
    public ResponseEntity<Long> deleteTask(@RequestParam String tripUrl, @RequestParam Long taskId){
        try {
            tasksService.deleteTask(tripUrl, taskId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }
}
