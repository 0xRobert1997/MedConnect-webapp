package code.medconnect.integration.rest;

import code.medconnect.api.dto.VisitDTO;
import code.medconnect.integration.configuration.RestAssuredIntegrationTestBase;
import code.medconnect.integration.support.VisitRestControllerTestSupport;
import code.medconnect.util.DtoFixtures;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class VisitRestControllerTest
        extends RestAssuredIntegrationTestBase
        implements VisitRestControllerTestSupport {


    @Test
    void allVisitsAreRetrievedCorrectly() {
        //given
        //when
        ExtractableResponse<Response> result = getVisits();

        //then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.body().jsonPath().getList("").size()).isGreaterThan(0);
    }

    @Test
    void makingNewVisitWorksCorrectly() {
        //given
        int patientId = 1;
        int doctorId = 1;
        VisitDTO visitDTO = DtoFixtures.someVisitDTO2()
                .withPatientId(patientId)
                .withDoctorId(doctorId);
        //when
        ExtractableResponse<Response> result = makeNewVisit(visitDTO);
        //then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
        Assertions.assertThat(result.as(VisitDTO.class).getPatientId()).isEqualTo(patientId);
        Assertions.assertThat(result.as(VisitDTO.class).getDoctorId()).isEqualTo(doctorId);
    }

    @Test
    void cancelingVisitWorksCorrectly() {
        //given
        int visitId = 1;
        //when
        ExtractableResponse<Response> result = cancelVisit(visitId);
        //then
        Assertions.assertThat(result.statusCode()).isEqualTo(HttpStatus.OK.value());
    }

}
