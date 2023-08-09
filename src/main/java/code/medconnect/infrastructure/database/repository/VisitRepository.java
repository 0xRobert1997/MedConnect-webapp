package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.NoteJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.NoteMapper;
import code.medconnect.infrastructure.database.repository.mapper.VisitMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class VisitRepository implements VisitDAO {

    private final VisitJpaRepository visitJpaRepository;
    private final NoteJpaRepository noteJpaRepository;

    private final VisitMapper visitMapper;
    private final NoteMapper noteMapper;


    @Override
    public Visit saveVisit(Visit visit) {
        VisitEntity toSave = visitMapper.map(visit);
        VisitEntity saved = visitJpaRepository.save(toSave);
        return visitMapper.map(saved);
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
                .map(visitMapper::map)
                .toList();
    }

    public Optional<Visit> findVisitById(Integer id) {
        return visitJpaRepository.findById(id)
                .map(visitMapper::map);
    }

}
