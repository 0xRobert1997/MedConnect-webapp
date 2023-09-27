package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Patient;
import code.medconnect.security.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(DoctorRestController.DOCTOR_API_BASE_PATH)
 class DoctorRestController {

    static final String DOCTOR_API_BASE_PATH = "/api/doctor";
    static final String DOCTOR_ID = "/{doctorId}";
    static final String PATIENT_EMAIL = "/{patientEmail}";

    private final VisitService visitService;
    private final PatientService patientService;
    private final PatientMapper patientMapper;
    private final VisitMapper visitMapper;


    @GetMapping(value = DOCTOR_ID)
    public List<VisitDTO> doctorVisits(
            @PathVariable Integer doctorId
    ) {
        return visitService.findAll().stream()
                .map(visitMapper::map)
                .toList();
    }


    @PostMapping(PATIENT_EMAIL)
    public PatientDTO checkPatient(
            @PathVariable String patientEmail
    ) {
        Patient patient = patientService.findByEmail(patientEmail);
        return patientMapper.map(patient);
    }
    
    
    
    
    










}
