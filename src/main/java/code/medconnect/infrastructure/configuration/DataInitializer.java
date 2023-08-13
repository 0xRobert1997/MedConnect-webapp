package code.medconnect.infrastructure.configuration;

import code.medconnect.domain.Address;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.repository.DoctorRepository;
import code.medconnect.infrastructure.database.repository.PatientRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserEntity;
import code.medconnect.security.AppUserRepository;
import code.medconnect.security.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final PatientJpaRepository patientJpaRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public void run(String... args) throws Exception {

        Integer userPatient1Id = initializerUserPatient1();
        Integer userPatient2Id = initializerUserPatient2();
        Integer userDoctor1Id = initializerUserDoctor1();
        Integer userDoctor2Id = initializerUserDoctor2();

        initializePatients(userPatient1Id, userPatient2Id);
        initializeDoctors(userDoctor1Id, userDoctor2Id);
    }

    private Integer initializerUserPatient1() {
        AppUserEntity userPatient1 = AppUserEntity.builder()
                .userName("Patient1")
                .email("patient1@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(1).build()))
                .active(true)
                .active(true)
                .build();
        AppUserEntity saved = appUserRepository.save(userPatient1);
        return saved.getId();
    }
    private Integer initializerUserPatient2() {
        AppUserEntity userPatient1 = AppUserEntity.builder()
                .userName("Patient2")
                .email("patient2@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(1).build()))
                .active(true)
                .build();
        AppUserEntity saved = appUserRepository.save(userPatient1);
        return saved.getId();
    }
    private Integer initializerUserDoctor1() {
        AppUserEntity userPatient1 = AppUserEntity.builder()
                .userName("Doctor1")
                .email("doctor1@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(2).build()))
                .active(true)
                .build();
        AppUserEntity saved = appUserRepository.save(userPatient1);
        return saved.getId();
    }
    private Integer initializerUserDoctor2() {
        AppUserEntity userPatient1 = AppUserEntity.builder()
                .userName("Doctor2")
                .email("doctor2@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(2).build()))
                .active(true)
                .build();
        AppUserEntity saved = appUserRepository.save(userPatient1);
        return saved.getId();
    }


    private void initializePatients(Integer user1Id, Integer user2Id) {

        Patient patient1 = getPatient1();
        Patient patient2 = getPatient2();

        patientRepository.savePatient(patient1.withAppUser(
                AppUser.builder()
                .id(user1Id)
                .build()));
        patientRepository.savePatient(patient2.withAppUser(
                AppUser.builder()
                        .id(user2Id)
                        .build()));
    }
    private void initializeDoctors(Integer userDoctor1Id, Integer userDoctor2Id) {

        Doctor doctor1 = getDoctor1();
        Doctor doctor2 = getDoctor2();

        doctorRepository.saveDoctor(doctor1
                .withUser(AppUser.builder()
                        .id(userDoctor1Id)
                        .build()));

        doctorRepository.saveDoctor(doctor2
                .withUser(AppUser.builder()
                        .id(userDoctor2Id)
                        .build()));
    }

    private byte[] getPhotoBytes(String imagePath) {
        try {
            ClassPathResource resource = new ClassPathResource(imagePath);
            return IOUtils.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.warn("Cannot load photo correctly");
            e.printStackTrace();
            return new byte[0];
        }
    }


    private Patient getPatient1() {
        return Patient.builder()
                .name("SpongeBob")
                .surname("Kanciastoporty")
                .pesel("21212121211")
                .phone("123-321-123")
                .dateOfBirth(LocalDate.of(1995, 10, 10))
                .sex("male")
                .photoData(getPhotoBytes("static/images/SpongeBob.jpg"))
                .email("spondgebob@gmail.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Patient getPatient2() {
        return Patient.builder()
                .name("Patrick")
                .surname("Star")
                .pesel("12121212121")
                .phone("321-321-321")
                .dateOfBirth(LocalDate.of(1996, 10, 10))
                .sex("male")
                .photoData(getPhotoBytes("static/images/Patrick.jpg"))
                .email("patrick@gmail.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Doctor getDoctor1() {
        return Doctor.builder()
                .name("Gregory")
                .surname("House")
                .specialization("Humans")
                .phone("666-666-666")
                .email("drHouse@example.com")
                .photoData(getPhotoBytes("static/images/doctor-house.jpg"))
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Doctor getDoctor2() {
        return Doctor.builder()
                .name("John")
                .surname("Dolittle")
                .specialization("Animals")
                .phone("420-420-420")
                .email("drDolittle@example.com")
                .photoData(getPhotoBytes("static/images/doctor-dolittle.jpg"))
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("22-222")
                        .address("pieskowa 3")
                        .build())
                .build();
    }
}
