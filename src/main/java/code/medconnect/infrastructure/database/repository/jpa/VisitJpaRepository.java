package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitJpaRepository extends JpaRepository<VisitEntity, Integer> {


    @Query("""
            UPDATE VisitEntity vst
            SET vst.canceled = true
            WHERE vst.visitId = :visitId
            """)
    @Modifying(clearAutomatically = true)
    void cancelVisitById(@Param("visitId") Integer visitId);



    @Query("""
            SELECT visit
            FROM VisitEntity visit
            WHERE visit.patient.pesel = :pesel
            """)
    @Modifying(clearAutomatically = true)
    List<VisitEntity> findVisitByPatientPesel(@Param("pesel") String pesel);

}
