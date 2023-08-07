package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.domain.Doctor;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {

    DoctorEntity findByEmail(String email);
}
