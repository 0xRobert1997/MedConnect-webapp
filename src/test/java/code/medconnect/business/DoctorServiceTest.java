package code.medconnect.business;

import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
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
import java.util.HashSet;
import java.util.Set;
@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

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
        LocalTime startTime = LocalTime.of(13, 13);
        LocalTime endTime = LocalTime.of(13, 19);
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




}
