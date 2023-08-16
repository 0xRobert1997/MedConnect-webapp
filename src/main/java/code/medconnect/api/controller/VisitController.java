package code.medconnect.api.controller;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@AllArgsConstructor
public class VisitController {

    private final VisitService visitService;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;


    @PostMapping("/confirm-visit")
    public String confirmVisit(
            @RequestParam("patientEmail") String patientEmail,
            @RequestParam("doctorEmail") String doctorEmail,
            @RequestParam("selectedDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime,
            RedirectAttributes redirectAttributes
    ) {
        PatientDTO patientDTO = patientService.findByEmail(patientEmail);
        DoctorDTO doctorDTO = doctorService.findByEmail(doctorEmail);
        Patient patient = patientMapper.map(patientDTO);
        Doctor doctor = doctorMapper.map(doctorDTO);

        visitService.makeVisit(patient, doctor, day, startTime, endTime);


        redirectAttributes.addFlashAttribute("successMessage", "Visit confirmed successfully!");

        return "redirect:/patient-portal";
    }
}
