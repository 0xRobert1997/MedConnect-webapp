package code.medconnect.webMvc;

import code.medconnect.api.controller.OurDoctorsController;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.domain.Doctor;
import code.medconnect.util.DomainFixtures;
import code.medconnect.util.DtoFixtures;
import lombok.AllArgsConstructor;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = OurDoctorsController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OurDoctorsControllerWebMvcTest {

    private MockMvc mockMvc;

    @MockBean
    DoctorService doctorService;

    @MockBean
    DoctorMapper doctorMapper;

    @Test
    void ourDoctorsPageTest() throws Exception {
        //given
        Doctor doctor1 = DomainFixtures.someDoctor1();
        Doctor doctor2 = DomainFixtures.someDoctor2();
        DoctorDTO doctorDTO1 = DtoFixtures.someDoctorDTO1();
        DoctorDTO doctorDTO2 = DtoFixtures.someDoctorDTO2();
        doctorDTO1.setPhotoData(new byte[]{1, 2, 3});
        doctorDTO2.setPhotoData(new byte[]{1, 2, 3});
        Set<DoctorDTO> doctorsDTO = new HashSet<>();
        doctorsDTO.add(doctorDTO1);
        doctorsDTO.add(doctorDTO2);

        Mockito.when(doctorService.findAll()).thenReturn(Set.of(doctor1, doctor2));
        Mockito.when(doctorMapper.map(doctor1)).thenReturn(doctorDTO1);
        Mockito.when(doctorMapper.map(doctor2)).thenReturn(doctorDTO2);

        mockMvc.perform(MockMvcRequestBuilders.get("/ourdoctors"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("ourdoctors"))
                .andExpect(model().attribute("doctors", Matchers.containsInAnyOrder(doctorDTO1, doctorDTO2)));
    }
}
