package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorAvailabilityEntityMapper {

    @Mapping(target = "timeSlots", ignore = true)
    DoctorAvailability map(DoctorAvailabilityEntity doctorAvailabilityEntity);

    DoctorAvailabilityEntity map(DoctorAvailability doctorAvailabilityEntity);
}
