package code.medconnect.infrastructure.configuration;

import code.medconnect.domain.*;
import code.medconnect.security.AppUserEntity;
import code.medconnect.security.RoleEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

@Slf4j
public class DataInitializerData {
    private static byte[] getPhotoBytes(String imagePath) {
        try {
            ClassPathResource resource = new ClassPathResource(imagePath);
            return IOUtils.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.warn("Cannot load photo correctly");
            e.printStackTrace();
            return new byte[0];
        }
    }

    static DoctorAvailability availabilityFixture1() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 15))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture2() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 16))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture3() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 17))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture4() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 18))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture5() {
        return DoctorAvailability.builder()
                .doctorId(2)
                .day(LocalDate.of(2023, 9, 19))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture6() {
        return DoctorAvailability.builder()
                .doctorId(1)
                .day(LocalDate.of(2023, 9, 25))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static DoctorAvailability availabilityFixture7() {
        return DoctorAvailability.builder()
                .doctorId(2)
                .day(LocalDate.of(2023, 9, 27))
                .startTime(LocalTime.of(8, 0))
                .endTime(LocalTime.of(16, 0))
                .build();
    }

    static AppUserEntity appUserEntityFixture1() {
        return AppUserEntity.builder()
                .userName("Patient1")
                .email("patient1@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(1).build()))
                .active(true)
                .active(true)
                .build();
    }

    static AppUserEntity appUserEntityFixture2() {
        return AppUserEntity.builder()
                .userName("Patient2")
                .email("patient2@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(1).build()))
                .active(true)
                .build();
    }

    static AppUserEntity appUserEntityFixture3() {
        return AppUserEntity.builder()
                .userName("Doctor1")
                .email("doctor1@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(2).build()))
                .active(true)
                .build();
    }

    static AppUserEntity appUserEntityFixture4() {
        return AppUserEntity.builder()
                .userName("Doctor2")
                .email("doctor2@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(2).build()))
                .active(true)
                .build();
    }

    static Patient patientFixture1() {
        return Patient.builder()
                .name("SpongeBob")
                .surname("Kanciastoporty")
                .pesel("21212121211")
                .phone("123-321-123")
                .dateOfBirth(LocalDate.of(1995, 10, 10))
                .sex("male")
                //     .photoData(getPhotoBytes("static/images/SpongeBob.jpg"))
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

    static Patient patientFixture2() {
        return Patient.builder()
                .name("Patrick")
                .surname("Star")
                .pesel("12121212121")
                .phone("321-321-321")
                .dateOfBirth(LocalDate.of(1996, 10, 10))
                .sex("male")
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

    static Doctor doctorFixture1() {
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

    static Doctor doctorFixture2() {
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

    static Visit visitFixture1() {
        return Visit.builder()
                .day(LocalDate.of(2023, 7, 30))
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(13, 0))
                .patientId(1)
                .doctorId(1)
                .build();
    }

    static Visit visitFixture2() {
        return Visit.builder()
                .day(LocalDate.of(2023, 9, 20))
                .startTime(LocalTime.of(14, 30))
                .endTime(LocalTime.of(15, 0))
                .patientId(1)
                .doctorId(1)
                .build();
    }

    static Visit visitFixture3() {
        return Visit.builder()
                .day(LocalDate.of(2023, 8, 25))
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(13, 0))
                .patientId(2)
                .doctorId(1)
                .build();
    }

    static Visit visitFixture4() {
        return Visit.builder()
                .day(LocalDate.of(2023, 8, 26))
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(13, 0))
                .patientId(1)
                .doctorId(2)
                .build();
    }

    static Visit visitFixture5() {
        return Visit.builder()
                .day(LocalDate.of(2023, 9, 6))
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(13, 0))
                .patientId(1)
                .doctorId(2)
                .build();
    }

    static Visit visitFixture6() {
        return Visit.builder()
                .day(LocalDate.of(2023, 9, 15))
                .startTime(LocalTime.of(12, 30))
                .endTime(LocalTime.of(13, 0))
                .patientId(2)
                .doctorId(1)
                .build();
    }

    static Disease diseaseFixture1() {
        return Disease.builder()
                .patient(patientFixture1().withPatientId(1))
                .diagnosisDate(OffsetDateTime.of(
                        2023, 1, 1, 11, 0, 0, 0, ZoneOffset.UTC))
                .diseaseName("Scary")
                .description("Olej cieknie pacjentowi stopy")
                .build();
    }

    static Disease diseaseFixture2() {
        return Disease.builder()
                .patient(patientFixture2().withPatientId(1))
                .diagnosisDate(OffsetDateTime.of(
                        2023, 2, 2, 13, 0, 0, 0, ZoneOffset.UTC))
                .diseaseName("Nothing scary")
                .description("Patient lost his mind!")
                .build();
    }

    static Disease diseaseFixture3() {
        return Disease.builder()
                .patient(patientFixture1().withPatientId(2))
                .diagnosisDate(OffsetDateTime.of(
                        2023, 7, 7, 12, 0, 0, 0, ZoneOffset.UTC))
                .diseaseName("Headache")
                .description("bla bla bla")
                .build();
    }

}
