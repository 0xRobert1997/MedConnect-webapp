package code.medconnect.integration.jpa;

import code.medconnect.domain.Doctor;
import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
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
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorJpaRepositoryTest {

    DoctorJpaRepository doctorJpaRepository;

    @Test
    void shouldSaveDoctorCorrectly() {
        //given
        DoctorEntity doctor = EntityFixtures.someDoctor1();
        doctorJpaRepository.saveAndFlush(doctor);

        //when
        List<DoctorEntity> all = doctorJpaRepository.findAll();

        //then
        assertThat(all).hasSize(1);
    }

    @Test
    void shouldFindPatientViaEmailCorrectly() {
        //given
        DoctorEntity doctor1 = EntityFixtures.someDoctor1();
        doctorJpaRepository.saveAndFlush(doctor1);
        String email = doctor1.getEmail();

        //when
        Optional<DoctorEntity> byEmail = doctorJpaRepository.findByEmail(email);

        //then
        assertThat(byEmail).isPresent();
    }
}

