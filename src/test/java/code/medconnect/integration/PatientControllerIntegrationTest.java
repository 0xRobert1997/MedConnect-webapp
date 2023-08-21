package code.medconnect.integration;

import code.medconnect.integration.configuration.RestAssuredIntegrationTestBase;
import io.restassured.RestAssured;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerIntegrationTest extends RestAssuredIntegrationTestBase {

    private final TestRestTemplate testRestTemplate;


    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
        RestAssured.basePath = basePath;
        RestAssured.baseURI = "http://localhost";
    }

    @AfterEach
    public void afterEach() {

    }

    @Test
    void thatPatientPageWorksCorrectly() {
        RestAssured.given()
                .auth().basic("Patient1", "test")
                .when()
                .get("/patient")
                .then()
                .statusCode(200);
    }


}