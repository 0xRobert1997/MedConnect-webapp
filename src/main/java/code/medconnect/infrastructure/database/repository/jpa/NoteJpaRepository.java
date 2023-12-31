package code.medconnect.infrastructure.database.repository.jpa;

import code.medconnect.infrastructure.database.entity.NoteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteJpaRepository extends JpaRepository<NoteEntity, Integer> {
}
