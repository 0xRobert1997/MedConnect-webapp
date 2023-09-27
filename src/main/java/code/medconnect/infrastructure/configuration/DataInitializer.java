package code.medconnect.infrastructure.configuration;

import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.repository.*;
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
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;


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
        diseaseRepository.saveDisease(DataInitializerData.diseaseFixture1());
        diseaseRepository.saveDisease(DataInitializerData.diseaseFixture2());
        diseaseRepository.saveDisease(DataInitializerData.diseaseFixture3());
    }

    private void initializerUsers() {
        appUserRepository.save(DataInitializerData.appUserEntityFixture1());
        appUserRepository.save(DataInitializerData.appUserEntityFixture2());
        appUserRepository.save(DataInitializerData.appUserEntityFixture3());
        appUserRepository.save(DataInitializerData.appUserEntityFixture4());
    }

    private void initializeVisits() {
        visitRepository.saveVisit(DataInitializerData.visitFixture1());
        visitRepository.saveVisit(DataInitializerData.visitFixture2());
        visitRepository.saveVisit(DataInitializerData.visitFixture3());
        visitRepository.saveVisit(DataInitializerData.visitFixture4());
        visitRepository.saveVisit(DataInitializerData.visitFixture5());
        visitRepository.saveVisit(DataInitializerData.visitFixture6());
    }

    private void initializeAvailabilities() {
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture1());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture2());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture3());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture4());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture5());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture6());
        doctorAvailabilityRepository.saveAvailability(DataInitializerData.availabilityFixture7());

    }

    private void initializePatients() {
        Patient patient1 = DataInitializerData.patientFixture1();
        Patient patient2 = DataInitializerData.patientFixture2();
        patientRepository.savePatient(patient1.withAppUser(
                AppUser.builder()
                        .id(1)
                        .build())
                .withDiseases(Set.of(
                        DataInitializerData.diseaseFixture1(),
                        DataInitializerData.diseaseFixture2())
                ));


        patientRepository.savePatient(patient2.withAppUser(
                AppUser.builder()
                        .id(2)
                        .build())
                .withDiseases(Set.of(
                        DataInitializerData.diseaseFixture3()
                ))
        );
    }

    private void initializeDoctors() {
        Doctor doctor1 = DataInitializerData.doctorFixture1();
        Doctor doctor2 = DataInitializerData.doctorFixture2();
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
