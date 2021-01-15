package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Peoples;
import com.travelitems.beapi.domain.Tasks;
import org.springframework.data.repository.CrudRepository;

public interface PeoplesRepository extends CrudRepository<Peoples, Long> {
    Iterable<Peoples> findByTripUrl(String tripUrl);
}
