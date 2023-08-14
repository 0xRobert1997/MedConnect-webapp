package code.medconnect.business;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.exception.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO doctorDAO;
    private final VisitDAO visitDAO;
    private final DoctorMapper doctorMapper;
    private final VisitMapper visitMapper;

    @Transactional
    public Set<Doctor> findAllDoctors() {
        return doctorDAO.findAll();
    }
    @Transactional
    public DoctorDTO findByEmail(String email) {
        return doctorDAO.findByEmail(email)
                .map(doctorMapper::map)
                .orElseThrow(() -> new NotFoundException("Doctor with email: " + email + " does not exist"));
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

    @Transactional
    public List<VisitDTO> getDoctorsVisits(String email) {
        return visitDAO.findVisitByDoctorEmail(email).stream()
                .map(visitMapper::map)
                .toList();
    }



}
