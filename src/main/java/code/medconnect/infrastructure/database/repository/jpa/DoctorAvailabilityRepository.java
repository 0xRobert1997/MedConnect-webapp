package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityRepository extends JpaRepository<DoctorAvailabilityEntity, Integer> {
}
