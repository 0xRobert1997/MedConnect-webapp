package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.AddressDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.domain.Address;
import code.medconnect.domain.Doctor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address map(final AddressDTO addressDTO);

}
