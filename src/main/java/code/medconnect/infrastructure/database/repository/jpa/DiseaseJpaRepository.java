package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DiseaseJpaRepository extends JpaRepository<DiseaseEntity, Integer> {

    Set<DiseaseEntity> findByPatientPesel(final String pesel);
}
