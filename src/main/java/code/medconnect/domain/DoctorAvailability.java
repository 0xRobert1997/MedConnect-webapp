package code.medconnect.domain;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {

    Integer doctorAvailabilityId;
    Doctor doctor;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;



}
