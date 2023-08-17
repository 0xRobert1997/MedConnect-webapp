package code.medconnect.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

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
}
