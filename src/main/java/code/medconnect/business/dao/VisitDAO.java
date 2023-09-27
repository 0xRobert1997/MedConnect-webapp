package code.medconnect.business.dao;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Visit;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface VisitDAO {

    List<Visit> findVisitsByDoctorId(Integer doctorId);

    Visit saveVisit(Visit visit);

    List<Visit> findByDoctorAndDay(Doctor doctor, LocalDate day);

    Optional<Visit> findVisitById(Integer id);


    void cancelVisit(Visit visit);

    List<Visit> findConflictingVisits(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime);

    Set<Visit> findByPatientId(Integer patientId);

    List<Visit> findAll();

}
