package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.NoteDAO;
import code.medconnect.domain.Note;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.repository.jpa.NoteJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.NoteEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class NoteRepository implements NoteDAO {

    private final NoteJpaRepository noteJpaRepository;
    private final NoteEntityMapper noteEntityMapper;
    private final VisitJpaRepository visitJpaRepository;


    @Override
    public Note saveNote(Note note) {
        NoteEntity toSave = noteEntityMapper.map(note);
        NoteEntity saved = noteJpaRepository.save(toSave);
        return noteEntityMapper.map(saved);
    }


}
