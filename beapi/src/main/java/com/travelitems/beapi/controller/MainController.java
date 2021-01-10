package com.travelitems.beapi.controller;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import com.travelitems.beapi.repo.PeoplesRepository;
import com.travelitems.beapi.repo.TasksRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/")
public class MainController {
    private final TasksRepository tasksRepository;
    private final PeoplesRepository peoplesRepository;

    @GetMapping(value = "/tasks")
    public Iterable<Tasks> getAllTasks() {
        return tasksRepository.findAll();
    }

    @GetMapping(value = "/peoples")
    public Iterable<Peoples> getAllPeoples() { return peoplesRepository.findAll(); }
}
