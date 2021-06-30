package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Assignee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<Assignee, Long> {
    Iterable<Assignee> findByTripUrlOrderByIdAsc(String tripUrl);
    void deleteByTripUrl(String tripUrl);
    boolean existsById(Long id);
}
