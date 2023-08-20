package code.medconnect.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DiseaseJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.integration.configuration.PersistenceContainerTestConfiguration;
import code.medconnect.security.AppUserEntity;
import code.medconnect.security.AppUserRepository;
import code.medconnect.util.EntityFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorAvailabilityJpaRepositoryTest {

    private AppUserRepository appUserRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;

    @Test
    void shouldFindDoctorAvailabilityByDoctorIdCorrectly() {
        //given
        AppUserEntity appUserEntity = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(
                EntityFixtures.someDoctor1().withAppUser(appUserEntity));
        Integer doctorId = doctorEntity.getDoctorId();
        doctorAvailabilityJpaRepository.saveAndFlush(EntityFixtures.someDoctorAvailability().withDoctorId(doctorId));
        //when
        List<DoctorAvailabilityEntity> result = doctorAvailabilityJpaRepository.findByDoctorId(doctorId);
        //then
        Assertions.assertEquals(1, result.size());
    }
}
