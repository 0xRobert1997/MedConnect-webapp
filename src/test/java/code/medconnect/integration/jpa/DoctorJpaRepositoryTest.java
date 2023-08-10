package code.medconnect.integration.jpa;

import code.medconnect.domain.Doctor;
import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorJpaRepositoryTest {

    DoctorJpaRepository doctorJpaRepository;
    PatientJpaRepository patientJpaRepository;
    VisitJpaRepository visitJpaRepository;

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
    void shouldFindDoctorViaEmailCorrectly() {
        //given
        DoctorEntity doctor = EntityFixtures.someDoctor1();
        doctorJpaRepository.saveAndFlush(doctor);
        String email = doctor.getEmail();

        //when
        Optional<DoctorEntity> byEmail = doctorJpaRepository.findByEmail(email);

        //then
        assertThat(byEmail).isPresent();
    }


}

