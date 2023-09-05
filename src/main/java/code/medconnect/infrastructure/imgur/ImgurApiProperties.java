package code.medconnect.infrastructure.imgur;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "imgur.api")
public class ImgurApiProperties {

    private String url;
    private String clientId;
    private String clientSecret;
    private String accessToken;

}
