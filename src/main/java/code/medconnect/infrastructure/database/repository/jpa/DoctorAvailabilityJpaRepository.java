package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DoctorAvailabilityJpaRepository extends JpaRepository<DoctorAvailabilityEntity, Integer> {
  //  Set<DoctorAvailabilityEntity> findByDoctorEmail(String email);
}
