package code.medconnect.business;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.fixtures.DomainFixtures;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.hamcrest.MatcherAssert;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.testcontainers.shaded.org.hamcrest.MatcherAssert.assertThat;
import static org.testcontainers.shaded.org.hamcrest.Matchers.containsInAnyOrder;


@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    PatientDAO patientDAO;

    @InjectMocks
    PatientService patientService;

    @Test
    void thatMakingNewDiseaseForPatientWorksCorrectly() {
        // Given
        String pesel = "123456789";
        String diseaseName = "Flu";
        String diseaseDescription = "Common cold";
        OffsetDateTime diagnosisDateTime = OffsetDateTime.of(
                        2010,5, 5, 10, 0,0,0, ZoneOffset.UTC);

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
        Patient patient = new Patient();
        Disease disease = new Disease();
        disease.setDiseaseName("Flu");
        patient.setDiseases(Set.of(disease));
        Mockito.when(patientDAO.findByPesel(pesel)).thenReturn(Optional.of(patient));


        // When
        Set<Disease> diseases = patientService.getPatientsDiseases(pesel);

        // Then
        MatcherAssert.assertThat(diseases,containsInAnyOrder(disease));
    }


}
