package code.medconnect.business.dao;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DoctorAvailabilityDAO {

    //  Set<DoctorAvailability> findByDoctorEmail(final String doctorEmail);

    List<DoctorAvailability> findByDoctorId(Integer doctorId);

    Page<DoctorAvailabilityEntity> getDoctorAvailabilityPage(Integer doctorId, Pageable pageable);

    void saveAvailability(DoctorAvailability doctorAvailability);

    void deleteAvailability(DoctorAvailability doctorAvailability);
}
