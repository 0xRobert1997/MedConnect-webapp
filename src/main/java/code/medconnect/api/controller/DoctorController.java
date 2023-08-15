package code.medconnect.api.controller;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.business.DoctorService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
public class DoctorController {

    static final String DOCTOR_BASE_PATH = "/doctor";

    private final VisitService visitService;
    private final DoctorService doctorService;
    private final AppUserService appUserService;

    @RequestMapping(value = DOCTOR_BASE_PATH, method = RequestMethod.GET)
    public String DoctorPage(Model model, Principal principal) {

        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        String userEmail = appUser.getEmail();

        DoctorDTO doctorDTO = doctorService.findByEmail(userEmail);
        List<VisitDTO> visits = doctorService.getDoctorsVisits(doctorDTO.getEmail());

        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("visits", visits);

        return "doctor-portal";
    }


    @PostMapping("/doctor/add-availability")
    public String addAvailability(
            @RequestParam String doctorEmail,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime
    ) {
        DoctorDTO doctorDTO = doctorService.findByEmail(doctorEmail);
        doctorService.saveAvailAbility(doctorDTO, date, startTime, endTime);

        return "redirect:/doctor";
    }



    @PostMapping("/doctor/add-note")
    public String addNoteToVisit(
            @RequestParam String doctorEmail,
            @RequestParam Integer visitId,
            @RequestParam String noteContent
    ) {
        DoctorDTO doctorDTO = doctorService.findByEmail(doctorEmail);
        visitService.addNoteToVisit(visitId, noteContent);

        return "redirect:/doctor";
    }

    @PostMapping("/doctor/check-patient")
    public String checkPatient(@RequestParam String patientPesel, Model model) {



        return "redirect:/doctor"; // lub inny widok w zależności od rezultatu
    }
}
