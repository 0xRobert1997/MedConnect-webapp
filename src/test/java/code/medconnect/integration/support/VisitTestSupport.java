package code.medconnect.integration.support;

import code.medconnect.api.controller.rest.VisitRestController;
import code.medconnect.api.dto.VisitDTO;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.springframework.http.HttpStatus;

public interface VisitTestSupport {

    RequestSpecification requestSpecification();

    default ExtractableResponse<Response> makeNewVisit(final VisitDTO visitDTO) {
        return requestSpecification()
                .body(visitDTO)
                .post(VisitRestController.VISIT_API_BASE_PATH + VisitRestController.VISIT_NEW_VISIT)
                .then()
                .statusCode(HttpStatus.CREATED.value())
                .and()
                .extract();
    }
}
