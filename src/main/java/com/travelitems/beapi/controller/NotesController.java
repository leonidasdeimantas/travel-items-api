package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Note;
import com.travelitems.beapi.service.NotesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;


@RequiredArgsConstructor
@RestController
@CrossOrigin
@RequestMapping("/notes")
public class NotesController {
    private final NotesService notesService;

    @GetMapping(value = "/all")
    public Iterable<Note> getAllNotes(@RequestParam String tripUrl) {
        try {
            return notesService.findNotes(tripUrl);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity addNotes(@RequestBody Note note) {
        try {
            notesService.addNote(note);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity deleteNote(@RequestParam String tripUrl, @RequestParam Long noteId){
        try {
            notesService.deleteNote(tripUrl, noteId);
        } catch (AttributeNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
