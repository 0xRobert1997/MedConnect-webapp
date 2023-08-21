package code.medconnect.api.dto;

import code.medconnect.domain.Address;
import code.medconnect.security.AppUserDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class PatientDTO {

    Integer patientId;
    String name;
    String surname;
    String pesel;
    LocalDate dateOfBirth;
    String sex;
    @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$")
    String phone;
    @Email
    String email;
    byte[] photoData;
    String base64Image;
    Set<DiseaseDTO> diseases;
    Set<VisitDTO> visits;
    Address address;
    AppUserDTO appUserDTO;

}
