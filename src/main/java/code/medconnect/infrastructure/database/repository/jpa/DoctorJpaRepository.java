package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.DoctorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorJpaRepository extends JpaRepository<DoctorEntity, Integer> {

    Optional<DoctorEntity> findByEmail(String email);


/*    @Query("""
            SELECT d FROM DoctorEntity d
            JOIN FETCH d.visits
            WHERE d.doctorId = :doctorId
            """)
    Optional<DoctorEntity> findDoctorWithVisits(@Param("doctorId") Integer doctorId);*/


}
