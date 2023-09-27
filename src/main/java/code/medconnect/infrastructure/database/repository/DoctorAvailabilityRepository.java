package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class DoctorAvailabilityRepository implements DoctorAvailabilityDAO {

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;


    @Override
    public void saveAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity toSave = doctorAvailabilityEntityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.save(toSave);
    }

    @Override
    public void deleteAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity doctorAvailabilityEntity = doctorAvailabilityEntityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.delete(doctorAvailabilityEntity);
    }

    @Override
    public Page<DoctorAvailabilityEntity> getDoctorAvailabilityPage(Integer doctorId, Pageable pageable) {
        return doctorAvailabilityJpaRepository.findAllByDoctorId(doctorId, pageable);
    }

}
