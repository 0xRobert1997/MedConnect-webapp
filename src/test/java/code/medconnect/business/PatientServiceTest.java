package code.medconnect.business;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.fixtures.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientDAO patientDAO;

    @InjectMocks
    PatientService patientService;

    @Test
    void thatGettingPatientDiseasesWorksCorrectly() {
        // Given
        Patient patient = DomainFixtures.somePatient();
        String pesel = patient.getPesel();
    //    patient.setDiseases(new HashSet<>()); // Ustalamy pustą listę chorób

        Mockito.when(patientDAO.findByPesel(pesel)).thenReturn(Optional.of(patient));

        // When
        Set<Disease> diseases = patientService.getPatientsDiseases(pesel);

        // Then
        assertTrue(diseases.isEmpty()); // Upewniamy się, że lista chorób jest pusta
    }
}
