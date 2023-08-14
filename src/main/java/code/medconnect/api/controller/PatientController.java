package code.medconnect.api.controller;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;
import java.util.List;

@Controller
@AllArgsConstructor
public class PatientController {

    static final String PATIENT_BASE_PATH = "/patient";
    private final PatientService patientService;
    private final VisitService visitService;
    private final AppUserService appUserService;


    @RequestMapping(value = PATIENT_BASE_PATH, method = RequestMethod.GET)
    public String PatientPage(Model model, Principal principal) {

        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        String userEmail = appUser.getEmail();
        PatientDTO patientDTO = patientService.findByEmail(userEmail);
        List<VisitDTO> patientsVisits = patientService.getPatientsVisits(patientDTO.getPesel());

        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("visitHistory", patientsVisits);

        return "patient-portal";
    }
}
