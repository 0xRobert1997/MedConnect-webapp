package code.medconnect.business.dao;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Visit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

public interface VisitDAO {

    List<Visit> findVisitByPatientPesel(String patientPesel);

    Visit saveVisit(Visit visit);

    List<Visit> findByDoctorAndDay(Doctor doctor, LocalDate day);

    Optional<Visit> findVisitById(Integer id);


    void cancelVisit(Visit visit);

    List<Visit> findConflictingVisits(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime);
}
