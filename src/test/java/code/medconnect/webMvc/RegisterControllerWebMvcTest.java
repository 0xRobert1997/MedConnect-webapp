package code.medconnect.webMvc;

import code.medconnect.api.controller.RegisterController;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.PatientService;
import code.medconnect.security.*;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class RegisterControllerWebMvcTest {

    private MockMvc mockMvc;

    @MockBean
    AppUserService appUserService;
    @MockBean
    AppUserMapper appUserMapper;
    @MockBean
    AppUserDTOMapper appUserDTOMapper;
    @MockBean
    RoleRepository roleRepository;
    @MockBean
    PatientService patientService;
    @MockBean
    PatientMapper patientMapper;

    @Test
    void registerPageTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("register"));
    }

    @Test
    void registerUserTest() throws Exception {
        //given
        String username = "user";
        String password = "test";
        String name = "Bob";
        String surname = "Bobowski";
        String pesel = "12345678911";
        String email = "xd@example.com";
        String phone = "123-123-123";
        String dateOfBirthStr = "2023-01-05";
        String country = "Poland";
        String city = "Warsaw";
        String postalCode = "123-123";
        String address = "Lipicka 2";
        AppUserDTO appUserDTO = AppUserDTO.builder()
                .userName(username)
                .email(email)
                .build();
        AppUser appUser = AppUser.builder()
                .build();


        Mockito.when(appUserMapper.map(appUserDTO)).thenReturn(appUser);
        Mockito.when(appUserDTOMapper.map(any(AppUser.class))).thenReturn(appUserDTO);
        Mockito.when(appUserService.createUser(appUser)).thenReturn(appUser);

        mockMvc.perform(post("/register")
                        .param("username", username)
                        .param("password", password)
                        .param("name", name)
                        .param("surname", surname)
                        .param("pesel", pesel)
                        .param("email", email)
                        .param("phoneNumber", phone)
                        .param("dateOfBirth", dateOfBirthStr)
                        .param("country", country)
                        .param("city", city)
                        .param("postalCode", postalCode)
                        .param("address", address))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/registration-success"));
    }
}
