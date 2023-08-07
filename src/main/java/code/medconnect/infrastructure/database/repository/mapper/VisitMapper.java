package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitMapper {

    Visit map(VisitEntity visitEntity);
}
