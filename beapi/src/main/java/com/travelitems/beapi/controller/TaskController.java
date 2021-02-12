package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.TaskEntity;
import com.travelitems.beapi.service.TasksService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {
    private final TasksService tasksService;

    @GetMapping(value = "/all", params = "tripUrl")
    public Iterable<TaskEntity> getAllTripTasks(@RequestParam String tripUrl) {
        try {
            return tasksService.findTasks(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Iterable<TaskEntity> getAllTripTasksForAssingee(@RequestParam String tripUrl, @RequestParam Long assigneeId) {
        return tasksService.findTasks(tripUrl, assigneeId);
    }

    @PostMapping
    public TaskEntity addTask(@RequestBody TaskEntity task) {
        try {
            return tasksService.addTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping
    public TaskEntity updateTask(@RequestBody TaskEntity task) {
        try {
            return tasksService.updateTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteTask(@RequestParam String tripUrl, @RequestParam Long taskId){
        try {
            tasksService.deleteTask(tripUrl, taskId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(taskId, HttpStatus.OK);
    }
}