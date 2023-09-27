package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.PatientService;
import code.medconnect.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(PatientRestController.PATIENT_API_BASE_PATH)
public class PatientRestController {

    static final String PATIENT_API_BASE_PATH = "/api/patient";
    static final String PATIENT_ID = "/{patientEmail}";
    private final PatientService patientService;
    private final PatientMapper patientMapper;


    @GetMapping(PATIENT_ID)
    public PatientDTO patientDetails(
            @PathVariable String patientEmail
    ) {
        Patient patient = patientService.findByEmail(patientEmail);
        return patientMapper.map(patient);
    }
}
