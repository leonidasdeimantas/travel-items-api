package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.AssigneeEntity;
import com.travelitems.beapi.domain.TaskEntity;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final TripRepository tripRepository;
    private final TasksRepository tasksRepository;

    public AssigneeEntity addAssignee(AssigneeEntity assignee) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(assignee.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        return assigneeRepository.save(assignee);
    }

    public Iterable<AssigneeEntity> findAssignees(String url) {
        return assigneeRepository.findByTripUrl(url);
    }

    public void deleteAssignee(String url, Long id) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent() || !assigneeRepository.findById(id).isPresent()) {
            throw new AttributeNotFoundException();
        }
        Iterable<TaskEntity> tasks = tasksRepository.findByAssigneeId(id);
        for (TaskEntity task : tasks) {
            task.setAssigneeId(null);
            tasksRepository.save(task);
        }
        assigneeRepository.deleteById(id);
    }
}
