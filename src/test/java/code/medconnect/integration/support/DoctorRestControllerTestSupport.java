package code.medconnect.integration.support;

import code.medconnect.api.controller.rest.DoctorRestController;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface DoctorRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default Set<DoctorDTO> listDoctors() {
        String endpoint = DoctorRestController.DOCTOR_API_BASE_PATH + DoctorRestController.DOCTORS;
        return new HashSet<>(requestSpecification()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .jsonPath()
                .getList("", DoctorDTO.class));
    }

    default List<VisitDTO> getDoctorVisits(Integer doctorId) {
        String endpoint = DoctorRestController.DOCTOR_API_BASE_PATH
                + DoctorRestController.DOCTOR_ID.replace("{doctorId}", doctorId.toString());
        return requestSpecification()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .body()
                .jsonPath()
                .getList("", VisitDTO.class);
    }

    default PatientDTO checkPatient(String patientEmail) {
        String endpoint = DoctorRestController.DOCTOR_API_BASE_PATH
                + DoctorRestController.PATIENT_EMAIL.replace("{patientEmail}", patientEmail);
        return requestSpecification()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(PatientDTO.class);
    }


}
