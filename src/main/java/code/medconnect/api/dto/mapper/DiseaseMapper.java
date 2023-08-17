package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.DiseaseDTO;
import code.medconnect.domain.Disease;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DiseaseMapper {

    DiseaseDTO map(final Disease disease);

    Disease map(final DiseaseDTO diseaseDTO);
}
