package code.medconnect.business;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.NoteDAO;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.*;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.domain.exception.VisitInTakenTimePeriodException;
import code.medconnect.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class VisitServiceTest {


    @Mock
    private VisitDAO visitDAO;
    @Mock
    private PatientDAO patientDAO;
    @Mock
    private DoctorDAO doctorDAO;
    @Mock
    private NoteDAO noteDAO;

    @InjectMocks
    private VisitService visitService;

    @Test
    void thatCancelingVisitInvokesMethodsInOrder() {
        //given
        Integer visitId = 5;
        Visit visit = DomainFixtures.someVisit();

        Mockito.when(visitDAO.findVisitById(visitId)).thenReturn(Optional.of(visit));

        //when
        visitService.cancelVisit(visitId);

        // Then
        InOrder inOrder = Mockito.inOrder(visitDAO);
        inOrder.verify(visitDAO).findVisitById(visitId);
        inOrder.verify(visitDAO).cancelVisit(visit);
    }

    @Test
    void thatAddingNoteInvokesMethodsInOrder() {
        // Given
        Integer visitId = 5;
        Visit visit = DomainFixtures.someVisit();
        String noteContent = "bla bla bla";

        Mockito.when(visitDAO.findVisitById(visitId)).thenReturn(Optional.of(visit));

        // When
        visitService.addNoteToVisit(visitId, noteContent);

        // Then
        InOrder inOrder = Mockito.inOrder(visitDAO, noteDAO);
        inOrder.verify(visitDAO).findVisitById(visitId);
        inOrder.verify(noteDAO).saveNote(Mockito.any(Note.class));

    }

    @Test
    void thatAddingNoteToNonexistentVisitThrowsException() {
        //given
        Integer nonexistentVisitId = 10;
        String noteContent = "This shouldn't  work";

        Mockito.when(visitDAO.findVisitById(nonexistentVisitId)).thenReturn(Optional.empty());

        //when, then
        Assertions.assertThrows(NotFoundException.class, () -> {
            visitService.addNoteToVisit(nonexistentVisitId, noteContent);
        });
        Mockito.verify(visitDAO).findVisitById(nonexistentVisitId);
        Mockito.verifyNoMoreInteractions(visitDAO, noteDAO);
    }

    @Test
    void thatGettingPatientsVisitsWorksCorrectly() {
        // Given
        Patient patient = DomainFixtures.somePatient();
        String pesel = patient.getPesel();
        patient.setPatientId(1);
        List<Visit> visits = new ArrayList<>();

        Mockito.when(patientDAO.findByPesel(pesel)).thenReturn(Optional.of(patient));
        Mockito.when(visitDAO.findByPatientId(patient.getPatientId())).thenReturn(visits);

        //when
        List<Visit> resultVisits = visitService.getPatientsVisits(pesel);

        //then
        Assertions.assertEquals(visits, resultVisits);
        Mockito.verify(patientDAO).findByPesel(pesel);
        Mockito.verify(visitDAO).findByPatientId(patient.getPatientId());
    }

    @Test
    void thatMakingValidVisitWorksCorrectly() {
        //given
        Integer patientId = 1;
        Integer doctorId = 2;
        LocalDate day = LocalDate.of(2023, 10, 15);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(12, 30);

        Doctor doctor = DomainFixtures.someDoctor1()
                .withAvailabilities(Set.of(DomainFixtures.someDoctorAvailability()));
        Mockito.when(doctorDAO.findById(doctorId)).thenReturn(doctor);
        Mockito.when(visitDAO.saveVisit(Mockito.any(Visit.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        //when
        Visit createdVisit = visitService.makeVisit(patientId, doctorId, day, startTime, endTime);

        //then
        Assertions.assertNotNull(createdVisit);
        Assertions.assertEquals(patientId, createdVisit.getPatientId());
        Assertions.assertEquals(doctorId, createdVisit.getDoctorId());
        Assertions.assertEquals(day, createdVisit.getDay());
        Assertions.assertEquals(startTime, createdVisit.getStartTime());
        Assertions.assertEquals(endTime, createdVisit.getEndTime());

        Mockito.verify(doctorDAO).findById(doctorId);
        Mockito.verify(visitDAO).saveVisit(Mockito.any(Visit.class));
    }



    @Test
    void thatMakingNotValidVisitThrowsException() {
        //given
        Integer patientId = 1;
        Integer doctorId = 1;
        LocalDate day = LocalDate.of(2023, 10, 10);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(12, 30);

        Doctor doctor = DomainFixtures.someDoctor1().withAvailabilities(Set.of(DomainFixtures.someDoctorAvailability()));


        Mockito.when(doctorDAO.findById(doctorId)).thenReturn(doctor);
        Mockito.when(visitDAO.findConflictingVisits(doctor, day, startTime, endTime)).thenReturn(List.of());

        //when, then
        Assertions.assertThrows(VisitInTakenTimePeriodException.class, () -> visitService.makeVisit(patientId, doctorId, day, startTime, endTime));
    }

}
