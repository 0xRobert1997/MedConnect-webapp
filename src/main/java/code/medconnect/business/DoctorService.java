package code.medconnect.business;

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

@Slf4j
@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorDAO doctorDAO;
    private final VisitDAO visitDAO;
    private final PatientDAO patientDAO;

    private final DoctorAvailabilityDAO doctorAvailabilityDAO;


    private static final LocalTime START_TIME = LocalTime.of(8, 0);
    private static final LocalTime END_TIME = LocalTime.of(16, 0);


    @Transactional
    public Set<Doctor> findAll() {
        return doctorDAO.findAll();

    }

    @Transactional
    public Doctor findByEmail(String email) {
        return doctorDAO.findByEmail(email);
    }

    @Transactional
    public void saveAvailAbility(Doctor doctor, LocalDate day) {
        DoctorAvailability ava = DoctorAvailability.builder()
                .doctorId(doctor.getDoctorId())
                .day(day)
                .startTime(START_TIME)
                .endTime(END_TIME)
                .build();
        doctorAvailabilityDAO.saveAvailability(ava);
    }


    @Transactional
    public void deleteAvailAbility(DoctorAvailability doctorAvailability) {
        doctorAvailabilityDAO.deleteAvailability(doctorAvailability);
    }


    @Transactional
    public Map<Visit, Patient> getDoctorsVisitsWithPatients(Integer doctorId) {
        List<Visit> visits = visitDAO.findVisitsByDoctorId(doctorId);


        Map<Visit, Patient> visitsWithPatients = new HashMap<>();

        for (Visit visit : visits) {
            Integer patientId = visit.getPatientId();
            Patient patient = patientDAO.findById(patientId)
                    .orElseGet(() -> Patient.builder().build());

            visitsWithPatients.put(visit, patient);
        }

        return visitsWithPatients;
    }


}
