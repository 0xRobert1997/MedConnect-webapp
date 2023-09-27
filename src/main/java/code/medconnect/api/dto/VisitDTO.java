package code.medconnect.api.dto;

import code.medconnect.domain.Note;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
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
