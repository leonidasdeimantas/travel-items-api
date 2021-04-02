package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.NewTripDto;
import com.travelitems.beapi.domain.TripDto;
import com.travelitems.beapi.domain.TripEntity;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TasksRepository;
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
public class TripsServiceTest {
    static final String TRIP_URL = "12345678";

    @InjectMocks
    private TripsService tripsService;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private AssigneeRepository assigneeRepository;

    @Mock
    private TripEntity tripEntity;

    @Mock
    private TripDto tripDto;

    @Test
    void shouldGetTrip() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripEntity));
        Mockito.when(tripEntity.tripToDto()).thenReturn(tripDto);

        Assertions.assertEquals(tripsService.getTrip(TRIP_URL), tripDto);
    }

    @Test
    void shouldThrowGetTrip() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripsService.getTrip(TRIP_URL);
        });
    }

    @Test
    void shouldCreateTrip() {
        Mockito.when(tripRepository.save(Mockito.any())).thenReturn(tripEntity);
        Mockito.when(tripEntity.tripToDto()).thenReturn(tripDto);

        NewTripDto tripStub = new NewTripDto("test", "test");
        Assertions.assertEquals(tripsService.createTrip(tripStub), tripDto);
    }

    @Test
    void shouldDeleteTrip() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripEntity));

        tripsService.deleteTrip(TRIP_URL);

        Mockito.verify(tripRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(tasksRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(assigneeRepository, Mockito.times(1)).deleteByTripUrl(TRIP_URL);
    }

    @Test
    void shouldNotDeleteTrip() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tripsService.deleteTrip(TRIP_URL);
        });

        Mockito.verify(tripRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(tasksRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
        Mockito.verify(assigneeRepository, Mockito.times(0)).deleteByTripUrl(TRIP_URL);
    }
}
