package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.NoteDTO;
import code.medconnect.domain.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteMapper {

    NoteDTO map(final Note note);

    Note map(final NoteDTO noteDTO);
}
