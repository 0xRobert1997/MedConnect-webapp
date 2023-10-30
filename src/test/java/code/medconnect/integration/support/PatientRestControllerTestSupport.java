package code.medconnect.integration.support;

import code.medconnect.api.controller.rest.PatientRestController;
import code.medconnect.api.dto.PatientDTO;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.util.Objects;

import static org.hamcrest.Matchers.containsString;

public interface PatientRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default void updateUsersPhoto(Integer patientId) {

        String endpoint = PatientRestController.PATIENT_API_BASE_PATH
                + PatientRestController.PATIENT_UPLOAD_PHOTO.replace("{patientId}", patientId.toString());

        requestSpecification()
                .multiPart("image", new File
                        (Objects.requireNonNull(getClass().getResource("/static/images/testImage.jpg")).getFile()))
                .contentType("multipart/form-data")
                .patch(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .body(containsString("Image uploaded successfully"));
    }

    default PatientDTO patientDetails(String patientEmail) {
        String endpoint = PatientRestController.PATIENT_API_BASE_PATH
                + PatientRestController.PATIENT_EMAIL.replace("{patientEmail}", patientEmail);
        return requestSpecification()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract()
                .as(PatientDTO.class);
    }
}
