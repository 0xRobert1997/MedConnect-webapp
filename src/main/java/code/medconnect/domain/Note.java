package code.medconnect.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Note {

    Integer noteId;
    String noteContent;
    OffsetDateTime dateTime;
    Visit visit;

}


