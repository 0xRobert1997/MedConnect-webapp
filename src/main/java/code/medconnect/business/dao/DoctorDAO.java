package code.medconnect.business.dao;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;

import java.util.Optional;

public interface DoctorDAO {

    Optional<Doctor> findByEmail(String email);
    void saveAvailability(DoctorAvailability doctorAvailability);
    void deleteAvailability(DoctorAvailability doctorAvailability);

    Doctor saveDoctor(Doctor doctor);


}
