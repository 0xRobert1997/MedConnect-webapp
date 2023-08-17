package code.medconnect.infrastructure.database.repository.mapper;

import code.medconnect.domain.Note;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitEntityMapper {


    Visit map(VisitEntity visitEntity);

    @Mapping(target = "notes", ignore = true)
    Visit mapWithoutNotes(VisitEntity visitEntity);


    VisitEntity map(Visit visit);
}
