package code.medconnect.business.dao;

import code.medconnect.domain.Disease;

import java.util.List;

public interface DiseaseDAO {


    Disease saveDisease(Disease disease);

    List<Disease> findAll();

}
