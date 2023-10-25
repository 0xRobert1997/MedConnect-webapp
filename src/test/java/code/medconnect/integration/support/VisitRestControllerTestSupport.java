package code.medconnect.integration.support;

import code.medconnect.api.controller.rest.VisitRestController;
import code.medconnect.api.dto.VisitDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface VisitRestControllerTestSupport {

    RequestSpecification requestSpecification();

    default ExtractableResponse<Response> getVisits() {
        String endpoint = VisitRestController.VISIT_API_BASE_PATH + VisitRestController.VISIT_ALL;
        return requestSpecification()
                .get(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> makeNewVisit(final VisitDTO visitDTO) {
        String endpoint = VisitRestController.VISIT_API_BASE_PATH + VisitRestController.VISIT_NEW;
        return requestSpecification()
                .body(visitDTO)
                .post(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

    default ExtractableResponse<Response> cancelVisit(Integer visitId) {
        String endpoint = VisitRestController.VISIT_API_BASE_PATH
                + VisitRestController.VISIT_CANCEL.replace("{visitId}", visitId.toString());
        return requestSpecification()
                .patch(endpoint)
                .then()
                .statusCode(HttpStatus.OK.value())
                .and()
                .extract();
    }

}
