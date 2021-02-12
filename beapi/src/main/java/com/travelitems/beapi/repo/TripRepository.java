package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.TripEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<TripEntity, Long> {
    Optional<TripEntity> findByTripUrl(String url);
    void deleteByTripUrl(String tripUrl);
}
