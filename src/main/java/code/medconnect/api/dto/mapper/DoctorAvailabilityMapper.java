package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.domain.DoctorAvailability;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DoctorAvailabilityMapper {

    DoctorAvailability map(final DoctorAvailabilityDTO doctorAvailabilityDTO);

    DoctorAvailabilityDTO map(final DoctorAvailability doctorAvailability);
}
