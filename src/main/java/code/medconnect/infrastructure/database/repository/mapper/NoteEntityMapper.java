package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Note;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface NoteEntityMapper {

    Note map(NoteEntity noteEntity);
    NoteEntity map(Note noteEntity);
}
