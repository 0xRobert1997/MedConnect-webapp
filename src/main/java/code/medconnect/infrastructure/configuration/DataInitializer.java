package code.medconnect.infrastructure.configuration;

import code.medconnect.domain.Address;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.repository.DoctorRepository;
import code.medconnect.infrastructure.database.repository.PatientRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

@Component
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class DataInitializer implements CommandLineRunner {

    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;

    private final PatientJpaRepository patientJpaRepository;

    @Override
    public void run(String... args) throws Exception {

       initializePatients();
       initializeDoctors();
    }

    private void initializePatients() {
        Patient patient1 = getPatient1();
        Patient patient2 = getPatient2();

        patientRepository.savePatient(patient1);
        patientRepository.savePatient(patient2);
    }

    private void initializeDoctors() {
        Doctor doctor1 = getDoctor1();
        Doctor doctor2 = getDoctor2();

        doctorRepository.saveDoctor(doctor1);
        doctorRepository.saveDoctor(doctor2);
    }

    private byte[] getPhotoBytes(String imagePath) {
        try {
            ClassPathResource resource = new ClassPathResource(imagePath);
            return IOUtils.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.warn("Cannot load photo correctly");
            e.printStackTrace();
            return new byte[0];
        }
    }


    private Patient getPatient1() {
        return Patient.builder()
                .name("SpongeBob")
                .surname("Kanciastoporty")
                .pesel("21212121211")
                .phone("123-321-123")
                .dateOfBirth(LocalDate.of(1995, 10, 10))
                .sex("male")
                .photoData(getPhotoBytes("static/images/SpongeBob.jpg"))
                .email("spondgebob@gmail.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Patient getPatient2() {
        return Patient.builder()
                .name("Patrick")
                .surname("Star")
                .pesel("12121212121")
                .phone("321-321-321")
                .dateOfBirth(LocalDate.of(1996, 10, 10))
                .sex("male")
                .photoData(getPhotoBytes("static/images/Patrick.jpg"))
                .email("patrick@gmail.com")
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Doctor getDoctor1() {
        return Doctor.builder()
                .name("Gregory")
                .surname("House")
                .specialization("Humans")
                .phone("666-666-666")
                .email("drHouse@example.com")
                .photoData(getPhotoBytes("static/images/doctor-house.jpg"))
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("11-111")
                        .address("domowa 2")
                        .build())
                .build();
    }

    private Doctor getDoctor2() {
        return Doctor.builder()
                .name("John")
                .surname("Dolittle")
                .specialization("Animals")
                .phone("420-420-420")
                .email("drDolittle@example.com")
                .photoData(getPhotoBytes("static/images/doctor-dolittle.jpg"))
                .address(Address
                        .builder()
                        .country("Poland")
                        .city("Warsaw")
                        .postalCode("22-222")
                        .address("pieskowa 3")
                        .build())
                .build();
    }
}
