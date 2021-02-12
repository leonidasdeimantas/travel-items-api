package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TasksRepository extends JpaRepository<TaskEntity, Long> {
    Iterable<TaskEntity> findByTripUrlAndAssigneeId(String tripUrl, Long assigneeId);
    Iterable<TaskEntity> findByTripUrl(String tripUrl);
    Iterable<TaskEntity> findByAssigneeId(Long assigneeId);
    void deleteByTripUrl(String tripUrl);
}
