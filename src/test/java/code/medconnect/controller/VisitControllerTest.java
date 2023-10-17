package code.medconnect.controller;

import code.medconnect.api.controller.VisitController;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Visit;
import code.medconnect.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class VisitControllerTest {

    @InjectMocks
    private VisitController visitController;

    @Mock
    private VisitService visitService;


    @Test
    void confirmVisitTest() {
        //given
        Integer patientId = 1;
        Integer doctorId = 1;
        LocalDate day = LocalDate.of(2023, 5, 5);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(12, 30);
        String selectedTimeSlot = "12:00-12:30";
        Visit visit = DomainFixtures.someVisit();

        Mockito.when(visitService.makeVisit(eq(patientId), eq(doctorId), eq(day), eq(startTime), eq(endTime)))
                .thenReturn(visit);

        //when
        String viewName = visitController.confirmVisit(patientId, doctorId, day, selectedTimeSlot);

        //then
        Assertions.assertEquals("redirect:/patient", viewName);

        Mockito.verify(visitService, Mockito.times(1))
                .makeVisit(any(), any(), any(), any(), any());
    }

    @Test
    void cancelVisitTest() {
        //given
        Integer visitId = 1;
        Mockito.doNothing().when(visitService).cancelVisit(visitId);
        //when
        visitController.cancelVisit(visitId);
        //then
        Mockito.verify(visitService, Mockito.times(1)).cancelVisit(visitId);
    }
}
