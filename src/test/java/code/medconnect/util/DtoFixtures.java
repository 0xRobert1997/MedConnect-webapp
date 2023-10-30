package code.medconnect.util;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.domain.Address;

import java.time.LocalDate;
import java.time.LocalTime;

public class DtoFixtures {

    public static DoctorDTO someDoctorDTO1() {
        return DoctorDTO.builder()
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

    public static DoctorDTO someDoctorDTO2() {
        return DoctorDTO.builder()
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

    public static PatientDTO somePatientDTO() {
        return PatientDTO.builder()
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

    public static VisitDTO someVisitDTO1() {
        return VisitDTO.builder()
                .day(LocalDate.of(2023, 9, 9))
                .startTime(LocalTime.of(10, 0))
                .endTime(LocalTime.of(10, 30))
                .build();
    }

    public static VisitDTO someVisitDTO2() {
        return VisitDTO.builder()
                .day(LocalDate.of(2023, 9, 25))
                .startTime(LocalTime.of(14, 0))
                .endTime(LocalTime.of(14, 30))
                .build();
    }
}
