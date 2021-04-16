package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.service.TaskService;
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
    private final TaskService taskService;

    @GetMapping(value = "/all", params = "tripUrl")
    public Iterable<Task> getAllTripTasks(@RequestParam String tripUrl) {
        try {
            return taskService.findTasks(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public Iterable<Task> getAllTripTasksForAssingee(@RequestParam String tripUrl, @RequestParam Long assigneeId) {
        return taskService.findTasks(tripUrl, assigneeId);
    }

    @PostMapping
    public ResponseEntity addTask(@RequestBody Task task) {
        try {
            taskService.addTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity updateTask(@RequestBody Task task) {
        try {
            taskService.updateTask(task);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteTask(@RequestParam String tripUrl, @RequestParam Long taskId){
        try {
            taskService.deleteTask(tripUrl, taskId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
