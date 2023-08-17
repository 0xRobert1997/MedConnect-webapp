package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DiseaseDAO;
import code.medconnect.domain.Disease;
import code.medconnect.infrastructure.database.entity.DiseaseEntity;
import code.medconnect.infrastructure.database.repository.jpa.DiseaseJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DiseaseEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@AllArgsConstructor
public class DiseaseRepository implements DiseaseDAO {

    private final DiseaseJpaRepository diseaseJpaRepository;

    private final DiseaseEntityMapper diseaseEntityMapper;

    @Override
    public List<Disease> findDiseasesByPatientPesel(String pesel) {
        return diseaseJpaRepository.findByPatientPesel(pesel).stream()
                .map(diseaseEntityMapper::map)
                .toList();
    }

    @Override
    public Disease saveDisease(Disease disease) {
        DiseaseEntity toSave = diseaseEntityMapper.map(disease);
        DiseaseEntity saved = diseaseJpaRepository.save(toSave);
        return diseaseEntityMapper.map(saved);

    }
}
