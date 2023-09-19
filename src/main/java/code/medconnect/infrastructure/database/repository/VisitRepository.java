package code.medconnect.infrastructure.database.repository;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.NoteJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.NoteEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.VisitEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class VisitRepository implements code.medconnect.business.dao.VisitDAO {

    private final VisitJpaRepository visitJpaRepository;
    private final DoctorJpaRepository doctorJpaRepository;
    private final NoteJpaRepository noteJpaRepository;

    private final VisitEntityMapper visitEntityMapper;
    private final NoteEntityMapper noteEntityMapper;
    private final DoctorEntityMapper doctorEntityMapper;


    @Override
    public Visit saveVisit(Visit visit) {
        VisitEntity toSave = visitEntityMapper.map(visit);
        VisitEntity saved = visitJpaRepository.save(toSave);
        return visitEntityMapper.map(saved);
    }


    @Override
    public void cancelVisit(Visit visit) {
        visitJpaRepository.cancelVisitById(visit.getVisitId());
    }


    @Override
    public List<Visit> findVisitsByDoctorId(Integer doctorId) {
        return visitJpaRepository.findByDoctorId(doctorId)
                .stream()
                .map(visitEntityMapper::map)
                .toList();
    }

    @Override
    public Optional<Visit> findVisitById(Integer id) {
        return visitJpaRepository.findById(id)
                .map(visitEntityMapper::map);
    }

    @Override
    public List<Visit> findByDoctorAndDay(Doctor doctor, LocalDate day) {
        return visitJpaRepository.findByDoctorIdAndDay(doctor.getDoctorId(), day).stream()
                .map(visitEntityMapper::map)
                .toList();
    }

    @Override
    public Set<Visit> findByPatientId(Integer patientId) {
        return visitJpaRepository.findByPatientId(patientId)
                .stream().map(this::mapWithNotes)
                .collect(Collectors.toSet());
    }

    private Visit mapWithNotes(VisitEntity visitEntity) {
        Visit visit = Visit.builder()
                .visitId(visitEntity.getVisitId())
                .patientId(visitEntity.getPatientId())
                .day(visitEntity.getDay())
                .doctorId(visitEntity.getDoctorId())
                .startTime(visitEntity.getStartTime())
                .endTime(visitEntity.getEndTime())
                .canceled(visitEntity.isCanceled())
                .build();
        List<Note> notes = new ArrayList<>();

        for (NoteEntity noteEntity : visitEntity.getNotes()) {
            notes.add(mapNoteWithoutVisit(noteEntity));
        }
        visit.setNotes(notes);
        return visit;
    }

    private Note mapNoteWithoutVisit(NoteEntity noteEntity) {
        return Note.builder()
                .noteId(noteEntity.getNoteId())
                .dateTime(noteEntity.getDateTime())
                .noteContent(noteEntity.getNoteContent())
                .build();
    }

    @Override
    public List<Visit> findByDoctorId(Integer doctorId) {
        return visitJpaRepository.findByDoctorId(doctorId)
                .stream().map(visitEntityMapper::map)
                .toList();
    }

    @Override
    public List<Visit> findAll() {
        return visitJpaRepository.findAll()
                .stream()
                .map(visitEntityMapper::map)
                .toList();
    }


    @Override
    public List<Visit> findConflictingVisits(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {
        List<Visit> byDoctorAndDay = findByDoctorAndDay(doctor, day);

        return byDoctorAndDay.stream()
                .filter(visit ->
                        (endTime.isAfter(visit.getStartTime()) && startTime.isBefore(visit.getEndTime()))
                                || (startTime.isBefore(visit.getEndTime()) && endTime.isAfter(visit.getStartTime()))
                                || (startTime.equals(visit.getStartTime()) && endTime.equals(visit.getEndTime())))
                .collect(Collectors.toList());
    }


}
