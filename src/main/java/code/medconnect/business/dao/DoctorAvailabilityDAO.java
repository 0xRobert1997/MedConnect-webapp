package code.medconnect.business.dao;

import code.medconnect.domain.DoctorAvailability;

import java.util.List;

public interface DoctorAvailabilityDAO {

    List<DoctorAvailability> findByDoctorEmail(final String doctorEmail);
}
