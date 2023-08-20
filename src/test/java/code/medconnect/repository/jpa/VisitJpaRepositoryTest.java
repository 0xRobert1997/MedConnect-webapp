package code.medconnect.repository.jpa;

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
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VisitJpaRepositoryTest {

    private AppUserRepository appUserRepository;
    private PatientJpaRepository patientJpaRepository;
    private DoctorJpaRepository doctorJpaRepository;
    private VisitJpaRepository visitJpaRepository;

    @Test
    void shouldFindByDoctorIdAndDayCorrectly() {
        //given
        LocalDate day = LocalDate.of(2023, 10, 10);
        AppUserEntity appUserEntity1 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        AppUserEntity appUserEntity2 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture2());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity1));
        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(
                EntityFixtures.someDoctor1().withAppUser(appUserEntity2));
        visitJpaRepository.saveAndFlush(EntityFixtures.someVisit()
                        .withDoctorId(doctorEntity.getDoctorId())
                        .withPatientId(patientEntity.getPatientId())
                        .withDay(day));

        //when
        List<VisitEntity> all = visitJpaRepository.findAll();
        List<VisitEntity> result = visitJpaRepository.findByDoctorIdAndDay(doctorEntity.getDoctorId(), day);
        //then
        Assertions.assertEquals(1, result.size());
    }
    @Test
    void shouldFindByDoctorIdCorrectly() {
        //given
        AppUserEntity appUserEntity1 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        AppUserEntity appUserEntity2 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture2());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity1));
        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(
                EntityFixtures.someDoctor1().withAppUser(appUserEntity2));
        visitJpaRepository.saveAndFlush(EntityFixtures.someVisit()
                        .withDoctorId(doctorEntity.getDoctorId())
                        .withPatientId(patientEntity.getPatientId()));

        //when
        List<VisitEntity> result = visitJpaRepository.findByDoctorId(doctorEntity.getDoctorId());
        //then
        Assertions.assertEquals(1, result.size());
    }

    @Test
    void shouldCancelVisitByIdCorrectly() {
        //given
        AppUserEntity appUserEntity1 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        AppUserEntity appUserEntity2 = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture2());
        PatientEntity patientEntity = patientJpaRepository.saveAndFlush(
                EntityFixtures.somePatient1().withAppUser(appUserEntity1));
        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(
                EntityFixtures.someDoctor1().withAppUser(appUserEntity2));
        VisitEntity visitEntity = visitJpaRepository.saveAndFlush(EntityFixtures.someVisit()
                .withDoctorId(doctorEntity.getDoctorId())
                .withPatientId(patientEntity.getPatientId()));
        Integer visitId = visitEntity.getVisitId();

        //when
        visitJpaRepository.cancelVisitById(visitId);
        Optional<VisitEntity> byId = visitJpaRepository.findById(visitId);
        //then
        Assertions.assertTrue(byId.isPresent());
        Assertions.assertTrue(byId.get().isCanceled());
    }
}
