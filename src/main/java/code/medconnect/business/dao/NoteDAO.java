package code.medconnect.business.dao;

import code.medconnect.domain.Note;
import code.medconnect.infrastructure.database.entity.NoteEntity;

import java.util.List;
import java.util.Optional;

public interface NoteDAO {


    Note saveNote(Note note);

    Optional<List<NoteEntity>> findNoteByVisitId(Integer visitId);
}
