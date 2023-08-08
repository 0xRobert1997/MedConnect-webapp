package code.medconnect.business.dao;

import code.medconnect.domain.Note;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;

import java.util.Date;
import java.util.Optional;

public interface PatientDAO {

    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByPesel(String pesel);
    Patient savePatient(Patient patient);



}
