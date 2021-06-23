package com.travelitems.beapi.repo;

import com.travelitems.beapi.domain.Note;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotesRepository extends JpaRepository<Note, Long> {
    Iterable<Note> findByTripUrlOrderByIdAsc(String tripUrl);
    void deleteByTripUrl(String tripUrl);
}
