package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TripRepository extends JpaRepository<Trip, Long> {
    Optional<Trip> findByTripUrl(String url);
    void deleteByTripUrl(String tripUrl);
}
