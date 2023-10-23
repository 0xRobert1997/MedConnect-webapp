package code.medconnect.integration.support;

import code.medconnect.api.controller.PatientController;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface PatientRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default void patientPortal() {
        requestSpecification()
            .get(PatientController.PATIENT_BASE_PATH)
            .then()
            .statusCode(HttpStatus.OK.value());
    }
}
