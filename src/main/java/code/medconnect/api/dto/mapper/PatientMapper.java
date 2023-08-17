package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.domain.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "base64Image", ignore = true)
    @Mapping(target = "appUserDTO", ignore = true)
    PatientDTO map(final Patient patient);

    //  @Mapping(target = "visits", ignore = true)
    //   @Mapping(target = "diseases", ignore = true)
    // @Mapping(target = "user", ignore = true)
    @Mapping(source = "appUserDTO", target = "appUser")
    Patient map(final PatientDTO patientDTO);
}
