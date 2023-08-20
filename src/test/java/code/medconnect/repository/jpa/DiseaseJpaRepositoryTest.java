package code.medconnect.repository.jpa;

import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.DiseaseJpaRepository;
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
import java.util.Optional;
import java.util.Set;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DiseaseJpaRepositoryTest {

    private DiseaseJpaRepository diseaseJpaRepository;
    private PatientJpaRepository patientJpaRepository;
    private AppUserRepository appUserRepository;

    @Test
    void shouldSaveDiseaseCorrectly() {
        //given
        AppUserEntity appUserEntity = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity));
        //when
        diseaseJpaRepository.save(EntityFixtures.someDisease().withPatient(patientEntity));
        //then
        List<DiseaseEntity> all = diseaseJpaRepository.findAll();
        Assertions.assertEquals(1, all.size());
    }

    @Test
    void shouldFindDiseaseByPatientPesel() {
        //given
        AppUserEntity appUserEntity = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity));
        DiseaseEntity diseaseEntity = diseaseJpaRepository.saveAndFlush(EntityFixtures.someDisease().withPatient(patientEntity));
        String pesel = patientEntity.getPesel();

        //when
        Set<DiseaseEntity> diseases = diseaseJpaRepository.findByPatientPesel(pesel);
        //then
        Assertions.assertEquals(1, diseases.size());

    }
}
