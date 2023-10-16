package code.medconnect.business;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Visit;
import code.medconnect.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(MockitoExtension.class)
public class DoctorAvailabilityServiceTest {

    @InjectMocks
    private DoctorAvailabilityService doctorAvailabilityService;
    @Mock
    private VisitDAO visitDAO;

    @Mock
    private DoctorDAO doctorDAO;

    @Test
    void getAvailabilityWithSlotsTest() {
        //given
        Integer doctorId = 1;
        DoctorAvailability someAvailability = DomainFixtures.someDoctorAvailability().withDoctorId(doctorId);
        Doctor doctor = DomainFixtures.someDoctor1().withDoctorId(doctorId);
        List<Visit> someVisits = List.of(DomainFixtures.someVisit());

        Mockito.when(doctorDAO.findById(doctor.getDoctorId())).thenReturn(doctor);
        Mockito.when(visitDAO.findByDoctorAndDay(doctor, someAvailability.getDay())).thenReturn(someVisits);

        //when
        DoctorAvailability result = doctorAvailabilityService.getAvailabilityWithSlots(someAvailability);

        //then
        Assertions.assertEquals(15, result.getTimeSlots().size());
        Assertions.assertEquals(1, result.getDoctorId());
    }
}
