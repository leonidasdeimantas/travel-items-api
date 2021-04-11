package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TaskService {
    private final TaskRepository taskRepository;
    private final TripRepository tripRepository;

    public Task addTask(Task task) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(task.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        task.setTime(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Iterable<Task> findTasks(String url) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return taskRepository.findByTripUrlOrderByIdAsc(url);
    }

    public Iterable<Task> findTasks(String url, Long id) {
        return taskRepository.findByTripUrlAndAssigneeIdOrderByIdAsc(url, id);
    }

    public Task updateTask(Task task) throws AttributeNotFoundException {
        Task taskEntity = taskRepository.findById(task.getId()).orElseThrow(AttributeNotFoundException::new);

        taskEntity.setAssigneeId(task.getAssigneeId());
        taskEntity.setTask(task.getTask());
        taskEntity.setPrice(task.getPrice());
        taskEntity.setCompleted(task.isCompleted());

        return taskRepository.save(taskEntity);
    }

    public void deleteTask(String url, Long id) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent() || !taskRepository.findById(id).isPresent()) {
            throw new AttributeNotFoundException();
        }
        taskRepository.deleteById(id);
    }
}
