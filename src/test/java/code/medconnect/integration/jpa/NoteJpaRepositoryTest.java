package code.medconnect.integration.jpa;

import code.medconnect.fixtures.EntityFixtures;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.entity.NoteEntity;
import code.medconnect.infrastructure.database.entity.PatientEntity;
import code.medconnect.infrastructure.database.entity.VisitEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.NoteJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.PatientJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.VisitJpaRepository;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@TestPropertySource(locations = "classpath:application-test.yaml")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class NoteJpaRepositoryTest {

    NoteJpaRepository noteJpaRepository;
    PatientJpaRepository patientJpaRepository;
    DoctorJpaRepository doctorJpaRepository;
    VisitJpaRepository visitJpaRepository;

    @Test
    void shouldAddNoteToVisitCorrectly() {
        // Given
        PatientEntity patient = patientJpaRepository.saveAndFlush(EntityFixtures.somePatient1());
        DoctorEntity doctor = doctorJpaRepository.saveAndFlush(EntityFixtures.someDoctor1());
        VisitEntity visit = visitJpaRepository.saveAndFlush(EntityFixtures.someVisit().withPatient(patient).withDoctor(doctor));
        NoteEntity note = EntityFixtures.someNote()
                .withDateTime(OffsetDateTime.of(
                        2023, 9, 10, 11, 12, 0, 0, ZoneOffset.UTC))
                .withVisit(visit);

        // When
        noteJpaRepository.saveAndFlush(note);
        visit.setNote(note);
        visitJpaRepository.saveAndFlush(visit);

        // Then
        VisitEntity updatedVisit = visitJpaRepository.findById(visit.getVisitId()).orElse(null);
        assertNotNull(updatedVisit);
        assertThat(updatedVisit.getNote()).isEqualTo(note);
    }
}
