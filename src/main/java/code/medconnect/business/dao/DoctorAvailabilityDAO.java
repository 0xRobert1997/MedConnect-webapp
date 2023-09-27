package code.medconnect.business.dao;

import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface DoctorAvailabilityDAO {


    Page<DoctorAvailabilityEntity> getDoctorAvailabilityPage(Integer doctorId, Pageable pageable);

    void saveAvailability(DoctorAvailability doctorAvailability);

    void deleteAvailability(DoctorAvailability doctorAvailability);
}
