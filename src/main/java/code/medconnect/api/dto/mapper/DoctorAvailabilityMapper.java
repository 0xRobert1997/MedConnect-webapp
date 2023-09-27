package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.domain.DoctorAvailability;
import org.mapstruct.Mapper;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface DoctorAvailabilityMapper {

    DoctorAvailability map(final DoctorAvailabilityDTO doctorAvailabilityDTO);

 //   DoctorAvailabilityDTO map(final DoctorAvailability doctorAvailability);



    default DoctorAvailabilityDTO map(DoctorAvailability availability) {
        return DoctorAvailabilityDTO.builder()
                .day(availability.getDay())
                .timeSlots(availability.getTimeSlots())
                .build();
    }

}
