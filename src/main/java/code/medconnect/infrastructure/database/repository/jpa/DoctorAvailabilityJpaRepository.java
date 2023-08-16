package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface DoctorAvailabilityJpaRepository extends JpaRepository<DoctorAvailabilityEntity, Integer> {



    List<DoctorAvailabilityEntity> findByDoctorId(Integer doctorId);

    Page<DoctorAvailabilityEntity> findAllByDoctorId(Integer doctorId, Pageable pageable);
}
