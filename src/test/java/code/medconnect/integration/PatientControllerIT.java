package code.medconnect.integration;

import code.medconnect.integration.configuration.AbstractIT;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;


@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PatientControllerIT extends AbstractIT {

    private final TestRestTemplate testRestTemplate;

    @BeforeEach
    void setup() {
        TestingAuthenticationToken authenticationToken
                = new TestingAuthenticationToken("Patient1", "test");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }

/*    @Test
    void thatPatientPageWorksCorrectly() {
        String url = String.format("http://localhost:%s%s/patient", port, basePath);

        TestingAuthenticationToken authenticationToken = new TestingAuthenticationToken("Patient1", "test");
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + authenticationToken.getCredentials());


        ResponseEntity<String> response
                = testRestTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);

        String page = response.getBody();

        Assertions.assertThat(page)
                .contains("Patient Details");
    }*/
}
