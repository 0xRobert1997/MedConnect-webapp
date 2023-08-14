package code.medconnect.api.dto.mapper;

import code.medconnect.api.dto.VisitDTO;
import code.medconnect.domain.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {

    VisitDTO map(final Visit visit);

    Visit map(final VisitDTO visitDTO);

}
