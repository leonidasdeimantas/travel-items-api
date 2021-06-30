package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.domain.dto.TripDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.dto.TripLocationDto;
import com.travelitems.beapi.domain.dto.TripNewDto;
import com.travelitems.beapi.domain.dto.TripPublicDto;
import com.travelitems.beapi.repo.*;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {
    static final String TRIP_URL = "12345678";
    static final String USERNAME = "USER1";
    static final Long USER_ID = (long) 321;
    static final Long USER_ID_PUBLIC = (long) 777;
    private User userStub;

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotesRepository notesRepository;

    @Mock
    private SecurityServiceImpl securityService;

    @Mock
    private Trip trip;

    @Mock
    private TripDto tripDto;

    @BeforeEach
    void setup() {
        userStub = new User(USERNAME, "testEmail", "testPassword");
        userStub.setId(USER_ID);
    }

    @Test
    void shouldGetTrip() throws AttributeNotFoundException {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.getUserId()).thenReturn(USER_ID);
        Mockito.when(trip.tripToDto()).thenReturn(tripDto);

        Assertions.assertEquals(tripDto, tripService.getTrip(TRIP_URL));
    }

    @Test
    void shouldThrowGetTripPublic() {
        userStub.setId(USER_ID_PUBLIC);
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.getUserId()).thenReturn(USER_ID);

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripService.getTrip(TRIP_URL);
        });
    }

    @Test
    void shouldNotThrowGetTripPublic() throws AttributeNotFoundException {
        userStub.setId(USER_ID_PUBLIC);
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.isPublic()).thenReturn(true);
        Mockito.when(trip.tripToDto()).thenReturn(tripDto);

        Assertions.assertEquals(tripDto, tripService.getTrip(TRIP_URL));
    }

    @Test
    void shouldThrowGetTrip() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripService.getTrip(TRIP_URL);
        });
    }

    @Test
    void shouldCreateTrip() {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.save(Mockito.any())).thenReturn(trip);
        Mockito.when(trip.tripToDto()).thenReturn(tripDto);

        TripNewDto tripStub = new TripNewDto("test", "test");
        Assertions.assertEquals(tripDto, tripService.createTrip(tripStub));
    }

    @Test
    void shouldDeleteTrip() throws AttributeNotFoundException {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrlAndUserId(TRIP_URL, USER_ID)).thenReturn(Optional.of(trip));

        tripService.deleteTrip(TRIP_URL);

        Mockito.verify(tripRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(taskRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(assigneeRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(notesRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
    }

    @Test
    void shouldNotDeleteTrip() {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrlAndUserId(TRIP_URL, USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripService.deleteTrip(TRIP_URL);
        });

        Mockito.verify(tripRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(taskRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(assigneeRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(notesRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
    }

    @Test
    void shouldChangeTripLocation() throws AttributeNotFoundException {
        TripLocationDto tripLocationDtoStub = new TripLocationDto(TRIP_URL, "testLocation");

        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.getUserId()).thenReturn(USER_ID);
        Mockito.when(tripRepository.save(Mockito.any())).thenReturn(trip);

        Assertions.assertEquals(trip, tripService.changeTripLocation(tripLocationDtoStub));

        Mockito.verify(trip, Mockito.times(1)).setLocation(tripLocationDtoStub.getLocation());
    }

    @Test
    void shouldThrowChangeTripLocation() {
        TripLocationDto tripLocationDtoStub = new TripLocationDto(TRIP_URL, "testLocation");

        userStub.setId(USER_ID_PUBLIC);
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.getUserId()).thenReturn(USER_ID);

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripService.changeTripLocation(tripLocationDtoStub);
        });

        Mockito.verify(trip, Mockito.times(0)).setLocation(tripLocationDtoStub.getLocation());
    }

    @Test
    void shouldChangePublicTrip() throws AttributeNotFoundException {
        TripPublicDto tripPublicDtoStub = new TripPublicDto(TRIP_URL, true);

        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrlAndUserId(TRIP_URL, USER_ID)).thenReturn(Optional.of(trip));
        Mockito.when(tripRepository.save(Mockito.any())).thenReturn(trip);

        Assertions.assertEquals(trip, tripService.changePublicTrip(tripPublicDtoStub));

        Mockito.verify(trip, Mockito.times(1)).setPublic(tripPublicDtoStub.isPublic());
    }

    @Test
    void shouldNotChangePublicTrip() {
        TripPublicDto tripPublicDtoStub = new TripPublicDto(TRIP_URL, true);

        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrlAndUserId(TRIP_URL, USER_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripService.changePublicTrip(tripPublicDtoStub);
        });

        Mockito.verify(trip, Mockito.times(0)).setPublic(tripPublicDtoStub.isPublic());
    }

    @Test
    void shouldGetAllTrips() {
        List<TripDto> tripDtoListStub = new ArrayList<>();
        tripDtoListStub.add(new TripDto("1", "1", "1", false));
        tripDtoListStub.add(new TripDto("2", "2", "2", true));
        tripDtoListStub.add(new TripDto("3", "3", "3", false));

        List<Trip> tripListStub = new ArrayList<>();
        tripListStub.add(new Trip("1", "1", "1", USER_ID));
        tripListStub.add(new Trip("2", "2", "2", USER_ID));
        tripListStub.add(new Trip("3", "3", "3", USER_ID));
        tripListStub.get(1).setPublic(true);

        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByUserId(USER_ID)).thenReturn(tripListStub);

        Assertions.assertEquals(tripDtoListStub, tripService.getAllTrips());
    }

    @Test
    void shouldNotGetUser() {
        userStub.setId(USER_ID_PUBLIC);
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.empty());
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
        Mockito.when(trip.getUserId()).thenReturn(USER_ID);

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            tripService.getTrip(TRIP_URL);
        });
    }
}
