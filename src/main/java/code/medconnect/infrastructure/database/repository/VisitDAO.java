package code.medconnect.infrastructure.database.repository;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class VisitDAO implements code.medconnect.business.dao.VisitDAO {

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
    public List<Visit> findVisitByPatientPesel(String patientPesel) {
        List<VisitEntity> visits = visitJpaRepository.findVisitByPatientPesel(patientPesel);
        return visits
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
        DoctorEntity doctorEntity = doctorEntityMapper.map(doctor);
        return visitJpaRepository.findByDoctorAndDay(doctorEntity, day).stream()
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
