package code.medconnect.business.dao;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;

public interface DoctorDAO {

    Doctor findByEmail(String email);
    void saveAvailability(DoctorAvailability doctorAvailability);
    void deleteAvailability(DoctorAvailability doctorAvailability);

    Doctor saveDoctor(Doctor doctor);


}
