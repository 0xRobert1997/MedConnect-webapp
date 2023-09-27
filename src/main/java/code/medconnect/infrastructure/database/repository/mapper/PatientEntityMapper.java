package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PatientEntityMapper {


    @Mapping(target = "visits", ignore = true)
    @Mapping(target = "diseases", ignore = true)
    Patient map(PatientEntity entity);



    PatientEntity map(Patient patient);


}
