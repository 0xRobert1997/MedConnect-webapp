package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.VisitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitJpaRepository extends JpaRepository<VisitEntity, Integer> {


    List<VisitEntity> findByDoctorIdAndDay(Integer doctorId, LocalDate day);

    List<VisitEntity> findByPatientId(Integer patientId);

    @Query("SELECT v FROM VisitEntity v WHERE v.doctorId = :doctorId")
    List<VisitEntity> findByDoctorId(Integer doctorId);

    @Query("""
            UPDATE VisitEntity vst
            SET vst.canceled = true
            WHERE vst.visitId = :visitId
            """)
    @Modifying(clearAutomatically = true)
    void cancelVisitById(@Param("visitId") Integer visitId);


}
