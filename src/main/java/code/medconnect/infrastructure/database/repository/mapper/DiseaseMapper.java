package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Disease;
import code.medconnect.domain.Doctor;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiseaseMapper {



    Disease map(DiseaseEntity diseaseEntity);


}
