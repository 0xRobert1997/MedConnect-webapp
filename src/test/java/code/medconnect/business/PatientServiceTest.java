package code.medconnect.business;

import code.medconnect.business.PatientService;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Optional;
import java.util.Set;

import static org.testcontainers.shaded.org.hamcrest.Matchers.containsInAnyOrder;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientDAO patientDAO;

    @InjectMocks
    PatientService patientService;

    @Test
    void createPatientTest() {
        //given
        Patient patient = DomainFixtures.somePatient();
        //when
        patientService.createPatient(patient);
        //then
        Mockito.verify(patientDAO).savePatient(Mockito.any(Patient.class));
    }

    @Test
    void thatMakingNewDiseaseForPatientWorksCorrectly() {
        // Given
        String pesel = "123456789";
        String diseaseName = "Flu";
        String diseaseDescription = "Common cold";
        OffsetDateTime diagnosisDateTime = OffsetDateTime.of(
                2010, 5, 5, 10, 0, 0, 0, ZoneOffset.UTC);

        // When
        patientService.makeNewDiseaseForPatient(pesel, diseaseName, diseaseDescription, diagnosisDateTime);

        // Then
        Mockito.verify(patientDAO).addDisease(Mockito.eq(pesel), Mockito.argThat(disease ->
                disease.getDiseaseName().equals(diseaseName) &&
                        disease.getDescription().equals(diseaseDescription) &&
                        disease.getDiagnosisDate().equals(diagnosisDateTime)
        ));
    }

    @Test
    void testGetPatientsDiseases() {
        // Given
        String pesel = "123456789";
        Patient patient = DomainFixtures.somePatient().withPesel("123456789");
        Disease disease = DomainFixtures.someDisease();
        patient.setDiseases(Set.of(disease));
        Mockito.when(patientDAO.findByPesel(pesel)).thenReturn(Optional.of(patient));

        // When
        Set<Disease> diseases = patientService.getPatientsDiseases(pesel);

        // Then
        MatcherAssert.assertThat(diseases, containsInAnyOrder(disease));
    }

    @Test
    void thatFindingPatientByEmailReturnsPatient() {
        //given
        Patient patient = DomainFixtures.somePatient();
        String email = patient.getEmail();
        Mockito.when(patientDAO.findByEmail(email)).thenReturn(Optional.of(patient));

        // When
        Patient resultPatient = patientService.findByEmail(email);

        // Then
        Assertions.assertEquals(patient, resultPatient);
    }

    @Test
    public void shouldThrowWhenPatientDoesntExists() {
        // Given
        String email = "nonexistent@example.com";
        Mockito.when(patientDAO.findByEmail(email)).thenReturn(Optional.empty());

        // when, then
        Assertions.assertThrows(NotFoundException.class, () -> patientService.findByEmail(email));
    }

    @Test
    public void testFindPatientWithDiseases_PatientDoesNotExist_ReturnsNull() {
        //given
        String patientPesel = "nonexistentPesel";
        Mockito.when(patientDAO.findByPeselWithDiseases(patientPesel)).thenReturn(null);
        //when
        Patient resultPatient = patientService.findPatientWithDiseases(patientPesel);
        //then
        Assertions.assertNull(resultPatient);
    }


}
