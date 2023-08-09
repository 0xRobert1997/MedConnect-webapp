package code.medconnect.integration.jpa;


import code.medconnect.domain.Patient;
import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Import(TestContainerConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientJpaRepositoryTest {

    private PatientJpaRepository patientJpaRepository;

    @Test
    void shouldSavePatientCorrectly() {
        //given
        PatientEntity patient1 = EntityFixtures.somePatient1();
        patientJpaRepository.saveAndFlush(patient1);

        //when
        List<PatientEntity> all = patientJpaRepository.findAll();

        //then
        assertThat(all).hasSize(1);
    }

    @Test
    void shouldFindPatientViaEmailCorrectly() {
        //given
        PatientEntity patient1 = EntityFixtures.somePatient1();
        patientJpaRepository.saveAndFlush(patient1);
        String email = patient1.getEmail();

        //when
        Optional<PatientEntity> byEmail = patientJpaRepository.findByEmail(email);

        //then
        assertThat(byEmail).isPresent();
    }
    @Test
    void shouldFindPatientViaPeselCorrectly() {
        //given
        PatientEntity patient1 = EntityFixtures.somePatient1();
        patientJpaRepository.saveAndFlush(patient1);
        String pesel = patient1.getPesel();

        //when
        Optional<PatientEntity> byEmail = patientJpaRepository.findByPesel(pesel);

        //then
        assertThat(byEmail).isPresent();
    }
}
