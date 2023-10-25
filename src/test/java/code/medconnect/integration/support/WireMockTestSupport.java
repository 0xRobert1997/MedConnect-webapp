package code.medconnect.integration.support;

import code.medconnect.business.ImgurService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.web.multipart.MultipartFile;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;

public interface WireMockTestSupport {

    default void stubForUploadImage(WireMockServer wireMockServer, MultipartFile image) {
        wireMockServer.stubFor(WireMock.post(WireMock.urlPathEqualTo(ImgurService.UPLOAD_URL))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBodyFile("{\"data\":{\"id\":\"uploadedImageId123\"}}")));
    }
}
