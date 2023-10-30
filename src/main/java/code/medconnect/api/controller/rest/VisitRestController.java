package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Visit;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(VisitRestController.VISIT_API_BASE_PATH)
public class VisitRestController {

    public static final String VISIT_API_BASE_PATH = "/api/visit";
    public static final String VISIT_ALL = "/all";
    public static final String VISIT_CANCEL = "/cancel/{visitId}";

    public static final String VISIT_NEW = "/new";

    private final VisitService visitService;
    private final VisitMapper visitMapper;


    @GetMapping(value = VISIT_ALL)
    public List<VisitDTO> allVisits() {
        return visitService.findAll()
                .stream()
                .map(visitMapper::map)
                .toList();
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
