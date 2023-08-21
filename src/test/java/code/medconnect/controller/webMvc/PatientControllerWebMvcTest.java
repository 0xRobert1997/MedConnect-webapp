package code.medconnect.controller.webMvc;

import code.medconnect.api.controller.PatientController;
import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PaginationService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Doctor;
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
    PatientService patientService;
    @MockBean
    VisitService visitService;
    @MockBean
    AppUserService appUserService;
    @MockBean
    DoctorService doctorService;
    @MockBean
    PaginationService paginationService;
    @MockBean
    PatientMapper patientMapper;
    @MockBean
    DoctorMapper doctorMapper;
    @MockBean
    VisitMapper visitMapper;

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

      List<Visit> visits = List.of(DomainFixtures.someVisit());
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
      int page = 1;
      Page<DoctorAvailabilityDTO> doctorAvailabilityPage = new PageImpl<>(List.of(new DoctorAvailabilityDTO()));

      Mockito.when(paginationService.paginate(eq(page), anyInt(), eq(doctorId)))
              .thenReturn(doctorAvailabilityPage);

      mockMvc.perform(MockMvcRequestBuilders.get("/new-visit")
                      .param("doctorId", String.valueOf(doctorId))
                      .param("patientId", String.valueOf(patientId))
                      .param("page", String.valueOf(page)))
              .andExpect(MockMvcResultMatchers.status().isOk())
              .andExpect(MockMvcResultMatchers.view().name("new-visit"))
              .andExpect(MockMvcResultMatchers.model().attribute("doctorAvailabilities", doctorAvailabilityPage.getContent()))
              .andExpect(MockMvcResultMatchers.model().attribute("doctorAvailabilityPage", doctorAvailabilityPage))
              .andExpect(MockMvcResultMatchers.model().attribute("patientId", patientId))
              .andExpect(MockMvcResultMatchers.model().attribute("doctorId", doctorId))
              .andExpect(MockMvcResultMatchers.model().attribute("currentPage", page))
              .andExpect(MockMvcResultMatchers.model().attribute("totalPages", doctorAvailabilityPage.getTotalPages()));

  }
}
