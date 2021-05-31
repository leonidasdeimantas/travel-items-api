package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TripNewDto;
import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.AttributeNotFoundException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {
    static final String TRIP_URL = "12345678";

    @InjectMocks
    private TripService tripService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private Trip trip;

    @Mock
    private TripDto tripDto;

//    @Test
//    void shouldGetTrip() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
//        Mockito.when(trip.tripToDto()).thenReturn(tripDto);
//
//        Assertions.assertEquals(tripDto, tripService.getTrip(TRIP_URL));
//    }
//
//    @Test
//    void shouldThrowGetTrip() {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            tripService.getTrip(TRIP_URL);
//        });
//    }

//    @Test
//    void shouldCreateTrip() {
//        Mockito.when(tripRepository.save(Mockito.any())).thenReturn(trip);
//        Mockito.when(trip.tripToDto()).thenReturn(tripDto);
//
//        TripNewDto tripStub = new TripNewDto("test", "test");
//        Assertions.assertEquals(tripDto, tripService.createTrip(tripStub));
//    }
//
//    @Test
//    void shouldDeleteTrip() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
//
//        tripService.deleteTrip(TRIP_URL);
//
//        Mockito.verify(tripRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
//        Mockito.verify(taskRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
//        Mockito.verify(assigneeRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
//    }
//
//    @Test
//    void shouldNotDeleteTrip() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            tripService.deleteTrip(TRIP_URL);
//        });
//
//        Mockito.verify(tripRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
//        Mockito.verify(taskRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
//        Mockito.verify(assigneeRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
//    }
}
