package code.medconnect.business;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorAvailabilityMapper;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO doctorDAO;
    private final VisitDAO visitDAO;
    private final PatientDAO patientDAO;

    private final DoctorAvailabilityDAO doctorAvailabilityDAO;
    private final DoctorMapper doctorMapper;
    private final VisitMapper visitMapper;
    private final PatientMapper patientMapper;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    @Transactional
    public Set<Doctor> findAllDoctors() {
        return doctorDAO.findAll();

    }

    @Transactional
    public Doctor findByEmail(String email) {
        return doctorDAO.findByEmail(email);
    }

    @Transactional
    public void saveAvailAbility(DoctorDTO doctorDTO, LocalDate day, LocalTime startTime, LocalTime endTime) {
        DoctorAvailability ava = DoctorAvailability.builder()
                .doctorId(doctorDTO.getDoctorId())
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build();
        doctorAvailabilityDAO.saveAvailability(ava);
    }


    @Transactional
    public void deleteAvailAbility(DoctorAvailability doctorAvailability) {
        doctorDAO.deleteAvailability(doctorAvailability);
    }

    @Transactional
    public List<Visit> getDoctorsVisits(Integer doctorId) {
        return visitDAO.findVisitsByDoctorId(doctorId);
    }

    @Transactional
    public List<DoctorAvailability> getDoctorAvailabilities(String doctorEmail) {
        Doctor doctor = findByEmail(doctorEmail);
        return doctorAvailabilityDAO.findByDoctorId(doctor.getDoctorId());
    }

    @Transactional
    public Map<VisitDTO, PatientDTO> getDoctorsVisitsWithPatients(Integer doctorId) {
        List<VisitDTO> visits = visitDAO.findVisitsByDoctorId(doctorId)
                .stream()
                .map(visitMapper::map)
                .toList();

        Map<VisitDTO, PatientDTO> visitsWithPatients = new HashMap<>();

        for (VisitDTO visit : visits) {
            Integer patientId = visit.getPatientId();
            Patient patient = patientDAO.findById(patientId)
                    .orElseGet(() -> Patient.builder().build());
            PatientDTO patientDTO = patientMapper.map(patient);
            visitsWithPatients.put(visit, patientDTO);
        }

        return visitsWithPatients;
    }


}
