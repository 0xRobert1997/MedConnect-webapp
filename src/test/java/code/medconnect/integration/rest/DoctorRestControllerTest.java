package code.medconnect.integration.rest;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.integration.configuration.RestAssuredIntegrationTestBase;
import code.medconnect.integration.support.DoctorRestControllerTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

public class DoctorRestControllerTest
        extends RestAssuredIntegrationTestBase
        implements DoctorRestControllerTestSupport {


    @Test
    void allDoctorsCanBeRetrievedCorrectly() {
        //given
        String doctorName1 = "Gregory";
        String doctorName2 = "Johny";
        //when
        Set<DoctorDTO> result = listDoctors();

        //then
        Assertions.assertThat(result).hasSize(2);
        Assertions.assertThat(result).doesNotContainNull();
        Assertions.assertThat(result)
                .anyMatch(doctor -> doctor.getName().equals(doctorName1));
        Assertions.assertThat(result)
                .anyMatch(doctor -> doctor.getName().equals(doctorName2));
    }

    @Test
    void retrievingDoctorsVisitsWorksCorrectly() {
        //given
        int doctorId = 1;
        int expectedVisits = 6;
        //when
        List<VisitDTO> result = getDoctorVisits(doctorId);
        //then
        Assertions.assertThat(result).hasSize(expectedVisits);
        Assertions.assertThat(result)
                .extracting(
                        "visitId", "day", "startTime", "endTime", "patientId", "doctorId"
                )
                .allSatisfy(field -> {
                    if (field == null) {
                        throw new AssertionError("null found");
                    }
                });
    }

    @Test
    void checkingPatientWorksCorrectly() {
        //given
        String patientEmail = "patient1@example.com";
        //when
        PatientDTO result = checkPatient(patientEmail);
        //then
        Assertions.assertThat(result).isNotNull();
    }

}
