package code.medconnect.api.controller;

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
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class DoctorController {

    private static final String DOCTOR_BASE_PATH = "/doctor";

    private final VisitService visitService;
    private final DoctorService doctorService;
    private final AppUserService appUserService;
    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final VisitMapper visitMapper;

    @GetMapping(value = DOCTOR_BASE_PATH)
    public String doctorPage(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        String userEmail = appUser.getEmail();

        Doctor doctor = doctorService.findByEmail(userEmail);
        DoctorDTO doctorDTO = doctorMapper.map(doctor);
        Map<Visit, Patient> doctorsVisitsWithPatients = doctorService.getDoctorsVisitsWithPatients(doctorDTO.getDoctorId());
        Map<VisitDTO, PatientDTO> doctorsVisitsWithPatientsDTO = doctorsVisitsWithPatients.entrySet().stream()
                .collect(Collectors.toMap(
                        entry -> visitMapper.map(entry.getKey()),
                        entry -> patientMapper.map(entry.getValue())
                ));

        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("visits", doctorsVisitsWithPatientsDTO);
        return "doctor-portal";
    }


    @PostMapping("/doctor/add-availability")
    public String addAvailability(
            @RequestParam String doctorEmail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Doctor doctor = doctorService.findByEmail(doctorEmail);
        doctorService.saveAvailAbility(doctor, date);

        return "redirect:/doctor";
    }


    @PostMapping("/doctor/add-note")
    public String addNoteToVisit(
            @RequestParam Integer visitId,
            @RequestParam String noteContent
    ) {
        visitService.addNoteToVisit(visitId, noteContent);

        return "redirect:/doctor";
    }


    @PostMapping("/doctor/check-patient")
    public String checkPatient(
            @RequestParam String patientPesel,
            Model model
    ) {
        Patient patient = patientService.findPatientWithDiseases(patientPesel);
        PatientDTO patientDTO = patientMapper.map(patient);

        model.addAttribute("patient", patientDTO);
        return "check-patient";
    }
}
