package code.medconnect.controller.webMvc;

import code.medconnect.api.controller.PatientController;
import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorAvailabilityMapper;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.*;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserService;
import code.medconnect.util.DomainFixtures;
import code.medconnect.util.DtoFixtures;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PatientController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerWebMvcTest {


    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;
    @MockBean
    private VisitService visitService;
    @MockBean
    private AppUserService appUserService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private PaginationService paginationService;
    @MockBean
    private PatientMapper patientMapper;
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private VisitMapper visitMapper;

    @MockBean
    private ImgurService imgurService;

    @MockBean
    private DoctorAvailabilityService doctorAvailabilityService;

    @MockBean
    private DoctorAvailabilityMapper doctorAvailabilityMapper;

    @Test
    void patientPageTest() throws Exception {
        //given
        Patient patient = DomainFixtures.somePatient().withPatientId(1);
        PatientDTO patientDTO = DtoFixtures.somePatientDTO().withPatientId(1);
        DoctorDTO doctorDTO = DtoFixtures.someDoctorDTO1().withDoctorId(1);
        VisitDTO visitDTO1 = DtoFixtures.someVisitDTO1();
        String username = "Patient1";
        String userEmail = patient.getEmail();
        AppUser appUser = AppUser.builder().email(userEmail).build();

        Set<Visit> visits = Set.of(DomainFixtures.someVisit());
        Set<Doctor> doctors = Set.of(DomainFixtures.someDoctor1());

        Set<DoctorDTO> doctorsDTOs = Set.of(doctorDTO);
        List<VisitDTO> patientVisits = List.of(visitDTO1);

        when(appUserService.findByUsername(username)).thenReturn(appUser);
        when(patientService.findByEmail(Mockito.anyString())).thenReturn(patient);
        when(patientMapper.map(Mockito.any(Patient.class))).thenReturn(patientDTO);
        when(doctorMapper.map(Mockito.any(Doctor.class))).thenReturn(doctorDTO);
        when(visitMapper.map(Mockito.any(Visit.class))).thenReturn(visitDTO1);
        when(visitService.getPatientsVisits(patientDTO.getPesel())).thenReturn(visits);
        when(doctorService.findAll()).thenReturn(doctors);
        when(imgurService.getPhoto(patient)).thenReturn(new byte[0]);

        //when then
        mockMvc.perform(MockMvcRequestBuilders.get("/patient").principal(new UsernamePasswordAuthenticationToken(username, "test")))
                .andExpect(status().isOk())
                .andExpect(view().name("patient-portal"))
                .andExpect(model().attribute("patientDTO", patientDTO))
                .andExpect(model().attribute("visitHistory", patientVisits))
                .andExpect(model().attribute("doctors", doctorsDTOs));
    }

    @Test
    void newVisitPageTest() throws Exception {
        int doctorId = 1;
        int patientId = 2;
        int page = 0;
        DoctorAvailability someAvailability = DomainFixtures.someDoctorAvailability();
        DoctorAvailabilityDTO someAvailabilityDTO = DoctorAvailabilityDTO.builder()
                .day(LocalDate.of(2023, 10, 10))
                .startTime(LocalTime.of(8,0))
                .endTime(LocalTime.of(16, 0))
                .build();

        Page<DoctorAvailability> doctorAvailabilityPage = new PageImpl<>(List.of(
                someAvailability)
        );
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOs = List.of(someAvailabilityDTO);

        Mockito.when(paginationService.paginate(eq(page), anyInt(), eq(doctorId)))
                .thenReturn(doctorAvailabilityPage);
        Mockito.when(doctorAvailabilityService.getAvailabilityWithSlots(Mockito.any()))
                        .thenReturn(someAvailability);
        Mockito.when(doctorAvailabilityMapper.map(someAvailability))
                        .thenReturn(someAvailabilityDTO);


        mockMvc.perform(MockMvcRequestBuilders.get("/new-visit")
                        .param("doctorId", String.valueOf(doctorId))
                        .param("patientId", String.valueOf(patientId))
                        .param("page", String.valueOf(page)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("new-visit"))
                .andExpect(MockMvcResultMatchers.model().attribute("doctorAvailabilities", doctorAvailabilityDTOs))
                .andExpect(MockMvcResultMatchers.model().attribute("patientId", patientId))
                .andExpect(MockMvcResultMatchers.model().attribute("doctorId", doctorId))
                .andExpect(MockMvcResultMatchers.model().attribute("currentPage", page))
                .andExpect(MockMvcResultMatchers.model().attribute("totalPages", doctorAvailabilityPage.getTotalPages()));
    }
}
