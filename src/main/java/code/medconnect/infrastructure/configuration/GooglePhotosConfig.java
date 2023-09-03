package code.medconnect.infrastructure.configuration;

import code.medconnect.business.GooglePhotosService;
import io.opencensus.resource.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class GooglePhotosConfig {
    @Value("${google.photos.credentials.location}")
    private Resource credentialsResource;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public GooglePhotosService googlePhotosService(RestTemplate restTemplate) {
        return new GooglePhotosService(restTemplate, credentialsResource);
    }
}
