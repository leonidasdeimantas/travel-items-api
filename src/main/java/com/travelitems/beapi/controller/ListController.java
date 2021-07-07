package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.domain.List;
import com.travelitems.beapi.service.AssigneeService;
import com.travelitems.beapi.service.ListService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/list")
public class ListController {
    private final ListService listService;

    @GetMapping(value = "/all")
    public Iterable<List> getAllAssignees(@RequestParam String tripUrl) {
        try {
            return listService.findLists(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addAssignee(@RequestBody List list) {
        try {
            listService.addList(list);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteAssignee(@RequestParam String tripUrl, @RequestParam Long listId){
        try {
            listService.deleteList(tripUrl, listId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
