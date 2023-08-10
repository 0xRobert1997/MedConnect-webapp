package code.medconnect.business;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.exception.DoctorNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {

    DoctorDAO doctorDAO;

    @Transactional
    public Set<Doctor> findAllDoctors() {
        return doctorDAO.findAll();
    }
    @Transactional
    public Doctor findDoctorByEmail(String email) {
        return doctorDAO.findByEmail(email)
                .orElseThrow(() -> new DoctorNotFoundException("Doctor with email: " + email + " does not exist"));
    }

    @Transactional
    public void saveAvailAbility(Doctor doctor, LocalDate day, LocalTime startTime, LocalTime endTime) {
        DoctorAvailability doctorAvailability = DoctorAvailability.builder()
                .doctor(doctor)
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        doctorDAO.saveAvailability(doctorAvailability);

    }
    @Transactional
    public void deleteAvailAbility(DoctorAvailability doctorAvailability) {
        doctorDAO.deleteAvailability(doctorAvailability);
    }



}
