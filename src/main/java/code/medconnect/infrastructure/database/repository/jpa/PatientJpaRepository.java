package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientJpaRepository extends JpaRepository<PatientEntity, Integer> {

    Optional<PatientEntity> findByEmail(String email);

    Optional<PatientEntity> findByPesel(String pesel);

    @Query("""
            SELECT p FROM PatientEntity p
            JOIN FETCH p.visits
            WHERE p.patientId = :patientId
            """)
    Optional<PatientEntity> findPatientWithVisits(@Param("patientId") Integer patientId);

    @Query("""
            SELECT p from PatientEntity p
            JOIN FETCH p.diseases
            WHERE p.pesel = :patientPesel
            """)
    Optional<PatientEntity> findByPeselWithDiseases(@Param("patientPesel") String patientPesel);
}
