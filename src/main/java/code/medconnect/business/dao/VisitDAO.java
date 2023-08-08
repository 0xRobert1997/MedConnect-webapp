package code.medconnect.business.dao;

import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;

import java.util.Date;
import java.util.List;

public interface VisitDAO {

    List<Visit> findVisitByPatientPesel(String patientPesel);

    Visit saveVisit(Visit visit);

    void saveNote(Note note, Visit visit);

    void cancelVisit(Visit visit);
}
