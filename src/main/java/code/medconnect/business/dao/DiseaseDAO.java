package code.medconnect.business.dao;

import code.medconnect.domain.Disease;

import java.util.List;

public interface DiseaseDAO {

    List<Disease> findDiseasesByPatientPesel(String pesel);
}
