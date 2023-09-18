package code.medconnect.api.dto;

import code.medconnect.domain.Address;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUserDTO;
import lombok.*;

import java.util.Set;

@Data
@Builder
@With
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
    String imgurPhotoId;
    Address address;
    Set<DoctorAvailability> availabilities;
    Set<Visit> visits;
    AppUserDTO appUser;
}
