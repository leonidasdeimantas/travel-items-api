package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<TaskEntity, Long> {
    Iterable<TaskEntity> findByTripUrlAndAssigneeIdOrderByIdAsc(String tripUrl, Long assigneeId);
    Iterable<TaskEntity> findByTripUrlOrderByIdAsc(String tripUrl);
    Iterable<TaskEntity> findByAssigneeIdOrderByIdAsc(Long assigneeId);
    void deleteByTripUrl(String tripUrl);
}
