package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final TripRepository tripRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityService;

    private void checkIfTripAvailable(String url) throws AttributeNotFoundException {
        Optional<User> user = userRepository.findByUsername(securityService.findLoggedInUsername());

        if (user.isPresent() && tripRepository.findByTripUrlAndUserId(url, user.get().getId()).isPresent()) {
            throw new AttributeNotFoundException();
        }
    }

    public Assignee addAssignee(Assignee assignee) throws AttributeNotFoundException {
        checkIfTripAvailable(assignee.getTripUrl());
        assignee.setTime(LocalDateTime.now());
        return assigneeRepository.save(assignee);
    }

    public Iterable<Assignee> findAssignees(String url) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        return assigneeRepository.findByTripUrlOrderByIdAsc(url);
    }

    public void deleteAssignee(String url, Long id) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        if (!assigneeRepository.findById(id).isPresent()) {
            throw new AttributeNotFoundException();
        }
        Iterable<Task> tasks = taskRepository.findByAssigneeIdOrderByIdAsc(id);
        for (Task task : tasks) {
            task.setAssigneeId(null);
            taskRepository.save(task);
        }
        assigneeRepository.deleteById(id);
    }
}
