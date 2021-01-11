package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Tasks;
import org.springframework.data.repository.CrudRepository;

public interface TasksRepository extends CrudRepository<Tasks, Long> {
    Iterable<Tasks> findByTripIdAndPeopleId(Long tripId, Long peopleId);
    Iterable<Tasks> findByTripId(Long tripId);
}
