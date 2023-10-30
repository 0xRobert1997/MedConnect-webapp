package code.medconnect.util;

import code.medconnect.domain.*;
import code.medconnect.security.AppUser;
import code.medconnect.security.RoleEntity;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Set;

public class DomainFixtures {

    public static Visit someVisit() {
        return Visit.builder()
                .day(LocalDate.of(2023, 9, 9))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .build();
    }

    public static Disease someDisease() {
        return Disease.builder()
                .diseaseName("butt hurt")
                .description("bla bla bla")
                .diagnosisDate(OffsetDateTime.of(2023, 5, 5, 12, 30, 0, 0, ZoneOffset.UTC))
                .build();
    }

    public static DoctorAvailability someDoctorAvailability() {
        return DoctorAvailability.builder()
                .day(LocalDate.of(2023, 10, 15))
                .startTime(LocalTime.of(10, 0, 0))
                .endTime(LocalTime.of(16, 0, 0))
                .build();
    }

    public static AppUser someAppUser() {
        return AppUser.builder()
                .userName("Patient1")
                .email("patient1@example.com")
                //password: test
                .password("$2a$12$GVehLNO8yk4GQc3Yil2IuOaoVR26Qg1/tjBCuUxKfoiAQadIs07AC")
                .roles(Set.of(RoleEntity.builder().id(1).build()))
                .build();
    }


    public static Note someNote() {
        return Note.builder()
                .noteContent("bla bla bla")
                .build();
    }

    public static Doctor someDoctor1() {
        return Doctor.builder()
                .name("Doc1")
                .surname("One")
                .specialization("Humans")
                .phone("666-666-666")
                .email("dr1@example.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    public static Doctor someDoctor2() {
        return Doctor.builder()
                .name("Doc1")
                .surname("One")
                .specialization("Humans")
                .phone("666-444-666")
                .email("dr2@example.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    public static Doctor someDoctorWithAvailability() {
        return Doctor.builder()
                .name("Doc1")
                .surname("One")
                .specialization("Humans")
                .phone("666-666-666")
                .email("dr1@example.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .availabilities(Set.of(DoctorAvailability.builder()
                        .day(LocalDate.of(2023, 9, 9))
                        .startTime(LocalTime.of(8, 0))
                        .endTime(LocalTime.of(16, 0))
                        .build()))
                .build();
    }


    public static Patient somePatient() {
        return Patient.builder()
                .name("Z")
                .surname("ZZ")
                .pesel("12345658900")
                .dateOfBirth(LocalDate.of(2003, 1, 14))
                .sex("female")
                .phone("888-999-999")
                .email("z@gmail.com")
                .address(Address.builder()
                        .country("Poland")
                        .city("Warszawa")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
}
