package code.medconnect.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoctorAvailability {

    Integer doctorAvailabilityId;
    Doctor doctor;
    String dayOfWeek;
    LocalTime startTime;
    LocalTime endTime;



}
