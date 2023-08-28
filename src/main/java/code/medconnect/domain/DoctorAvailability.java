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
public class DoctorAvailability {

    Integer doctorAvailabilityId;
    Integer doctorId;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;
    private List<TimeSlot> timeSlots;

}
