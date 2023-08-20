package code.medconnect.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
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

import java.util.Optional;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(PersistenceContainerTestConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorJpaRepositoryTest {

    private AppUserRepository appUserRepository;
    private DoctorJpaRepository doctorJpaRepository;

    @Test
    void shouldFindByEmailCorrectly() {
        //given
        AppUserEntity appUserEntity = appUserRepository.saveAndFlush(
                EntityFixtures.someAppUserEntityFixture1());
        DoctorEntity doctorEntity = doctorJpaRepository.saveAndFlush(
                EntityFixtures.someDoctor1().withAppUser(appUserEntity));
        String email = doctorEntity.getEmail();
        //when
        Optional<DoctorEntity> result = doctorJpaRepository.findByEmail(email);
        //then
        Assertions.assertTrue(result.isPresent());
    }
}
