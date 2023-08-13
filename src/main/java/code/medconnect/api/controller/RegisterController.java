package code.medconnect.api.controller;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.business.PatientService;
import code.medconnect.domain.Address;
import code.medconnect.security.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Set;

@Controller
@AllArgsConstructor
public class RegisterController {

    static final String REGISTER_BASE_PATH = "/register";
    AppUserService appUserService;
    AppUserMapper appUserMapper;
    AppUserDTOMapper appUserDTOMapper;
    RoleRepository roleRepository;
    PatientService patientService;

    @RequestMapping(value = REGISTER_BASE_PATH, method = RequestMethod.GET)
    public String homePage() {
        return "register";
    }

    @PostMapping(REGISTER_BASE_PATH)
    public String registerUser(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam("name") String name,
            @RequestParam("surname") String surname,
            @RequestParam("pesel") String pesel,
            @RequestParam("email") String email,
            @RequestParam("phoneNumber") String phone,
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

        patientService.createPatient(patientDTO);

        return "redirect:/registration-success";
    }

}
