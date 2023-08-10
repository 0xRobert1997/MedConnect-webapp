package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.domain.exception.PatientNotFoundException;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DiseaseMapper;
import code.medconnect.infrastructure.database.repository.mapper.PatientMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientMapper patientMapper;
    private final DiseaseMapper diseaseMapper;

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email).map(patientMapper::map);
    }

    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientJpaRepository.findByPesel(pesel).map(patientMapper::map);
    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientMapper.map(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientMapper.map(saved);
    }

    @Override
    public void addDisease(String pesel, Disease disease) {
        Optional<PatientEntity> patient = patientJpaRepository.findByPesel(pesel);
        patient.orElseThrow(() -> new PatientNotFoundException("Patient with pesel: " + pesel + " not found"));
        PatientEntity patientEntity = patient.get();
        Set<DiseaseEntity> diseasesSet = patientEntity.getDiseases();
        DiseaseEntity diseaseEntity = diseaseMapper.map(disease);
        diseasesSet.add(diseaseEntity);
        patientJpaRepository.save(patientEntity);
    }






}
