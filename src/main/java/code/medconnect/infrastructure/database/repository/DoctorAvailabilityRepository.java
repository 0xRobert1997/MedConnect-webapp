package code.medconnect.infrastructure.database.repository;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.mapper.DoctorAvailabilityMapper;
import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DoctorAvailabilityRepository implements DoctorAvailabilityDAO {

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;
    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    @Override
    public List<DoctorAvailability> findByDoctorId(Integer doctorId) {
        return doctorAvailabilityJpaRepository.findByDoctorId(doctorId)
                .stream()
                .map(doctorAvailabilityEntityMapper::map)
                .toList();
    }

    @Override
    public Page<DoctorAvailabilityDTO> getDoctorAvailabilityPage(Integer doctorId, Pageable pageable) {

        Page<DoctorAvailabilityEntity> availabilityPage
                = doctorAvailabilityJpaRepository.findAllByDoctorId(doctorId, pageable);

        List<DoctorAvailabilityDTO> doctorAvailabilityDTOs = availabilityPage.getContent()
                .stream()
                .map(doctorAvailabilityEntityMapper::map)
                .map(doctorAvailabilityMapper::map)
                .toList();

        return new PageImpl<>(doctorAvailabilityDTOs, pageable, availabilityPage.getTotalElements());
    }


/*    @Override
    public Set<DoctorAvailability> findByDoctorEmail(String email) {
        return doctorAvailabilityJpaRepository.findByDoctorEmail(email)
                .stream()
                .map(doctorAvailabilityEntityMapper::map)
                .collect(Collectors.toSet());
    }*/
}
