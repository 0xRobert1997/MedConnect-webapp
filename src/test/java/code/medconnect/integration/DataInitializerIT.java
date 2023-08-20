package code.medconnect.integration;

import code.medconnect.business.DoctorService;
import code.medconnect.business.PatientService;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Disease;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.infrastructure.database.repository.DiseaseRepository;
import code.medconnect.infrastructure.database.repository.DoctorRepository;
import code.medconnect.integration.configuration.AbstractIT;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Set;


@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializerIT extends AbstractIT {


    private DoctorService doctorService;
    private PatientService patientService;
    private VisitService visitService;

    @Test
    void DataInitializationTest() throws Exception {

        List<Patient> patients = patientService.findAll();
        Set<Doctor> doctors = doctorService.findAll();
        List<Visit>visits = visitService.findAll();


        Assertions.assertEquals(2, patients.size());
        Assertions.assertEquals(2, doctors.size());
        Assertions.assertEquals(6, visits.size());


    }
}
