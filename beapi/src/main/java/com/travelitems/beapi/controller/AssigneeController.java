package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.AssigneeEntity;
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
    public Iterable<AssigneeEntity> getAllAssignees(@RequestParam String tripUrl) {
        return assigneeService.findAssignees(tripUrl);
    }

    @PostMapping
    public AssigneeEntity addAssignee(@RequestBody AssigneeEntity assignee) {
        try {
            return assigneeService.addAssignee(assignee);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping
    public ResponseEntity<Long> deleteAssignee(@RequestParam String tripUrl, @RequestParam Long assigneeId){
        try {
            assigneeService.deleteAssignee(tripUrl, assigneeId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(assigneeId, HttpStatus.OK);
    }
}
