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
    public Set<DoctorDTO> findAllDoctors() {
        return doctorDAO.findAll().stream()
                .map(doctorMapper::map)
                .collect(Collectors.toSet());
    }

    @Transactional
    public DoctorDTO findByEmail(String email) {
        Doctor doctor = doctorDAO.findByEmail(email);
        return doctorMapper.map(doctor);
    }

    @Transactional
    public void saveAvailAbility(DoctorDTO doctorDTO, LocalDate day, LocalTime startTime, LocalTime endTime) {
        Set<DoctorAvailability> availabilities = doctorDTO.getAvailabilities();
        availabilities.add(DoctorAvailability.builder()
                .doctorId(doctorDTO.getDoctorId())
                .day(day)
                .startTime(startTime)
                .endTime(endTime)
                .build());
        doctorDAO.saveDoctor(doctorMapper.map(doctorDTO));

    }


    @Transactional
    public void deleteAvailAbility(DoctorAvailability doctorAvailability) {
        doctorDAO.deleteAvailability(doctorAvailability);
    }

    @Transactional
    public List<VisitDTO> getDoctorsVisits(Integer doctorId) {
        return visitDAO.findVisitsByDoctorId(doctorId)
                .stream()
                .map(visitMapper::map)
                .toList();
    }

    @Transactional
    public List<DoctorAvailabilityDTO> getDoctorAvailabilities(String doctorEmail) {
        DoctorDTO doctorDTO = findByEmail(doctorEmail);
        return doctorAvailabilityDAO.findByDoctorId(doctorDTO.getDoctorId())
                .stream().map(doctorAvailabilityMapper::map)
                .toList();
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
