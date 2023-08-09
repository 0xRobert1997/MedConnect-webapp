package code.medconnect.fixtures;

import code.medconnect.infrastructure.database.entity.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class EntityFixtures {

    public static PatientEntity somePatient1() {
        return PatientEntity.builder()
                .name("X")
                .surname("XX")
                .pesel("12345678900")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .sex("male")
                .phone("999-999-999")
                .email("x@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Kolobrzeg")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
    public static PatientEntity somePatient2() {
        return PatientEntity.builder()
                .name("Y")
                .surname("YY")
                .pesel("12344678900")
                .dateOfBirth(LocalDate.of(2001, 11, 11))
                .sex("female")
                .phone("888-888-999")
                .email("y@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Wadowice")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
    public static PatientEntity somePatient3() {
        return PatientEntity.builder()
                .name("Z")
                .surname("ZZ")
                .pesel("12345658900")
                .dateOfBirth(LocalDate.of(2003, 1, 14))
                .sex("female")
                .phone("888-999-999")
                .email("z@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Warszawa")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }

    public static DoctorEntity someDoctor1() {
        return DoctorEntity.builder()
                .name("Doc1")
                .surname("One")
                .specialization("Humans")
                .phone("666-666-666")
                .email("dr1@example.com")
                .address(AddressEntity
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    public static DoctorEntity someDoctor2() {
        return DoctorEntity.builder()
                .name("Doc2")
                .surname("Two")
                .specialization("Animals")
                .phone("420-420-420")
                .email("dr2@example.com")
                .address(AddressEntity
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("22-222")
                        .address("pieskowa 3")
                        .build())
                .build();
    }

    public static DiseaseEntity someDisease() {
        return DiseaseEntity.builder()
                .diseaseName("butt hurt")
                .description("bla bla bla")
                .diagnosisDate(OffsetDateTime.of(2023, 5, 5, 12, 30, 0, 0, ZoneOffset.UTC))
                .build();
    }

    public static DoctorAvailabilityEntity someDoctorAvailability() {
        return DoctorAvailabilityEntity.builder()
                .day(LocalDate.of(2023, 10, 15))
                .startTime(LocalTime.of(10,0,0))
                .endTime(LocalTime.of(16,0,0))
                .build();
    }

    public static VisitEntity someVisit() {
        return VisitEntity.builder()
                .day(LocalDate.of(2023, 9, 9))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10,30))
                .build();
    }

    public static NoteEntity someNote() {
       return NoteEntity.builder()
                .noteContent("bla bla bla")
                .build();
    }
}
