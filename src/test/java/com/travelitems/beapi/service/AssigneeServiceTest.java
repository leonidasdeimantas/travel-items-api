package com.travelitems.beapi.service;

import com.travelitems.beapi.domain.Assignee;
import com.travelitems.beapi.domain.Trip;
import com.travelitems.beapi.domain.User;
import com.travelitems.beapi.repo.AssigneeRepository;
import com.travelitems.beapi.repo.TripRepository;
import com.travelitems.beapi.repo.UserRepository;
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

import javax.management.AttributeNotFoundException;
import java.util.Optional;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class AssigneeServiceTest {
    private static String TRIP_URL = "12345678";
    private static String USERNAME = "USER1";
    private static Long USER_ID = (long) 123;
    private User userStub;
    private Assignee assigneeStub;
    private Trip tripStub;

    @InjectMocks
    private AssigneeService assigneeService;

    @Mock
    private AssigneeRepository assigneeRepository;

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
        assigneeStub = new Assignee("testName", TRIP_URL);
        tripStub = new Trip(TRIP_URL, "testName", "testLocation", USER_ID);
    }

    @Test
    void shouldAddAssignee() throws AttributeNotFoundException {
        Mockito.when(securityService.findLoggedInUsername()).thenReturn(USERNAME);
        Mockito.when(userRepository.findByUsername(USERNAME)).thenReturn(Optional.of(userStub));
        Mockito.when(tripRepository.findByTripUrl(assigneeStub.getTripUrl())).thenReturn(Optional.of(tripStub));
        Mockito.when(assigneeRepository.save(assigneeStub)).thenReturn(assigneeStub);

        assigneeService.addAssignee(assigneeStub);

        Assertions.assertNotNull(assigneeStub.getTime());
    }
}
