package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.AddressDTO;
import code.medconnect.domain.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {

    Address map(final AddressDTO addressDTO);

}
