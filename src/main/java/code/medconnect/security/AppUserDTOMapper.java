package code.medconnect.security;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserDTOMapper {

    //  @Mapping(target = "active", constant = "true")
    AppUserDTO map(final AppUser appUser);
}
