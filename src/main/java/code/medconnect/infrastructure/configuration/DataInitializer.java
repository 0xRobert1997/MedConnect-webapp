package code.medconnect.infrastructure.configuration;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.repository.DiseaseRepository;
import code.medconnect.infrastructure.database.repository.DoctorRepository;
import code.medconnect.infrastructure.database.repository.PatientRepository;
import code.medconnect.infrastructure.database.repository.VisitRepository;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final AppUserRepository appUserRepository;
    private final VisitRepository visitRepository;
    private final DiseaseRepository diseaseRepository;


    @Override
    public void run(String... args) throws Exception {

        initializerUsers();
        initializePatients();
        initializeDoctors();
        initializeAvailabilities();
        initializeVisits();
        initializeDiseases();


    }

    private void initializeDiseases() {
        diseaseRepository.saveDisease(DataInitializerFixtures.diseaseFixture1());
        diseaseRepository.saveDisease(DataInitializerFixtures.diseaseFixture2());
        diseaseRepository.saveDisease(DataInitializerFixtures.diseaseFixture3());
    }

    private void initializerUsers() {
        appUserRepository.save(DataInitializerFixtures.appUserEntityFixture1());
        appUserRepository.save(DataInitializerFixtures.appUserEntityFixture2());
        appUserRepository.save(DataInitializerFixtures.appUserEntityFixture3());
        appUserRepository.save(DataInitializerFixtures.appUserEntityFixture4());
    }

    private void initializeVisits() {
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture1());
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture2());
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture3());
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture4());
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture5());
        visitRepository.saveVisit(DataInitializerFixtures.visitFixture6());
    }

    private void initializeAvailabilities() {
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture1());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture2());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture3());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture4());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture5());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture6());
        doctorRepository.saveAvailability(DataInitializerFixtures.availabilityFixture7());

    }

    private void initializePatients() {
        Patient patient1 = DataInitializerFixtures.patientFixture1();
        Patient patient2 = DataInitializerFixtures.patientFixture2();
        patientRepository.savePatient(patient1.withAppUser(
                AppUser.builder()
                        .id(1)
                        .build())
                .withDiseases(Set.of(
                        DataInitializerFixtures.diseaseFixture1(),
                        DataInitializerFixtures.diseaseFixture2())
                ));


        patientRepository.savePatient(patient2.withAppUser(
                AppUser.builder()
                        .id(2)
                        .build())
                .withDiseases(Set.of(
                        DataInitializerFixtures.diseaseFixture3()
                ))
        );
    }

    private void initializeDoctors() {
        Doctor doctor1 = DataInitializerFixtures.doctorFixture1();
        Doctor doctor2 = DataInitializerFixtures.doctorFixture2();
        doctorRepository.saveDoctor(doctor1
                .withAppUser(AppUser.builder()
                        .id(3)
                        .build()));
         doctorRepository.saveDoctor(doctor2
                .withAppUser(AppUser.builder()
                        .id(4)
                        .build()));
    }


}
