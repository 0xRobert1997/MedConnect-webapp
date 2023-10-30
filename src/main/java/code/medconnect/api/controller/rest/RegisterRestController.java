package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.PatientService;
import code.medconnect.domain.Address;
import code.medconnect.domain.Patient;
import code.medconnect.security.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping(RegisterRestController.REGISTER_API_BASE_PATH)
public class RegisterRestController {

    public static final String REGISTER_API_BASE_PATH = "/api/register";

    private AppUserService appUserService;
    private PatientService patientService;
    private AppUserMapper appUserMapper;
    private AppUserDTOMapper appUserDTOMapper;
    private PatientMapper patientMapper;


    @PostMapping
    public String register(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("pesel") String pesel,
            @RequestParam("email") @Email(message = "Invalid email format") String email,
            @RequestParam("phoneNumber") @Pattern(regexp = "^\\d{3}-\\d{3}-\\d{3}$",
                    message = "Invalid phone format") String phone,
            @RequestParam("dateOfBirth") String dateOfBirthStr,
            @RequestParam("country") String country,
            @RequestParam("city") String city,
            @RequestParam("postalCode") String postalCode,
            @RequestParam("address") String address
    ) {
        LocalDate parsedDateOfBirth = LocalDate.parse(dateOfBirthStr);

        AppUserDTO appUserDTO = AppUserDTO.builder()
                .userName(username)
                .email(email)
                .build();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        AppUser appUser = appUserMapper.map(appUserDTO);
        appUser.setPassword(hashedPassword);


        RoleEntity role = RoleEntity.builder().id(1).build();
        appUser.setRoles(Set.of(role));
        AppUserDTO createdUser = appUserDTOMapper.map(appUserService.createUser(appUser));


        PatientDTO patientDTO = PatientDTO.builder()
                .appUserDTO(createdUser)
                .name(name)
                .surname(surname)
                .pesel(pesel)
                .phone(phone)
                .email(email)
                .dateOfBirth(parsedDateOfBirth)
                .address(Address.builder()
                        .country(country)
                        .city(city)
                        .postalCode(postalCode)
                        .address(address)
                        .build())
                .build();

        Patient patient = patientMapper.map(patientDTO);
        patientService.createPatient(patient);

        return "Patient successfully registered";
    }
}
