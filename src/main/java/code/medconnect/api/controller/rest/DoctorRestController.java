package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(DoctorRestController.DOCTOR_API_BASE_PATH)
public class DoctorRestController {

    public static final String DOCTOR_API_BASE_PATH = "/api/doctor";
    public static final String DOCTOR_ID = "/{doctorId}";
    public static final String PATIENT_EMAIL = "/checkpatient/{patientEmail}";
    public static final String DOCTORS = "/allDoctors";

    private final VisitService visitService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PatientMapper patientMapper;
    private final VisitMapper visitMapper;
    private final DoctorMapper doctorMapper;

    @GetMapping(value = DOCTORS)
    public Set<DoctorDTO> allDoctors() {
        return doctorService.findAll().stream()
                .map(doctorMapper::map)
                .collect(Collectors.toSet());
    }

    @GetMapping(value = DOCTOR_ID)
    public List<VisitDTO> doctorVisits(
            @PathVariable Integer doctorId
    ) {
        return visitService.findAll().stream()
                .map(visitMapper::map)
                .toList();
    }


    @GetMapping(PATIENT_EMAIL)
    public PatientDTO checkPatient(
            @PathVariable String patientEmail
    ) {
        Patient patient = patientService.findByEmail(patientEmail);
        return patientMapper.map(patient);
    }


}
