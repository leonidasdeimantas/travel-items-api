package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
    Iterable<Task> findByTripUrlAndAssigneeIdOrderByIdAsc(String tripUrl, Long assigneeId);
    Iterable<Task> findByTripUrlAndListIdOrderByIdAsc(String tripUrl, Long listId);
    Iterable<Task> findByTripUrlOrderByIdAsc(String tripUrl);
    Iterable<Task> findByAssigneeIdOrderByIdAsc(Long assigneeId);
    void deleteByTripUrl(String tripUrl);
    void deleteByTripUrlAndListId(String tripUrl, Long listId);
}
