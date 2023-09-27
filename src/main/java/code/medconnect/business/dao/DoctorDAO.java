package code.medconnect.business.dao;

import code.medconnect.domain.Doctor;

import java.util.Set;

public interface DoctorDAO {

    Doctor findByEmail(String email);


    Doctor saveDoctor(Doctor doctor);

    Set<Doctor> findAll();

    Doctor findById(Integer doctorId);


}
