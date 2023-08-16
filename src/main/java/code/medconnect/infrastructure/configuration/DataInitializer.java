package code.medconnect.infrastructure.configuration;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.business.DoctorService;
import code.medconnect.domain.*;
import code.medconnect.infrastructure.database.repository.*;
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
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    private final AppUserRepository appUserRepository;
    private final VisitRepository visitRepository;
    private final NoteRepository noteRepository;

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public void run(String... args) throws Exception {

        Integer userPatient1Id = initializerUserPatient1();
        Integer userPatient2Id = initializerUserPatient2();

        Integer userDoctor1Id = initializerUserDoctor1();
        Integer userDoctor2Id = initializerUserDoctor2();

        initializePatients(userPatient1Id, userPatient2Id);
        initializeDoctors(userDoctor1Id, userDoctor2Id);

        initializeAvailabilities();





    }
    private void initializeAvailabilities() {
        doctorRepository.saveAvailability(someAvailability1());
        doctorRepository.saveAvailability(someAvailability2());
        doctorRepository.saveAvailability(someAvailability3());
        doctorRepository.saveAvailability(someAvailability4());
    }

    private DoctorAvailability someAvailability1() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 15))
                .startTime(LocalTime.of(8,0))
                .endTime(LocalTime.of(16,0))
                .build();
    }
    private DoctorAvailability someAvailability2() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 16))
                .startTime(LocalTime.of(8,0))
                .endTime(LocalTime.of(16,0))
                .build();
    }
    private DoctorAvailability someAvailability3() {
        return DoctorAvailability.builder()
                .doctorId(2)
                .day(LocalDate.of(2023, 9, 14))
                .startTime(LocalTime.of(8,0))
                .endTime(LocalTime.of(16,0))
                .build();
    }
    private DoctorAvailability someAvailability4() {
        return DoctorAvailability.builder()
                .doctorId(2)
                .day(LocalDate.of(2023, 9, 18))
                .startTime(LocalTime.of(8,0))
                .endTime(LocalTime.of(16,0))
                .build();
    }


       /* private void initializeExampleVisits(Patient patient , Doctor doctor ) {

*//*            Patient patient = patientRepository.findById(userPatient1Id);
            Doctor doctor = doctorRepository.findById(userDoctor1Id);*//*

            Visit visit1 = Visit.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .day(LocalDate.now())
                    .startTime(LocalTime.of(10, 0))
                    .endTime(LocalTime.of(11, 0))
                    .build();

            Visit visit2 = Visit.builder()
                    .patient(patient)
                    .doctor(doctor)
                    .day(LocalDate.now().plusDays(1))
                    .startTime(LocalTime.of(15, 30))
                    .endTime(LocalTime.of(16, 30))
                    .build();

            Visit savedVisit1 = visitRepository.saveVisit(visit1);
            Visit savedVisit2 = visitRepository.saveVisit(visit2);

            initializeNote(savedVisit1);
            initializeNote(savedVisit2);

        }*/

/*    private void initializeNote(Visit visit) {
        noteRepository.saveNote(
                Note.builder()
                        .noteContent("bla bla bla")
                        .dateTime(OffsetDateTime.of(2023, 8, 10,10,10,0,0, ZoneOffset.UTC))
                        .build(),
                visit
        );
    }*/


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


    private List<Integer> initializePatients(Integer user1Id, Integer user2Id) {

        Patient patient1 = getPatient1();
        Patient patient2 = getPatient2();

        Patient savedPatient1 = patientRepository.savePatient(patient1.withAppUser(
                AppUser.builder()
                        .id(user1Id)
                        .build()));
        Patient savedPatient2 = patientRepository.savePatient(patient2.withAppUser(
                AppUser.builder()
                        .id(user2Id)
                        .build())
        );
        return List.of(savedPatient1.getPatientId(), savedPatient2.getPatientId());
    }
    private List<Integer> initializeDoctors(Integer userDoctor1Id, Integer userDoctor2Id) {

        Doctor doctor1 = getDoctor1();
        Doctor doctor2 = getDoctor2();

        Doctor savedDoctor1 = doctorRepository.saveDoctor(doctor1
                .withAppUser(AppUser.builder()
                        .id(userDoctor1Id)
                        .build()));

        Doctor savedDoctor2 = doctorRepository.saveDoctor(doctor2
                .withAppUser(AppUser.builder()
                        .id(userDoctor2Id)
                        .build()));
        return List.of(savedDoctor1.getDoctorId(), savedDoctor2.getDoctorId());
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
                .email("patient1@example.com")
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
                .email("patient2@example.com")
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
                .email("doctor1@example.com")
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
                .email("doctor2@example.com")
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
