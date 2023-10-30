package code.medconnect.integration.rest;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.integration.configuration.RestAssuredIntegrationTestBase;
import code.medconnect.integration.support.PatientRestControllerTestSupport;
import code.medconnect.integration.support.WireMockTestSupport;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;

public class PatientRestControllerTest
        extends RestAssuredIntegrationTestBase
        implements PatientRestControllerTestSupport, WireMockTestSupport {


    @Test
    void updatingUsersPhotoWorksCorrectly() throws IOException {
        //given
        Integer patientId = 1;

        MockMultipartFile image = new MockMultipartFile(
                "image", "testImage.jpg", "application/octet-stream",
                getClass().getResourceAsStream("/static/images/testImage.jpg")
        );
        stubForUploadImage(wireMockServer, image);

        //when, then
        updateUsersPhoto(patientId);
    }


    @Test
    void patientDetailsWorksCorrectly() {
        //given
        String patientEmail = "patient1@example.com";
        //when
        PatientDTO result = patientDetails(patientEmail);
        //then
        Assertions.assertThat(result).isNotNull();
    }
}
