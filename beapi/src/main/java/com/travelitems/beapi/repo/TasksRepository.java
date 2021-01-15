package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Tasks;
import org.springframework.data.repository.CrudRepository;

public interface TasksRepository extends CrudRepository<Tasks, Long> {
    Iterable<Tasks> findByTripUrlAndAssigneeId(String tripUrl, Long assigneeId);
    Iterable<Tasks> findByTripUrl(String tripUrl);
}
