package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityService;

    // could be public
    public Task addTask(Task task) throws AttributeNotFoundException {
        checkIfTripAvailable(task.getTripUrl());
        task.setTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Iterable<Task> findTasks(String url) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        return taskRepository.findByTripUrlOrderByIdAsc(url);
    }

    public Iterable<Task> findTasks(String url, Long id) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        return taskRepository.findByTripUrlAndAssigneeIdOrderByIdAsc(url, id);
    }

    public Task updateTask(Task task) throws AttributeNotFoundException {
        checkIfTripAvailable(task.getTripUrl());
        Task taskEntity = taskRepository.findById(task.getId()).orElseThrow(AttributeNotFoundException::new);

        taskEntity.setAssigneeId(task.getAssigneeId());
        taskEntity.setTask(task.getTask());
        taskEntity.setPrice(task.getPrice());
        taskEntity.setCompleted(task.isCompleted());

        return taskRepository.save(taskEntity);
    }

    public void deleteTask(String url, Long id) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        if (taskRepository.findById(id).isEmpty()) {
            throw new AttributeNotFoundException();
        }

        taskRepository.deleteById(id);
    }

    // private
    private void checkIfTripAvailable(String url) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(url).orElseThrow(AttributeNotFoundException::new);
        Optional<User> user = userRepository.findByUsername(securityService.findLoggedInUsername());

        if (trip.isPublic() || (user.isPresent() && trip.getUserId() == user.get().getId())) {
            return;
        } else {
            throw new AttributeNotFoundException();
        }
    }
}
