package code.medconnect.api.controller;

import code.medconnect.business.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class VisitController {

    private final VisitService visitService;


    @PostMapping("/confirm-visit")
    public String confirmVisit(
            @RequestParam("patientId") Integer patientId,
            @RequestParam("doctorId") Integer doctorId,
            @RequestParam("selectedDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
            @RequestParam("selectedTimeSlot") String selectedTimeSlotStr
    ) {

        Map<String, LocalTime> parsedTimes = parseStartAndEndTime(selectedTimeSlotStr);

        visitService.makeVisit(patientId, doctorId, day, parsedTimes.get("startTime"), parsedTimes.get("endTime"));

        return "redirect:/patient";
    }

    @PostMapping("/cancel-visit")
    public String cancelVisit(
            @RequestParam("visitId") Integer visitId
    ) {
        visitService.cancelVisit(visitId);

        return "redirect:/patient";
    }

    private Map<String, LocalTime> parseStartAndEndTime(String timeSlotStr) {
        Map<String, LocalTime> parsedValues = new HashMap<>();

        String[] split = timeSlotStr.split("-");
        parsedValues.put("startTime", LocalTime.parse(split[0]));
        parsedValues.put("endTime", LocalTime.parse(split[1]));

        return parsedValues;
    }

}
