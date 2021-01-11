package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Trip;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TripRepository extends CrudRepository<Trip, Long> {
    Optional<Trip> findByTripUrl(String url);
}
