package code.medconnect.integration.support;

import code.medconnect.api.controller.PatientController;
import code.medconnect.api.controller.rest.PatientRestController;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface PatientRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default void updateUsersPhoto(Integer patientId) {
        String endpoint = PatientRestController.PATIENT_API_BASE_PATH
                + PatientRestController.PATIENT_UPLOAD_PHOTO.replace("{patientId}", patientId.toString());
        requestSpecification()
            .patch(endpoint)
            .then()
            .statusCode(HttpStatus.OK.value());
    }
}
