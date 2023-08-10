package code.medconnect.business;

import code.medconnect.domain.*;
import code.medconnect.domain.exception.VisitInTakenTimePeriodException;
import code.medconnect.domain.exception.VisitNotFoundException;
import code.medconnect.fixtures.DomainFixtures;
import code.medconnect.infrastructure.database.repository.VisitDAO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {

    @Mock
    VisitDAO visitDAO;

    @InjectMocks
    VisitService visitService;

    @Test
    void thatCancelingVisitWorksCorrectly() {
        //given
        Integer visitId = 5;
        Visit visit = DomainFixtures.someVisit();

        Mockito.when(visitDAO.findVisitById(visitId)).thenReturn(Optional.of(visit));

        //when
        visitService.cancelVisit(visitId);

        // Then
        InOrder inOrder = Mockito.inOrder(visitDAO);
        inOrder.verify(visitDAO).cancelVisit(visit);
        inOrder.verify(visitDAO).saveVisit(visit);
    }

    @Test
    void thatAddingNoteToVisitWorksCorrectly() {
        // Given
        Integer visitId = 5;
        Visit visit = DomainFixtures.someVisit();
        Note note = DomainFixtures.someNote();

        Mockito.when(visitDAO.findVisitById(visitId)).thenReturn(Optional.of(visit));

        // When
        visitService.addNoteToVisit(visitId, note);

        // Then
        assertEquals(note, visit.getNote());  // Make sure the note was set in the visit
        InOrder inOrder = Mockito.inOrder(visitDAO);
        inOrder.verify(visitDAO).saveVisit(visit);
    }

    @Test
    void thatAddingNoteToNonexistentVisitThrowsException() {
        // Given
        Integer visitId = 5;
        Note note = DomainFixtures.someNote();

        Mockito.when(visitDAO.findVisitById(visitId)).thenReturn(Optional.empty());

        // when, then
        assertThrows(VisitNotFoundException.class, () -> visitService.addNoteToVisit(visitId, note));
    }

    @Test
    void thatMakingValidVisitWorksCorrectly() {
        // Given
        Patient patient = DomainFixtures.somePatient();
        Doctor doctor = DomainFixtures.someDoctorWithAvailability();
        LocalDate day = LocalDate.of(2023, 9,9);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);

        Mockito.when(visitDAO.saveVisit(Mockito.any(Visit.class))).thenAnswer(a -> a.getArgument(0));

        // When
        Visit createdVisit = visitService.makeVisit(patient, doctor, day, startTime, endTime);

        // Then
        assertNotNull(createdVisit);
        assertEquals(patient, createdVisit.getPatient());
        assertEquals(doctor, createdVisit.getDoctor());
        assertEquals(day, createdVisit.getDay());
        assertEquals(startTime, createdVisit.getStartTime());
        assertEquals(endTime, createdVisit.getEndTime());
    }



    @Test
    void thatMakingVisitInTakenTimeThrowsException() {
        // Given
        Patient patient = DomainFixtures.somePatient();
        LocalDate day = LocalDate.of(2023, 9, 9);
        LocalTime startTime = LocalTime.of(9, 0);
        LocalTime endTime = LocalTime.of(10, 0);
        Doctor doctor = DomainFixtures.someDoctorWithAvailability();


        List<Visit> conflictingVisits = List.of(DomainFixtures.someVisit());


        Mockito.when(visitDAO.findConflictingVisits(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(conflictingVisits);

        // When, Then
        assertThrows(VisitInTakenTimePeriodException.class,
                () -> visitService.makeVisit(patient, doctor, day, startTime, endTime)
        );
    }

}
