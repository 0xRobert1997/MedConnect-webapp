package code.medconnect.integration;

import code.medconnect.integration.configuration.AbstractIntegrationTest;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HomeControllerIntegrationTest extends AbstractIntegrationTest {

    private final TestRestTemplate testRestTemplate;

    @Test
    void thatHomePageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s", port, basePath);

        String page = this.testRestTemplate.getForObject(url, String.class);
        Assertions.assertThat(page)
                .contains("MedConnect is a platform dedicated to connecting patients and doctors :)");
    }
}