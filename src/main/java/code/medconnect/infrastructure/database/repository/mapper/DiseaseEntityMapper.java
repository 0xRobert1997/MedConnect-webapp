package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Disease;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DiseaseEntityMapper {


    @Mapping(target = "patient", ignore = true)
    Disease map(DiseaseEntity diseaseEntity);

    DiseaseEntity map(Disease disease);


}
