package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.repo.AssigneeRepository;
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
public class AssigneeService {
    private final AssigneeRepository assigneeRepository;
    private final TripRepository tripRepository;
    private final TaskRepository taskRepository;

    public Assignee addAssignee(Assignee assignee) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(assignee.getTripUrl()).isPresent()) {
            throw new AttributeNotFoundException();
        }
        assignee.setTime(LocalDateTime.now());
        return assigneeRepository.save(assignee);
    }

    public Iterable<Assignee> findAssignees(String url) {
        return assigneeRepository.findByTripUrlOrderByIdAsc(url);
    }

    public void deleteAssignee(String url, Long id) throws AttributeNotFoundException {
        if (!tripRepository.findByTripUrl(url).isPresent() || !assigneeRepository.findById(id).isPresent()) {
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
