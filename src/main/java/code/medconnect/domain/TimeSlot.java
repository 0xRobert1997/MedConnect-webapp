package code.medconnect.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@With
@NoArgsConstructor
@AllArgsConstructor
public class TimeSlot {

    private LocalDate day;
    private LocalTime startTime;
    private LocalTime endTime;
    private boolean isTaken;
}
