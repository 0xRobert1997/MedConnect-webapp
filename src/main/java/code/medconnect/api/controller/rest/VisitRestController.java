package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.VisitService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(VisitRestController.VISIT_API_BASE_PATH)
public class VisitRestController {

    static final String VISIT_API_BASE_PATH = "/api/visit";
    static final String VISIT_ID = "/{visitId}";

    private final VisitService visitService;
    private final VisitMapper visitMapper;


    @GetMapping(value = VISIT_ID)
    public List<VisitDTO> allVisits() {
        return visitService.findAll()
                .stream()
                .map(visitMapper::map)
                .toList();
    }

}
