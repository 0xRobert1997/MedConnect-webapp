package code.medconnect.infrastructure.databse.repository;

import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;

import java.util.*;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientJpaRepositoryTest {

    private AppUserRepository appUserRepository;
    private PatientJpaRepository patientJpaRepository;

    @Test
    void shouldFindByEmailAndPeselCorrectly() {
        //given
        AppUserEntity appUserEntity = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity));
        String email = patientEntity.getEmail();
        String pesel = patientEntity.getPesel();

        //when
        Optional<PatientEntity> byEmail = patientJpaRepository.findByEmail(email);
        Optional<PatientEntity> byPesel = patientJpaRepository.findByPesel(pesel);
        //then
        Assertions.assertTrue(byEmail.isPresent());
        Assertions.assertTrue(byPesel.isPresent());
    }

}
