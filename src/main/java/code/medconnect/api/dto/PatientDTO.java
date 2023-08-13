package code.medconnect.api.dto;

import code.medconnect.domain.Address;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    Integer patientId;
    String name;
    String surname;
    String pesel;
    LocalDate dateOfBirth;
    String sex;
    String phone;
    String email;
    byte[] photoData;
    String base64Image;
    Address address;
    AppUserDTO appUserDTO;

}
