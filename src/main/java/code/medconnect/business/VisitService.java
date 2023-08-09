package code.medconnect.business;

import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;
import code.medconnect.domain.exception.VisitNotFoundException;
import code.medconnect.infrastructure.database.repository.VisitRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class VisitService {

    private final VisitRepository visitRepository;



    public void cancelVisit(Integer visitId) {
        Visit visit = visitRepository.findVisitById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit with ID " + visitId + " not found"));

        visitRepository.cancelVisit(visit);
        visitRepository.saveVisit(visit);
    }

    public void addNoteToVisit(Integer visitId, Note note) {
        Visit visit = visitRepository.findVisitById(visitId)
                .orElseThrow(() -> new VisitNotFoundException("Visit with ID " + visitId + " not found"));

        note.setVisit(visit);
        visit.setNote(note);

        visitRepository.saveVisit(visit);
    }
}
