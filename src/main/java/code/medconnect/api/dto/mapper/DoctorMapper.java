package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.domain.Doctor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "base64Image", ignore = true)
    DoctorDTO map(final Doctor doctor);
}
