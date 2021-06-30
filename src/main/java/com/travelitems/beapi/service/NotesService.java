package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.*;
import com.travelitems.beapi.repo.*;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.management.AttributeNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class NotesService {
    private final NotesRepository notesRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final SecurityServiceImpl securityService;

    // could be public
    public Note addNote(Note note) throws AttributeNotFoundException {
        checkIfTripAvailable(note.getTripUrl());
        note.setTime(LocalDateTime.now());
        try {
            note.setUserName(getUser().getUsername());
        } catch (UsernameNotFoundException e) {}
        return notesRepository.save(note);
    }

    public Iterable<Note> findNotes(String url) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        return notesRepository.findByTripUrlOrderByIdAsc(url);
    }

    public void deleteNote(String url, Long id) throws AttributeNotFoundException {
        checkIfTripAvailable(url);
        if (notesRepository.existsById(id)) {
            notesRepository.deleteById(id);
        } else {
            throw new AttributeNotFoundException();
        }
    }

    private void checkIfTripAvailable(String url) throws AttributeNotFoundException {
        Trip trip = tripRepository.findByTripUrl(url).orElseThrow(AttributeNotFoundException::new);
        Optional<User> user = userRepository.findByUsername(securityService.findLoggedInUsername());

        if (trip.isPublic() || (user.isPresent() && trip.getUserId() == user.get().getId())) {
            return;
        } else {
            throw new AttributeNotFoundException();
        }
    }

    private User getUser() {
        return userRepository.findByUsername(securityService.findLoggedInUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found with this username"));
    }
}
