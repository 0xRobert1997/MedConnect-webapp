package code.medconnect.api.dto;

import code.medconnect.domain.TimeSlot;
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

public class DoctorAvailabilityDTO {

    Integer doctorAvailabilityId;
    Integer doctorId;
    LocalDate day;
    LocalTime startTime;
    LocalTime endTime;
    List<TimeSlot> timeSlots;

}
