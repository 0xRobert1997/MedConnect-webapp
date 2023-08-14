package code.medconnect.business;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.fixtures.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    DoctorDAO doctorDAO;

    @InjectMocks
    DoctorService doctorService;

    @Test
    void testFindDoctorByEmailReturnsDoctor() {
        // Given
        String email = "doctor@example.com";
        Doctor doctor = DomainFixtures.someDoctor();
        Mockito.when(doctorDAO.findByEmail(email)).thenReturn(Optional.of(doctor));

        // When
        Doctor foundDoctor = doctorService.findByEmail(email);

        // Then
        Assertions.assertSame(doctor, foundDoctor);
    }

    @Test
    void testFindDoctorByEmailThrowsExceptionIfDoctorNotFound() {
        // Given
        String email = "wrongEmail";
        Mockito.when(doctorDAO.findByEmail(email)).thenReturn(Optional.empty());

        // When, Then
        Assertions.assertThrows(NotFoundException.class, () -> doctorService.findByEmail(email));
    }
}
