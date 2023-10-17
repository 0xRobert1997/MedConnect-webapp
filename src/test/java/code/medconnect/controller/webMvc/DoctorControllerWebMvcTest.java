package code.medconnect.controller.webMvc;

import code.medconnect.api.controller.DoctorController;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.DoctorService;
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
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DoctorController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DoctorControllerWebMvcTest {

    private MockMvc mockMvc;
    @MockBean
    private VisitService visitService;
    @MockBean
    private DoctorService doctorService;
    @MockBean
    private AppUserService appUserService;
    @MockBean
    private PatientService patientService;
    @MockBean
    private PatientMapper patientMapper;
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private VisitMapper visitMapper;

    @Test
    void doctorPageTest() throws Exception {
        Patient patient = DomainFixtures.somePatient();
        Doctor doctor = DomainFixtures.someDoctor1().withDoctorId(1);
        Visit visit = DomainFixtures.someVisit();
        DoctorDTO doctorDTO = DtoFixtures.someDoctorDTO1().withDoctorId(1);
        VisitDTO visitDTO = DtoFixtures.someVisitDTO1();
        PatientDTO patientDTO = DtoFixtures.somePatientDTO();
        String username = "Doctor1";
        String userEmail = doctor.getEmail();
        AppUser appUser = AppUser.builder().email(userEmail).build();
        Map<Visit, Patient> visitsWithPatients = new HashMap<>();
        visitsWithPatients.put(visit, patient);

        Mockito.when(appUserService.findByUsername(username)).thenReturn(appUser);
        Mockito.when(doctorService.findByEmail(Mockito.anyString())).thenReturn(doctor);
        Mockito.when(doctorMapper.map(Mockito.any(Doctor.class))).thenReturn(doctorDTO);
        Mockito.when(visitMapper.map(Mockito.any(Visit.class))).thenReturn(visitDTO);
        Mockito.when(patientMapper.map(Mockito.any(Patient.class))).thenReturn(patientDTO);
        Mockito.when(doctorService.getDoctorsVisitsWithPatients(Mockito.anyInt())).thenReturn(visitsWithPatients);


        // when then
        mockMvc.perform(get("/doctor").principal(new UsernamePasswordAuthenticationToken(username, "test")))
                .andExpect(status().isOk())
                .andExpect(view().name("doctor-portal"))
                .andExpect(model().attribute("doctorDTO", doctorDTO))
                .andExpect(model().attribute("visits", Matchers.hasEntry(visitDTO, patientDTO)));

        Mockito.verify(appUserService).findByUsername(username);
        Mockito.verify(doctorService).findByEmail(userEmail);
        Mockito.verify(doctorMapper).map(Mockito.any(Doctor.class));
        Mockito.verify(doctorService).getDoctorsVisitsWithPatients(Mockito.anyInt());
    }

    @Test
    void addAvailabilityTest() throws Exception {
        //given
        Doctor doctor = DomainFixtures.someDoctor1();
        String doctorEmail = doctor.getEmail();

        //when
        Mockito.when(doctorService.findByEmail(doctorEmail)).thenReturn(doctor);
        //then
        mockMvc.perform(post("/doctor/add-availability")
                        .param("doctorEmail", doctorEmail)
                        .param("date", "2023-01-01")
                        .param("startTime", "12:15")
                        .param("endTime", "12:30"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
    }

    @Test
    void addNoteToVisitTest() throws Exception {
        // given
        Integer visitId = 1;
        String noteContent = "Some note content";

        // when then
        mockMvc.perform(post("/doctor/add-note")
                        .param("visitId", visitId.toString())
                        .param("noteContent", noteContent))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/doctor"));
        Mockito.verify(visitService).addNoteToVisit(visitId, noteContent);
    }

    @Test
    void checkPatientTest() throws Exception {
        // given
        Patient patient = DomainFixtures.somePatient();
        String patientPesel = patient.getPesel();
        PatientDTO patientDTO = DtoFixtures.somePatientDTO();
        patientDTO.setPesel(patientPesel);

        Mockito.when(patientService.findPatientWithDiseases(patientPesel)).thenReturn(patient);
        Mockito.when(patientMapper.map(patient)).thenReturn(patientDTO);

        // wen then
        mockMvc.perform(post("/doctor/check-patient")
                        .param("patientPesel", patientPesel))
                .andExpect(status().isOk())
                .andExpect(view().name("check-patient"))
                .andExpect(model().attribute("patient", patientDTO));
        Mockito.verify(patientService).findPatientWithDiseases(patientPesel);
        Mockito.verify(patientMapper).map(patient);

    }
}
