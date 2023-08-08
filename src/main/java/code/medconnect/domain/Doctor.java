package code.medconnect.domain;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
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
    Address address;
    Set<DoctorAvailability> availabilities;
    Set<Visit> visits;


}
