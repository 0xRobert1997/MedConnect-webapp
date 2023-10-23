package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Visit;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.apache.catalina.connector.Response;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(VisitRestController.VISIT_API_BASE_PATH)
public class VisitRestController {

    public static final String VISIT_API_BASE_PATH = "/api/visit";
    public static final String VISIT_ID = "/{visitId}";
    public static final String VISIT_CANCEL = "/cancel/{visitId}";
    public static final String VISIT_NEW_VISIT = "/{patientId}/{doctorId}/{selectedDay}/{startTime}/{endTime}";

    private final String VISIT_NEW = "/new";

    private final VisitService visitService;
    private final VisitMapper visitMapper;


    @GetMapping(value = VISIT_ID)
    public List<VisitDTO> allVisits() {
        return visitService.findAll()
                .stream()
                .map(visitMapper::map)
                .toList();
    }

    @PostMapping(VISIT_NEW_VISIT)
    public VisitDTO makeNewVisit(
            @PathVariable("patientId") Integer patientId,
            @PathVariable("doctorId") Integer doctorId,
            @PathVariable("selectedDay") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day,
            @PathVariable("startTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime startTime,
            @PathVariable("endTime") @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)LocalTime endTime
        ) {

        Visit visit = visitService.makeVisit(patientId, doctorId, day, startTime, endTime);

        return visitMapper.map(visit);

    }

    @PostMapping(VISIT_NEW)
    public VisitDTO makeNewVisit(
            @RequestBody VisitDTO visitDTO
        ) {

        Visit visit = visitService.makeVisit(
                visitDTO.getPatientId(),
                visitDTO.getDoctorId(),
                visitDTO.getDay(),
                visitDTO.getStartTime(),
                visitDTO.getEndTime()
        );
        return visitMapper.map(visit);
    }

    @PatchMapping(VISIT_CANCEL)
    public String cancelVisit(
            @PathVariable("visitId") Integer visitId
    ) {
        visitService.cancelVisit(visitId);

        return "Successfully canceled visit with ID: " + visitId;
    }



}
