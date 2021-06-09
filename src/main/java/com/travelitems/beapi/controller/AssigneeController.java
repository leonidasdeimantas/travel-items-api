package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.service.AssigneeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/assignee")
public class AssigneeController {
    private final AssigneeService assigneeService;

    @GetMapping(value = "/all")
    public Iterable<Assignee> getAllAssignees(@RequestParam String tripUrl) {
        try {
            return assigneeService.findAssignees(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addAssignee(@RequestBody Assignee assignee) {
        try {
            assigneeService.addAssignee(assignee);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteAssignee(@RequestParam String tripUrl, @RequestParam Long assigneeId){
        try {
            assigneeService.deleteAssignee(tripUrl, assigneeId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
