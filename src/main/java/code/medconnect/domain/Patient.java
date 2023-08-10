package code.medconnect.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {


    Integer patientId;
    String name;
    String surname;
    String pesel;
    LocalDate dateOfBirth;
    String sex;
    String phone;
    String email;
    byte[] photoData;
    Address address;
    Set<Visit> visits;
    Set<Disease> diseases;

}
