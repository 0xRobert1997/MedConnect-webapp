package code.medconnect.api.dto;

import code.medconnect.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoteDTO {

    Integer noteId;
    String noteContent;
    OffsetDateTime dateTime;
    Visit visit;

}
