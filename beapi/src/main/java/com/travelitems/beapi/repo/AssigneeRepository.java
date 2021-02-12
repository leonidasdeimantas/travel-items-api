package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.AssigneeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssigneeRepository extends JpaRepository<AssigneeEntity, Long> {
    Iterable<AssigneeEntity> findByTripUrl(String tripUrl);
    void deleteByTripUrl(String tripUrl);
}
