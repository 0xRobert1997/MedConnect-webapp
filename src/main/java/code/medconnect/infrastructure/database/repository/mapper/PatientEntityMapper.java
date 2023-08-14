package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {

    Patient map(PatientEntity entity);

 //   @Mapping(source = "appUser", target = "appUserEntity")
    PatientEntity map(Patient patient);
}
