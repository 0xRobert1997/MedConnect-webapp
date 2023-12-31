package code.medconnect.controller;

import code.medconnect.api.controller.DoctorController;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DoctorControllerTest {

    @Mock
    private VisitService visitService;
    @Mock
    private DoctorService doctorService;
    @Mock
    private AppUserService appUserService;
    @Mock
    private PatientService patientService;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private Principal principal;
    @Mock
    private Model model;

    @InjectMocks
    private DoctorController doctorController;

    @Test
    void thatDoctorPageWorksCorrectly() {
        //given
        String username = "Doctor1";
        String userEmail = "doctor@example.com";
        AppUser appUser = DomainFixtures.someAppUser();
        appUser.setEmail(userEmail);
        Doctor doctor = DomainFixtures.someDoctor1();
        DoctorDTO doctorDTO = DtoFixtures.someDoctorDTO1();
        Map<Visit, Patient> visitsWithPatients = new HashMap<>();

        //when
        when(principal.getName()).thenReturn(username);
        when(appUserService.findByUsername(username)).thenReturn(appUser);
        when(doctorService.findByEmail(userEmail)).thenReturn(doctor);
        when(doctorService.getDoctorsVisitsWithPatients(doctorDTO.getDoctorId())).thenReturn(visitsWithPatients);
        when(doctorMapper.map(doctor)).thenReturn(doctorDTO);

        String viewName = doctorController.doctorPage(model, principal);

        // then
        verify(appUserService).findByUsername(username);
        verify(doctorService).findByEmail(userEmail);
        verify(doctorService).getDoctorsVisitsWithPatients(doctorDTO.getDoctorId());
        verify(doctorMapper).map(doctor);

        verify(model).addAttribute("doctorDTO", doctorDTO);
        verify(model).addAttribute("visits", visitsWithPatients);
        Assertions.assertEquals("doctor-portal", viewName);
    }

    @Test
    void thatAddAvailabilityWorksCorrectly() {
        //given
        String doctorEmail = "doctor@example.com";
        LocalDate date = LocalDate.of(2023, 1, 1);
        Doctor doctor = DomainFixtures.someDoctor1();

        when(doctorService.findByEmail(doctorEmail)).thenReturn(doctor);
        //when
        String viewName = doctorController.addAvailability(doctorEmail, date);
        //then
        verify(doctorService).findByEmail(doctorEmail);
        verify(doctorService).saveAvailAbility(doctor, date);
        Assertions.assertEquals("redirect:/doctor", viewName);
    }

    @Test
    void thatAddNoteToVisitWorksCorrectly() {
        //gien
        Integer visitId = 1;
        String noteContent = "Some note content";

        //when
        String viewName = doctorController.addNoteToVisit(visitId, noteContent);

        //then
        verify(visitService).addNoteToVisit(visitId, noteContent);
        Assertions.assertEquals("redirect:/doctor", viewName);
    }

    @Test
    void thatCheckPatientWorksCorrectly() {
        //given
        String patientPesel = "12345678901";
        Patient patient = DomainFixtures.somePatient().withPesel(patientPesel);
        PatientDTO patientDTO = DtoFixtures.somePatientDTO().withPesel(patientPesel);

        when(patientService.findPatientWithDiseases(patientPesel)).thenReturn(patient);
        when(patientMapper.map(patient)).thenReturn(patientDTO);

        //when
        String viewName = doctorController.checkPatient(patientPesel, mock(Model.class));

        //then
        verify(patientService).findPatientWithDiseases(patientPesel);
        verify(patientMapper).map(patient);
        Model model = mock(Model.class);
        doctorController.checkPatient(patientPesel, model);
        verify(model).addAttribute("patient", patientDTO);

        Assertions.assertEquals("check-patient", viewName);
    }
}
