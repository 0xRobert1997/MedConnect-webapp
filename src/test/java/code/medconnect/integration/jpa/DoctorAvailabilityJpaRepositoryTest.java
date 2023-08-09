package code.medconnect.integration.jpa;

import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorAvailabilityJpaRepositoryTest {

    DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    DoctorJpaRepository doctorJpaRepository;

    @Test
    void shouldAddAvailabilityToDoctorCorrectly() {
        //given
        DoctorEntity doctor = EntityFixtures.someDoctor1();
        DoctorEntity savedDoctor = doctorJpaRepository.saveAndFlush(doctor);
        DoctorAvailabilityEntity availability = EntityFixtures.someDoctorAvailability().withDoctor(savedDoctor);
        doctorAvailabilityJpaRepository.saveAndFlush(availability);

        //when
        Set<DoctorAvailabilityEntity> result = doctorAvailabilityJpaRepository.findByDoctorEmail(doctor.getEmail());

        //then
        assertThat(result).hasSize(1);
    }

}

