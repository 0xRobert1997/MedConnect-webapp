package code.medconnect.business;

import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.*;
import code.medconnect.domain.exception.VisitInTakenTimePeriodException;
import code.medconnect.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.List;
@Slf4j
@Service
@AllArgsConstructor
public class VisitService {

    private final VisitDAO visitDAO;


    @Transactional
    public void cancelVisit(Integer visitId) {
        Visit visit = visitDAO.findVisitById(visitId)
                .orElseThrow(() -> new NotFoundException("Visit with ID " + visitId + " not found"));

        visitDAO.cancelVisit(visit);
        visitDAO.saveVisit(visit);
        log.info("Canceled visit with id: [{}]", visitId);
    }
    @Transactional
    public void addNoteToVisit(Integer visitId, String noteContent) {

        Visit visit = visitDAO.findVisitById(visitId)
                .orElseThrow(() -> new NotFoundException("Visit with ID " + visitId + " not found"));

        Note.builder()
                .visit(visit)
                .noteContent(noteContent)
                .dateTime(OffsetDateTime.now())
                .build();
/*        note.setVisit(visit);
        visit.setNote(note);*/
        log.info("Added note to visit with id: [{}]", visitId);
        visitDAO.saveVisit(visit);
    }


    @Transactional
    public Visit makeVisit(Patient patient, Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {

        boolean dateAndTimeInDoctorAvailability = isDoctorAvailableAtTime(doctor, day, startTime, endTime);
        boolean visitTimeAvailable = areVisitSlotsAvailable(doctor, day, startTime, endTime);

        if (dateAndTimeInDoctorAvailability && visitTimeAvailable) {
            return visitDAO.saveVisit(Visit.builder()
                    .patient(patient)
                    .doctor(doctor)
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

    @Transactional
    private boolean areVisitSlotsAvailable(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Visit> conflictingVisits = visitDAO.findConflictingVisits(doctor, day, startTime, endTime);
        if (conflictingVisits.isEmpty()) {
            log.info("no conflicting visits found");
        }
        return conflictingVisits.isEmpty();
    }

    @Transactional
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
