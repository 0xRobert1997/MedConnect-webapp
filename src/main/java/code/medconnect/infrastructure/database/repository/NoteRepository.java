package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.NoteDAO;
import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.NoteJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.NoteEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.VisitEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class NoteRepository implements NoteDAO {

    private final NoteJpaRepository noteJpaRepository;
    private final NoteEntityMapper noteEntityMapper;
    private final VisitJpaRepository visitJpaRepository;
    private final VisitEntityMapper visitEntityMapper;

    @Override
    public Note saveNote(Note note, Visit visit) {
        NoteEntity toSave = noteEntityMapper.map(note.withVisit(visit));
        NoteEntity saved = noteJpaRepository.save(toSave);
        return noteEntityMapper.map(saved);
    }

    @Override
    public Optional<Note> findNoteByVisitId(Integer visitId) {
        return visitJpaRepository.findById(visitId)
                .map(VisitEntity::getNote)
                .map(noteEntityMapper::map);
    }

}
