package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.PatientMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientMapper patientMapper;

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email);
    }

    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return Optional.empty();
    }

    @Override
    public Patient savePatient(Patient patient) {
        return null;
    }

    @Override
    public Visit saveVisit(Visit visit) {
        return null;
    }

    @Override
    public Visit cancelVisit(Visit visit) {
        return null;
    }
}
