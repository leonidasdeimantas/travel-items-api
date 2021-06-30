package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Note;
import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.*;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.management.AttributeNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class NotesServiceTest {
    private static String TRIP_URL = "12345678";
    private static String USERNAME = "USER1";
    private static Long USER_ID = (long) 123;
    private User userStub;
    private Note noteStub;
    private Trip tripStub;

    @InjectMocks
    private NotesService notesService;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private SecurityServiceImpl securityService;

    @BeforeEach
    void setup() {
        userStub = new User(USERNAME, "testEmail", "testPassword");
        userStub.setId(USER_ID);
        noteStub = new Note("testNote", TRIP_URL);
        tripStub = new Trip(TRIP_URL, "testName", "testLocation", USER_ID);
    }

    @Test
    void shouldAddNote() throws AttributeNotFoundException {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(noteStub.getTripUrl())).thenReturn(Optional.of(tripStub));
        Mockito.when(notesRepository.save(noteStub)).thenReturn(noteStub);

        notesService.addNote(noteStub);

        Assertions.assertEquals(USERNAME, noteStub.getUserName());
        Assertions.assertNotNull(noteStub.getTime());
    }

    @Test
    void shouldAddNoteWithoutUsername() throws AttributeNotFoundException {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Mockito.when(tripRepository.findByTripUrl(noteStub.getTripUrl())).thenReturn(Optional.of(tripStub));
        tripStub.setPublic(true);
        Mockito.when(notesRepository.save(noteStub)).thenReturn(noteStub);

        notesService.addNote(noteStub);

        Assertions.assertNull(noteStub.getUserName());
        Assertions.assertNotNull(noteStub.getTime());
    }
}
