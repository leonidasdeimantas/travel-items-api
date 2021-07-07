package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.domain.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListRepository extends JpaRepository<List, Long> {
    Iterable<List> findByTripUrlOrderByIdAsc(String tripUrl);
    void deleteByTripUrl(String tripUrl);
    boolean existsById(Long id);
}
