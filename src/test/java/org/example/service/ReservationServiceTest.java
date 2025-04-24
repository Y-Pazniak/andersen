package org.example.service;

import org.example.model.*;
import org.example.repository.DataStorage;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

public class ReservationServiceTest {
    private DataStorage mockDataStorage;
    private ReservationService reservationService;

    @BeforeEach
    public void setUp() {
        mockDataStorage = Mockito.mock(DataStorage.class);
        reservationService = ReservationService.getInstance();
        reservationService.setDataStorage(mockDataStorage);
    }

    @AfterEach
    void tearDown() {
        reservationService.restoreDefaultStorage();
    }

    @Test
    void makeReservationSuccess() {
        Customer customer = new Customer();
        Workspace workspace = new Workspace(Type.OPEN_SPACE, 5, ReservationStatus.AVAILABLE);

        Mockito.when(mockDataStorage.getWorkspace(Mockito.anyInt())).thenReturn(workspace);
        reservationService.makeReservation(customer, 1, "today", "tomorrow");
        Mockito.verify(mockDataStorage).addReservation(Mockito.any(Reservation.class));
        Assertions.assertEquals(ReservationStatus.UNAVAILABLE, workspace.getStatus());
    }
}
