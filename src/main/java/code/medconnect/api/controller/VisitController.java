package code.medconnect.api.controller;

import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;

@Controller
@AllArgsConstructor
public class VisitController {

    private final VisitService visitService;



    @PostMapping("/confirm-visit")
    public String confirmVisit(
            @RequestParam("patientId") Integer patientId,
            @RequestParam("doctorId") Integer doctorId,
            @RequestParam("selectedDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
            @RequestParam("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
            @RequestParam("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime
    ) {

        visitService.makeVisit(patientId, doctorId, day, startTime, endTime);

        return "redirect:/patient";
    }

    @PostMapping("/cancel-visit")
    public String cancelVisit(
            @RequestParam("visitId") Integer visitId
    ) {
        visitService.cancelVisit(visitId);

        return "redirect:/patient";
    }

}
