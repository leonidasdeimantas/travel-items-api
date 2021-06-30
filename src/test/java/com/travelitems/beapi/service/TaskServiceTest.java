package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Task;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.TaskRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
import com.travelitems.beapi.security.services.SecurityService;
import com.travelitems.beapi.security.services.SecurityServiceImpl;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
	static final String TRIP_URL = "12345678";
	static final String USERNAME = "USER1";
	static final Long TASK_ID = (long) 123;
	static final Long USER_ID = (long) 321;
	static final Long USER_ID_PUBLIC = (long) 777;
	private Task taskStub;
	private User userStub;
	private Trip tripStub;

    @InjectMocks
    private TaskService taskService;

    @Mock
    private TaskRepository taskRepository;

	@Mock
	private TripRepository tripRepository;

	@Mock
	private UserRepository userRepository;

	@Mock
	private SecurityServiceImpl securityService;

    @Mock
    private Iterable<Task> iterableTaskEntity;

	@Mock
	private Task task;

    @BeforeEach
    void setup() {
        taskStub = new Task("testTask", "testPrice", (long) 999, TRIP_URL, true);
		taskStub.setId(TASK_ID);
		userStub = new User(USERNAME, "testEmail", "testPassword");
		userStub.setId(USER_ID);
		tripStub = new Trip(TRIP_URL, "testName", "testLocation", USER_ID);
    }

	@Test
	void shouldAddTask() throws AttributeNotFoundException {
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.of(tripStub));
		Mockito.when(taskRepository.save(taskStub)).thenReturn(taskStub);

		Assertions.assertEquals(taskStub, taskService.addTask(taskStub));
	}

	@Test
	void shouldAddTaskPublic() throws AttributeNotFoundException {
		userStub.setId(USER_ID_PUBLIC);
		tripStub.setPublic(true);

		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.of(tripStub));
		Mockito.when(taskRepository.save(taskStub)).thenReturn(taskStub);

		Assertions.assertEquals(taskStub, taskService.addTask(taskStub));
	}

    @Test
    void shouldThrowAddTask() {
        Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            taskService.addTask(taskStub);
        });
    }

	@Test
	void shouldThrowAddTaskPublic() throws AttributeNotFoundException {
		userStub.setId(USER_ID_PUBLIC);
		tripStub.setPublic(false);

		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.of(tripStub));

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.addTask(taskStub);
		});
	}

	@Test
	void shouldFindTasks() throws AttributeNotFoundException {
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(taskRepository.findByTripUrlOrderByIdAsc(TRIP_URL)).thenReturn(iterableTaskEntity);

		Assertions.assertEquals(iterableTaskEntity, taskService.findTasks(TRIP_URL));
	}

	@Test
	void shouldFindTasksPublic() throws AttributeNotFoundException {
		tripStub.setPublic(true);

		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(taskRepository.findByTripUrlOrderByIdAsc(TRIP_URL)).thenReturn(iterableTaskEntity);

		Assertions.assertEquals(iterableTaskEntity, taskService.findTasks(TRIP_URL));
	}

	@Test
	void shouldThrowFindTasks() {
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.findTasks(TRIP_URL);
		});
	}

	@Test
	void shouldThrowFindTasksPublic() {
		userStub.setId(USER_ID_PUBLIC);
		tripStub.setPublic(false);

		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(tripRepository.findByTripUrl(taskStub.getTripUrl())).thenReturn(Optional.of(tripStub));

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.findTasks(TRIP_URL);
		});
	}

    @Test
    void shouldUpdateTask() throws AttributeNotFoundException {
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));
        Mockito.when(taskRepository.save(task)).thenReturn(taskStub);

        Assertions.assertEquals(taskStub, taskService.updateTask(taskStub));

        Mockito.verify(task, Mockito.times(1)).setAssigneeId(taskStub.getAssigneeId());
        Mockito.verify(task, Mockito.times(1)).setTask(taskStub.getTask());
        Mockito.verify(task, Mockito.times(1)).setPrice(taskStub.getPrice());
        Mockito.verify(task, Mockito.times(1)).setCompleted(taskStub.isCompleted());
    }

	@Test
	void shouldThrowUpdateTask() {
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
		Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.empty());

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.updateTask(taskStub);
		});
	}

	@Test
	void shouldThrowUpdateTaskPublic() {
		userStub.setId(USER_ID_PUBLIC);
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.updateTask(taskStub);
		});
	}

    @Test
    void shouldDeleteTask() throws AttributeNotFoundException {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(taskRepository.findById(TASK_ID)).thenReturn(Optional.of(task));

        taskService.deleteTask(TRIP_URL, TASK_ID);

        Mockito.verify(taskRepository, Mockito.times(1)).deleteById(TASK_ID);
    }

    @Test
    void shouldThrowByTripDeleteTask() {
        Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.empty());

        Assertions.assertThrows(AttributeNotFoundException.class, () -> {
            taskService.deleteTask(TRIP_URL, TASK_ID);
        });

        Mockito.verify(taskRepository, Mockito.times(0)).deleteById(TASK_ID);
    }

    @Test
    void shouldThrowDeleteTaskPublic() {
		userStub.setId(USER_ID_PUBLIC);
		Mockito.when(tripRepository.findByTripUrl(TRIP_URL)).thenReturn(Optional.of(tripStub));
		Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
		Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));

		Assertions.assertThrows(AttributeNotFoundException.class, () -> {
			taskService.deleteTask(TRIP_URL, TASK_ID);
		});

		Mockito.verify(taskRepository, Mockito.times(0)).deleteById(TASK_ID);
    }
}
