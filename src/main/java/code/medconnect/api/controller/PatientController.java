package code.medconnect.api.controller;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PaginationService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Visit;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PatientController {

    static final String PATIENT_BASE_PATH = "/patient";
    private final PatientService patientService;
    private final VisitService visitService;
    private final AppUserService appUserService;
    private final DoctorService doctorService;
    private final PaginationService paginationService;


    @RequestMapping(value = PATIENT_BASE_PATH, method = RequestMethod.GET)
    public String PatientPage(Model model, Principal principal) {

        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        String userEmail = appUser.getEmail();
        PatientDTO patientDTO = patientService.findByEmail(userEmail);
        List<VisitDTO> patientsVisits = patientService.getPatientsVisits(patientDTO.getPesel());
        Set<Doctor> doctors = doctorService.findAllDoctors();

        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("visitHistory", patientsVisits);
        model.addAttribute("doctors", doctors);

        return "patient-portal";
    }

    @GetMapping("/new-visit")
    public String newVisitPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("patientEmail") String patientEmail,
            @ModelAttribute("doctorEmail") String doctorEmail
    ) {
        PatientDTO patientDTO = patientService.findByEmail(patientEmail);
        int pageSize = 1;

        Page<DoctorAvailabilityDTO> doctorAvailabilityPage = paginationService.paginate(page, pageSize, doctorEmail);
        List<LocalTime> availableTimes = getAvailableTimeFrames();

        model.addAttribute("doctorAvailabilities", doctorAvailabilityPage.getContent());
        model.addAttribute("doctorAvailabilityPage", doctorAvailabilityPage);
        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("doctorEmail", doctorEmail);
        model.addAttribute("availableTimes", availableTimes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", doctorAvailabilityPage.getTotalPages());

        return "new-visit";
    }



    List<LocalTime> getAvailableTimeFrames() {
        return List.of(
                LocalTime.of(8, 0),
                LocalTime.of(8, 30),
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30)
        );
    }


    private String formatAvailability(DoctorAvailabilityDTO availability) {
        return availability.getDay() + " " +
                availability.getStartTime() + " - " +
                availability.getEndTime();
    }

}
