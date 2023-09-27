package code.medconnect.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class Visit {

    Integer visitId;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;
    boolean canceled;
    List<Note> notes;
    Integer patientId;
    Integer doctorId;

}
