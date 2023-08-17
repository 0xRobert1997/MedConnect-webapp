package code.medconnect.business.dao;

import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;

import java.util.Optional;

public interface PatientDAO {

    Optional<Patient> findPatientWithVisits(Integer patientId);

    Optional<Patient> findByPesel(String pesel);

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findById(Integer id);

    Patient savePatient(Patient patient);


    void addDisease(String pesel, Disease disease);

    Optional<Patient> findByPeselWithDiseases(String patientPesel);
}
