package code.medconnect.business;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.dao.DiseaseDAO;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.domain.exception.NotFoundException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class PatientService {

    PatientDAO patientDAO;
    DiseaseDAO diseaseDAO;
    VisitDAO visitDAO;

    PatientMapper patientMapper;


    @Transactional
    public void createPatient(PatientDTO patientDTO) {

        Patient patient = patientMapper.map(patientDTO);
        patientDAO.savePatient(patient);
    }

    @Transactional
    public void makeNewDiseaseForPatient(
            String pesel,
            String diseaseName,
            String diseaseDescription,
            OffsetDateTime diagnosisDateTime
    ) {
        Disease newDisease = Disease.builder()
                .diseaseName(diseaseName)
                .diagnosisDate(diagnosisDateTime)
                .description(diseaseDescription)
                .build();
        patientDAO.addDisease(pesel, newDisease);
    }

    @Transactional
    public Set<Disease> getPatientsDiseases(String pesel) {
        return patientDAO.findByPesel(pesel)
                .map(Patient::getDiseases)
                .orElseThrow(() -> new NotFoundException("Patient with pesel: " + pesel + " doesn't exist"));
    }

    @Transactional
    public List<Visit> getPatientsVisits(String pesel) {
        return visitDAO.findVisitByPatientPesel(pesel);
    }
}
