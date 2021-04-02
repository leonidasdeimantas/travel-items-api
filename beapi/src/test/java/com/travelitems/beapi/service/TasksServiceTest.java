package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.TaskEntity;
import com.travelitems.beapi.domain.TripEntity;
import com.travelitems.beapi.repo.TasksRepository;
import com.travelitems.beapi.repo.TripRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.management.AttributeNotFoundException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TasksServiceTest {
    static final String TRIP_URL = "12345678";
    static final Long TASK_ID = (long) 123;

    @InjectMocks
    private TasksService tasksService;

    @Mock
    private TasksRepository tasksRepository;

    @Mock
    private TripRepository tripRepository;

    @Mock
    private TripEntity tripEntity;

    @Mock
    private TaskEntity taskEntity;

    @Mock
    private Iterable<TaskEntity> iterableTaskEntity;

    private TaskEntity taskEntityStub;

    @BeforeEach
    void setup() {
        taskEntityStub = new TaskEntity("testTask", "testPrice", (long) 999, "testUrl", true);
        taskEntityStub.setId(TASK_ID);
    }

    @Test
    void shouldAddTask() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(taskEntityStub.getTripUrl())).thenReturn(Optional.of(tripEntity));
        Mockito.when(tasksRepository.save(taskEntityStub)).thenReturn(taskEntity);

        Assertions.assertEquals(taskEntity, tasksService.addTask(taskEntityStub));
    }

    @Test
    void shouldThrowAddTask() {
        Mockito.when(tripRepository.findByTripUrl(taskEntityStub.getTripUrl())).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tasksService.addTask(taskEntityStub);
        });
    }

    @Test
    void shouldFindTasks() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripEntity));
        Mockito.when(tasksRepository.findByTripUrl(TRIP_URL)).thenReturn(iterableTaskEntity);

        Assertions.assertEquals(iterableTaskEntity, tasksService.findTasks(TRIP_URL));
    }

    @Test
    void shouldThrowFindTasks() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tasksService.findTasks(TRIP_URL);
        });
    }

    @Test
    void shouldUpdateTask() throws AttributeNotFoundException {
        Mockito.when(tasksRepository.findById(TASK_ID)).thenReturn(Optional.of(taskEntity));
        Mockito.when(tasksRepository.save(taskEntity)).thenReturn(taskEntity);

        Assertions.assertEquals(taskEntity, tasksService.updateTask(taskEntityStub));

        Mockito.verify(taskEntity, Mockito.times(1)).setAssigneeId(taskEntityStub.getAssigneeId());
        Mockito.verify(taskEntity, Mockito.times(1)).setTask(taskEntityStub.getTask());
        Mockito.verify(taskEntity, Mockito.times(1)).setPrice(taskEntityStub.getPrice());
        Mockito.verify(taskEntity, Mockito.times(1)).setCompleted(taskEntityStub.isCompleted());
    }

    @Test
    void shouldThrowUpdateTask() {
        Mockito.when(tasksRepository.findById(TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tasksService.updateTask(taskEntityStub);
        });
    }

    @Test
    void shouldThrowDeleteTask() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripEntity));
        Mockito.when(tasksRepository.findById(TASK_ID)).thenReturn(Optional.of(taskEntity));

        tasksService.deleteTask(TRIP_URL, TASK_ID);

        Mockito.verify(tasksRepository, Mockito.times(1)).deleteById(TASK_ID);
    }

    @Test
    void shouldThrowByTripDeleteTask() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tasksService.deleteTask(TRIP_URL, TASK_ID);
        });

        Mockito.verify(tasksRepository, Mockito.times(0)).deleteById(TASK_ID);
    }

    @Test
    void shouldThrowByTaskDeleteTask() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripEntity));
        Mockito.when(tasksRepository.findById(TASK_ID)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            tasksService.deleteTask(TRIP_URL, TASK_ID);
        });

        Mockito.verify(tasksRepository, Mockito.times(0)).deleteById(TASK_ID);
    }
}
