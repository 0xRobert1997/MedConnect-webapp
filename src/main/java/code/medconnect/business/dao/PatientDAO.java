package code.medconnect.business.dao;

import code.medconnect.domain.Disease;
import code.medconnect.domain.Patient;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface PatientDAO {

    Optional<Patient> findByEmail(String email);

    Optional<Patient> findByPesel(String pesel);

    Patient savePatient(Patient patient);


    void addDisease(String pesel, Disease disease);
}
