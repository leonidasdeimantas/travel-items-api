package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByTripUrl(String url);
    Optional<Trip> findByTripUrlAndUserId(String url, Long userId);
    Iterable<Trip> findByUserId(Long userId);
    void deleteByTripUrl(String tripUrl);
    boolean existsByTripUrl(String tripUrl);
}
