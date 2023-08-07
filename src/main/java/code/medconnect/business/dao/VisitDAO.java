package code.medconnect.business.dao;

import code.medconnect.domain.Visit;

import java.util.Date;
import java.util.List;

public interface VisitDAO {

    List<Visit> findVisitByPatientPesel(String patientPesel);

    void saveVisit(Date date, String startHour, String endHour, String patientEmail, String DoctorEmail);

    void saveNote(String noteContent, Visit visit);
}
