package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Doctor;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DoctorEntityMapper {

    @Mapping(target = "visits", ignore = true)
    Doctor map(DoctorEntity doctorEntity);

    DoctorEntity map(Doctor doctorEntity);


}
