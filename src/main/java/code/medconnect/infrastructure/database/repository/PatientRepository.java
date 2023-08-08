package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.entity.PatientEntity;
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
        return patientJpaRepository.findByPesel(pesel);
    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientMapper.map(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientMapper.map(saved);
    }





}
