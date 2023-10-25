package code.medconnect.api.dto;

import code.medconnect.domain.Note;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class VisitDTO {
    Integer visitId;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;
    boolean canceled;
    List<Note> notes;
    Integer patientId;
    Integer doctorId;
}
