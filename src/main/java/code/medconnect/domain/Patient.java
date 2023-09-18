package code.medconnect.domain;

import code.medconnect.security.AppUser;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@With
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
    String imgurPhotoId;
    Address address;
    Set<Disease> diseases;
    Set<Visit> visits;
    AppUser appUser;

}
