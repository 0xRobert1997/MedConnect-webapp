package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {

    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByPesel(String pesel);


}
