package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class TasksService {
    private final TasksRepository tasksRepository;
    private final TripRepository tripRepository;

    public Tasks addTask(Tasks task) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(task.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return tasksRepository.save(task);
    }

    public Iterable<Tasks> findTasks(String url) {
        return tasksRepository.findByTripUrl(url);
    }

    public Iterable<Tasks> findTasks(String url, Long id) {
        return tasksRepository.findByTripUrlAndAssigneeId(url, id);
    }

    public Tasks updateTask(Tasks task) throws AttributeNotFoundException {
        Tasks taskEntity = tasksRepository.findById(task.getId()).orElseThrow(AttributeNotFoundException::new);

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
