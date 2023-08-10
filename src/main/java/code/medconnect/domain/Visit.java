package code.medconnect.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visit {


    Integer visitId;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;
    boolean cancelled;
    Note note;
    Patient patient;
    Doctor doctor;


}
