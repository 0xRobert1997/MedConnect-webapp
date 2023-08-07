package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.VisitMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
@AllArgsConstructor
public class VisitRepository implements VisitDAO {

    private final VisitJpaRepository visitJpaRepository;

    private final VisitMapper visitMapper;


    @Override
    public List<Visit> findVisitByPatientPesel(String patientPesel) {
        return null;
    }

    @Override
    public void saveVisit(Date date, String startHour, String endHour, String patientEmail, String DoctorEmail) {

    }

    @Override
    public void saveNote(String noteContent, Visit visit) {

    }
}
