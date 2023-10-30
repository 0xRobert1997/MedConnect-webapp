package code.medconnect.business;

import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Patient;
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
import java.util.*;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private VisitDAO visitDAO;
    @Mock
    private PatientDAO patientDAO;
    @Mock
    private DoctorDAO doctorDAO;
    @Mock
    private DoctorAvailabilityDAO doctorAvailabilityDAO;


    @InjectMocks
    private DoctorService doctorService;

    @Test
    void shouldFindAllDoctorsCorrectly() {
        //given
        Doctor doctor = DomainFixtures.someDoctor1();
        Set<Doctor> allDoctors = new HashSet<>();
        allDoctors.add(doctor);
        //when
        Mockito.when(doctorDAO.findAll()).thenReturn(allDoctors);
        //then
        Set<Doctor> result = doctorService.findAll();
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void shouldFindDoctorByEmailCorrectly() {
        // given
        Doctor doctor = DomainFixtures.someDoctor1();
        String email = doctor.getEmail();
        Mockito.when(doctorDAO.findByEmail(email)).thenReturn(doctor);

        // when,then
        Doctor result = doctorService.findByEmail(email);
        Assertions.assertEquals(doctor.getEmail(), result.getEmail());
    }

    @Test
    void shouldInvokeDaoToSaveAvailabilityCorrectly() {
        //given
        Doctor doctor = DomainFixtures.someDoctor1();
        LocalDate day = LocalDate.of(2050, 1, 4);
        //when
        doctorService.saveAvailAbility(doctor, day);
        //then
        Mockito.verify(doctorAvailabilityDAO).saveAvailability(Mockito.any(DoctorAvailability.class));
    }

    @Test
    void shouldInvokeDaoToDeleteCorrectly() {
        //given
        DoctorAvailability doctorAvailability = DomainFixtures.someDoctorAvailability();
        //then
        doctorService.deleteAvailAbility(doctorAvailability);
        //then
        Mockito.verify(doctorAvailabilityDAO).deleteAvailability(Mockito.any(DoctorAvailability.class));
    }

    @Test
    void shouldReturnVisitsWithPatientsCorrectly() {
        //given
        Integer doctorId = 1;
        Integer patientId = 1;
        Patient patient = DomainFixtures.somePatient();
        Visit visit1 = DomainFixtures.someVisit()
                .withPatientId(patientId);
        Visit visit2 = DomainFixtures.someVisit()
                .withPatientId(patientId)
                .withDay(LocalDate.of(2023, 10, 10));
        List<Visit> visits = new ArrayList<>();
        visits.add(visit1);
        visits.add(visit2);

        Mockito.when(visitDAO.findVisitsByDoctorId(doctorId)).thenReturn(visits);
        Mockito.when(patientDAO.findById(Mockito.anyInt())).thenReturn(Optional.of(patient));
        //when
        Map<Visit, Patient> result = doctorService.getDoctorsVisitsWithPatients(doctorId);
        Integer expected = visits.size();
        //then
        Assertions.assertEquals(expected, result.size());

    }


}
