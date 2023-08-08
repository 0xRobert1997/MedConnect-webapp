package code.medconnect.fixtures;

import code.medconnect.infrastructure.database.entity.AddressEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;

import java.time.LocalDate;

public class EntityFixtures {

    public static PatientEntity patient1() {
        return PatientEntity.builder()
                .name("X")
                .surname("XX")
                .pesel("12345678900")
                .dateOfBirth(LocalDate.of(2000, 10, 10))
                .sex("male")
                .email("x@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Kolobrzeg")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
    public static PatientEntity patient2() {
        return PatientEntity.builder()
                .name("Y")
                .surname("YY")
                .pesel("12344678900")
                .dateOfBirth(LocalDate.of(2001, 11, 11))
                .sex("male")
                .email("y@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Wadowice")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
    public static PatientEntity patient3() {
        return PatientEntity.builder()
                .name("Z")
                .surname("ZZ")
                .pesel("12345658900")
                .dateOfBirth(LocalDate.of(2003, 1, 14))
                .sex("female")
                .email("z@gmail.com")
                .address(AddressEntity.builder()
                        .country("Poland")
                        .city("Warszawa")
                        .postalCode("11-111")
                        .address("Kolobrzeska 11")
                        .build())
                .build();
    }
}
