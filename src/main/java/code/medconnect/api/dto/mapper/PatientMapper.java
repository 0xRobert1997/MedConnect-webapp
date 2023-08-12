package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "base64Image", ignore = true)
    PatientDTO map(final Patient patient);
}
