package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TaskEntity;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TasksService {
    private final TasksRepository tasksRepository;
    private final TripRepository tripRepository;

    public TaskEntity addTask(TaskEntity task) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(task.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        task.setTime(LocalDateTime.now());
        return tasksRepository.save(task);
    }

    public Iterable<TaskEntity> findTasks(String url) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return tasksRepository.findByTripUrl(url);
    }

    public Iterable<TaskEntity> findTasks(String url, Long id) {
        return tasksRepository.findByTripUrlAndAssigneeId(url, id);
    }

    public TaskEntity updateTask(TaskEntity task) throws AttributeNotFoundException {
        TaskEntity taskEntity = tasksRepository.findById(task.getId()).orElseThrow(AttributeNotFoundException::new);

        taskEntity.setAssigneeId(task.getAssigneeId());
        taskEntity.setTask(task.getTask());
        taskEntity.setPrice(task.getPrice());
        taskEntity.setCompleted(task.isCompleted());

        return tasksRepository.save(taskEntity);
    }

    public void deleteTask(String url, Long id) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent() || !tasksRepository.findById(id).isPresent()) {
            throw new AttributeNotFoundException();
        }
        tasksRepository.deleteById(id);
    }
}
