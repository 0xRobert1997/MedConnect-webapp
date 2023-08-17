package code.medconnect.security;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppUserMapper {

    AppUser map(final AppUserDTO appUserDTO);

    @Mapping(target = "active", constant = "true")
    AppUserEntity map(final AppUser appUser);

    AppUser map(final AppUserEntity appUserEntity);
}
