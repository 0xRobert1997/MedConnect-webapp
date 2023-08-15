package code.medconnect.api.dto;

import code.medconnect.domain.Address;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDTO {

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
    AppUserDTO appUser;
}
