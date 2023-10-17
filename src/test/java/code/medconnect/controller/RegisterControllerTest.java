package code.medconnect.controller;

import code.medconnect.api.controller.RegisterController;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.PatientService;
import code.medconnect.domain.Patient;
import code.medconnect.security.*;
import code.medconnect.util.DomainFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class RegisterControllerTest {

    @InjectMocks
    RegisterController registerController;

    @Mock
    private AppUserService appUserService;
    @Mock
    private AppUserMapper appUserMapper;
    @Mock
    private AppUserDTOMapper appUserDTOMapper;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private PatientService patientService;
    @Mock
    private PatientMapper patientMapper;

    @Test
    void registerUserTest() {
        //given
        String username = "tester";
        String password = "test";
        String name = "Rob";
        String surname = "Bobowski";
        String pesel = "12345678901";
        String email = "rob@test.com";
        String phone = "123-123-123";
        String dateOfBirthStr = "2000-10-10";
        String country = "Poland";
        String city = "Warsaw";
        String postalCode = "12-123";
        String address = "Papugowa 12";

        AppUser appUser = DomainFixtures.someAppUser();
        Patient patient = DomainFixtures.somePatient();

        AppUserDTO appUserDTO = AppUserDTO.builder()
                .password(appUser.getPassword())
                .userName(appUser.getUserName())
                .email(appUser.getEmail())
                .roles(appUser.getRoles())
                .build();

        Mockito.when(appUserMapper.map(Mockito.any(AppUserDTO.class))).thenReturn(appUser);
        Mockito.when(appUserService.createUser(Mockito.any(AppUser.class))).thenReturn(appUser);
        Mockito.when(appUserDTOMapper.map(Mockito.any(AppUser.class))).thenReturn(appUserDTO);
        Mockito.when(patientMapper.map(Mockito.any(PatientDTO.class))).thenReturn(patient);

        //when

        String viewName = registerController.registerUser
                (username, password, name, surname, pesel, email, phone, dateOfBirthStr, country, city, postalCode, address);

        //then
        Assertions.assertEquals("redirect:/registration-success", viewName);

        Mockito.verify(patientService, Mockito.times(1)).createPatient(Mockito.any(Patient.class));
        Mockito.verify(appUserMapper, Mockito.times(1)).map(Mockito.any(AppUserDTO.class));


    }
}
