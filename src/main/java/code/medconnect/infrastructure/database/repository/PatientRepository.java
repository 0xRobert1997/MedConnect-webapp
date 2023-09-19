package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DiseaseEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.PatientEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class PatientRepository implements PatientDAO {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientEntityMapper patientEntityMapper;
    private final DiseaseEntityMapper diseaseEntityMapper;



    @Override
    public Optional<Patient> findByPesel(String pesel) {
        return patientJpaRepository.findByPesel(pesel).map(patientEntityMapper::map);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email).map(patientEntityMapper::map);
    }

    @Override
    public Optional<Patient> findById(Integer id) {
        return patientJpaRepository.findById(id).map(patientEntityMapper::map);

    }
    @Override
    public List<Patient> findAll() {
        return patientJpaRepository.findAll()
                .stream()
                .map(patientEntityMapper::map)
                .toList();
    }

    @Override
    public Patient savePatient(Patient patient) {
        PatientEntity toSave = patientEntityMapper.map(patient);
        PatientEntity saved = patientJpaRepository.save(toSave);
        return patientEntityMapper.map(saved);
    }

    @Override
    public void addDisease(String pesel, Disease disease) {
        Optional<PatientEntity> patient = patientJpaRepository.findByPesel(pesel);
        patient.orElseThrow(() -> new NotFoundException("Patient with pesel: " + pesel + " not found"));
        PatientEntity patientEntity = patient.get();
        Set<DiseaseEntity> diseasesSet = patientEntity.getDiseases();
        DiseaseEntity diseaseEntity = diseaseEntityMapper.map(disease);
        diseasesSet.add(diseaseEntity);
        patientJpaRepository.save(patientEntity);
    }

    @Override
    public Patient findByPeselWithDiseases(String patientPesel) {
        Optional<PatientEntity> patientEntityOpt = patientJpaRepository.findByPesel(patientPesel);

        if (patientEntityOpt.isPresent()) {
            return mapWithDiseases(patientEntityOpt.get());
        } else {
            throw new NotFoundException("Patient with pesel: " + patientPesel + " doesn't exist");
        }
    }



    private Patient mapWithDiseases(PatientEntity entity) {
        Set<Disease> mappedDiseases = new HashSet<>();

        for (DiseaseEntity disease : entity.getDiseases()) {
            Disease mapped = diseaseEntityMapper.map(disease);
            mappedDiseases.add(mapped);
        }
        return  Patient.builder()
                    .patientId(entity.getPatientId())
                    .name(entity.getName())
                    .pesel(entity.getPesel())
                    .dateOfBirth(entity.getDateOfBirth())
                    .diseases(mappedDiseases)
                    .build();
    }



}
