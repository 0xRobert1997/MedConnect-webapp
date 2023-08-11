package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DoctorAvailabilityRepository implements DoctorAvailabilityDAO {

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;

    @Override
    public Set<DoctorAvailability> findByDoctorEmail(String email) {
        return doctorAvailabilityJpaRepository.findByDoctorEmail(email)
                .stream()
                .map(doctorAvailabilityEntityMapper::map)
                .collect(Collectors.toSet());
    }
}
