package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.repo.TaskRepository;
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
public class TaskServiceTest {
//    static final String TRIP_URL = "12345678";
//    static final Long TASK_ID = (long) 123;
//
//    @InjectMocks
//    private TaskService taskService;
//
//    @Mock
//    private TaskRepository taskRepository;
//
//    @Mock
//    private TripRepository tripRepository;
//
//    @Mock
//    private Trip trip;
//
//    @Mock
//    private Task task;
//
//    @Mock
//    private Iterable<Task> iterableTaskEntity;
//
//    private Task taskStub;
//
//    @BeforeEach
//    void setup() {
//        taskStub = new Task("testTask", "testPrice", (long) 999, "testUrl", true);
//        taskStub.setId(TASK_ID);
//    }
//
//    @Test
//    void shouldAddTask() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.of(trip));
//        Mockito.when(taskRepository.save(taskStub)).thenReturn(task);
//
//        Assertions.assertEquals(task, taskService.addTask(taskStub));
//    }
//
//    @Test
//    void shouldThrowAddTask() {
//        Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            taskService.addTask(taskStub);
//        });
//    }
//
//    @Test
//    void shouldFindTasks() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
//        Mockito.when(taskRepository.findByTripUrlOrderByIdAsc(TRIP_URL)).thenReturn(iterableTaskEntity);
//
//        Assertions.assertEquals(iterableTaskEntity, taskService.findTasks(TRIP_URL));
//    }
//
//    @Test
//    void shouldThrowFindTasks() {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            taskService.findTasks(TRIP_URL);
//        });
//    }
//
//    @Test
//    void shouldUpdateTask() throws AttributeNotFoundException {
//        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
//        Mockito.when(taskRepository.save(task)).thenReturn(task);
//
//        Assertions.assertEquals(task, taskService.updateTask(taskStub));
//
//        Mockito.verify(task, Mockito.times(1)).setAssigneeId(taskStub.getAssigneeId());
//        Mockito.verify(task, Mockito.times(1)).setTask(taskStub.getTask());
//        Mockito.verify(task, Mockito.times(1)).setPrice(taskStub.getPrice());
//        Mockito.verify(task, Mockito.times(1)).setCompleted(taskStub.isCompleted());
//    }
//
//    @Test
//    void shouldThrowUpdateTask() {
//        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            taskService.updateTask(taskStub);
//        });
//    }
//
//    @Test
//    void shouldThrowDeleteTask() throws AttributeNotFoundException {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
//        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
//
//        taskService.deleteTask(TRIP_URL, TASK_ID);
//
//        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(TASK_ID);
//    }
//
//    @Test
//    void shouldThrowByTripDeleteTask() {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            taskService.deleteTask(TRIP_URL, TASK_ID);
//        });
//
//        Mockito.verify(taskRepository, Mockito.times(0)).deleteById(TASK_ID);
//    }
//
//    @Test
//    void shouldThrowByTaskDeleteTask() {
//        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(trip));
//        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());
//
//        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
//            taskService.deleteTask(TRIP_URL, TASK_ID);
//        });
//
//        Mockito.verify(taskRepository, Mockito.times(0)).deleteById(TASK_ID);
//    }
}
