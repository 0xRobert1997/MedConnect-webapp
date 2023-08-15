package code.medconnect.domain;

import code.medconnect.security.AppUser;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {

    Integer doctorId;
    String name;
    String surname;
    String specialization;
    String phone;
    String email;
    byte[] photoData;
    String base64Image;
    Address address;
    Set<DoctorAvailability> availabilities;
    Set<Visit> visits;
    AppUser appUser;


}
