package code.medconnect.business;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.NoteDAO;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.*;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.domain.exception.VisitInTakenTimePeriodException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class VisitService {

    private final VisitDAO visitDAO;
    private final PatientDAO patientDAO;
    private final DoctorDAO doctorDAO;
    private final NoteDAO noteDAO;



    @Transactional
    public void cancelVisit(Integer visitId) {
        Visit visit = visitDAO.findVisitById(visitId)
                .orElseThrow(() -> new NotFoundException("Visit with ID " + visitId + " not found"));

        visitDAO.cancelVisit(visit);
        log.info("Canceled visit with id: [{}]", visitId);
    }

    @Transactional
    public void addNoteToVisit(Integer visitId, String noteContent) {
        Visit visit = visitDAO.findVisitById(visitId)
                .orElseThrow(() -> new NotFoundException("Visit with ID " + visitId + " not found"));

        Note note = Note.builder()
                .noteContent(noteContent)
                .dateTime(OffsetDateTime.now())
                .build();

        note.setVisit(visit);
        noteDAO.saveNote(note);
        log.info("Added note to visit with id: [{}], noteContent: [{}]", visitId, noteContent);
    }


    @Transactional
    public Set<Visit> getPatientsVisits(String pesel) {
        Patient patient = patientDAO.findByPesel(pesel)
                .orElseThrow(() -> new NotFoundException("Patient with pesel: " + pesel + " doesn't exist"));
        Integer patientId = patient.getPatientId();

        return visitDAO.findByPatientId(patientId);

    }

    public List<Visit> findAll() {
        return visitDAO.findAll();
    }

    @Transactional
    public Visit makeVisit(Integer patientId, Integer doctorId, LocalDate day, LocalTime startTime, LocalTime endTime) {

        Doctor doctor = doctorDAO.findById(doctorId);

        boolean dateAndTimeInDoctorAvailability = isDoctorAvailableAtTime(doctor, day, startTime, endTime);
        boolean visitTimeAvailable = isVisitInAvailableTime(doctor, day, startTime, endTime);

        if (dateAndTimeInDoctorAvailability && visitTimeAvailable) {
            return visitDAO.saveVisit(Visit.builder()
                    .patientId(patientId)
                    .doctorId(doctorId)
                    .day(day)
                    .startTime(startTime)
                    .endTime(endTime)
                    .build());
        } else {
            log.warn("Visit at date: [{}], startTime: [{}], endTime: [{}], for Doctor: [{}] is not available",
                    day, startTime, endTime, doctor);
            throw new VisitInTakenTimePeriodException("Visit day and time is not available!");
        }
    }


    private boolean isVisitInAvailableTime(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Visit> conflictingVisits = visitDAO.findConflictingVisits(doctor, day, startTime, endTime);
        if (conflictingVisits.isEmpty()) {
            log.info("no conflicting visits found");
        }
        return conflictingVisits.isEmpty();
    }


    private boolean isDoctorAvailableAtTime(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {
        for (DoctorAvailability availability : doctor.getAvailabilities()) {
            if (availability.getDay().equals(day)
                    && !startTime.isBefore(availability.getStartTime())
                    && !endTime.isAfter(availability.getEndTime())) {
                return true;
            }
        }
        return false;
    }
}
