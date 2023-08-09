package code.medconnect.integration.jpa;

import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.DiseaseJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiseaseJpaRepositoryTest {

    DiseaseJpaRepository diseaseJpaRepository;
    PatientJpaRepository patientJpaRepository;


    @Test
    void shouldAddDiseaseToPatientCorrectly() {
        //given
        PatientEntity patient1 = EntityFixtures.somePatient1();
        DiseaseEntity disease1 = EntityFixtures.someDisease().withPatient(patient1);
        patientJpaRepository.saveAndFlush(patient1);
        diseaseJpaRepository.saveAndFlush(disease1);

        //when
        Set<DiseaseEntity> diseases = diseaseJpaRepository.findByPatientPesel(patient1.getPesel());

        //then
        assertThat(diseases).hasSize(1);
    }
}
