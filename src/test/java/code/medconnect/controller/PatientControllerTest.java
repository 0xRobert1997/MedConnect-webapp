package code.medconnect.controller;

import code.medconnect.api.controller.PatientController;
import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.*;
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
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.ui.Model;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    @Mock
    private PatientService patientService;
    @Mock
    private VisitService visitService;
    @Mock
    private AppUserService appUserService;
    @Mock
    private DoctorService doctorService;
    @Mock
    private ImgurService imgurService;
    @Mock
    private PaginationService paginationService;
    @Mock
    private PatientMapper patientMapper;
    @Mock
    private DoctorMapper doctorMapper;
    @Mock
    private VisitMapper visitMapper;
    @Mock
    private Model model;
    @Mock
    private Principal principal;

    @InjectMocks
    PatientController patientController;

    @Test
    void thatPatientPageWorksCorrectly() {
        //given
        AppUser appUser = DomainFixtures.someAppUserFixture1();
        Patient patient = DomainFixtures.somePatient().withAppUser(appUser);
        PatientDTO patientDTO = DtoFixtures.somePatientDTO();
        Set<Visit> visits = Set.of(DomainFixtures.someVisit());
        Set<Doctor> doctors = Set.of(DomainFixtures.someDoctor1(), DomainFixtures.someDoctor2());

        when(principal.getName()).thenReturn("username");
        when(appUserService.findByUsername("username")).thenReturn(appUser);
        when(patientService.findByEmail(appUser.getEmail())).thenReturn(patient);
        when(visitService.getPatientsVisits(patient.getPesel())).thenReturn(visits);
        when(doctorService.findAll()).thenReturn(doctors);
        when(patientMapper.map(Mockito.any(Patient.class))).thenReturn(patientDTO);

        when(imgurService.getPhoto(Mockito.any(Patient.class))).thenReturn(new byte[0]);

        //when
        String viewName = patientController.patientPage(model, principal);

        // then
        verify(appUserService).findByUsername("username");
        verify(patientService).findByEmail(appUser.getEmail());
        verify(visitService).getPatientsVisits(patient.getPesel());
        verify(doctorService).findAll();
        Assertions.assertEquals("patient-portal", viewName);
    }


}
