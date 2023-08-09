package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.NoteDAO;
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

import java.util.Optional;

@Repository
@AllArgsConstructor
public class NoteRepository implements NoteDAO {

    private final NoteJpaRepository noteJpaRepository;
    private final NoteMapper noteMapper;
    private final VisitJpaRepository visitJpaRepository;
    private final VisitMapper visitMapper;

    @Override
    public Note saveNote(Note note, Visit visit) {
        NoteEntity toSave = noteMapper.map(note.withVisit(visit));
        NoteEntity saved = noteJpaRepository.save(toSave);
        return noteMapper.map(saved);
    }

    @Override
    public Optional<Note> findNoteByVisitId(Integer visitId) {
        return visitJpaRepository.findById(visitId)
                .map(VisitEntity::getNote)
                .map(noteMapper::map);
    }

}
