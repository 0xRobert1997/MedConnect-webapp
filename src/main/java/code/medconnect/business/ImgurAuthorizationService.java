package code.medconnect.business;

import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import code.medconnect.infrastructure.imgur.ImgurData;
import code.medconnect.infrastructure.imgur.ImgurResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@AllArgsConstructor
public class ImgurAuthorizationService {

    private final ImgurApiProperties imgurApiProperties;
    private final RestTemplate restTemplate;
    private final String imgurApiUrl = "https://api.imgur.com/3";

    private final String authUrl = "https://api.imgur.com/oauth2/token";

// https://api.imgur.com/oauth2/authorize?client_id=YOUR_CLIENT_ID&response_type=REQUESTED_RESPONSE_TYPE&state=APPLICATION_STATE
// https://api.imgur.com/oauth2/authorize?client_id=8ce1a67ee460abc&response_type=token
// https://api.imgur.com/oauth2/authorize?response_type=code&client_id=8ce1a67ee460abc&redirect_uri=https%3A%2F%2Fapi.imgur.com%2Foauth2%2Ftoken

    //response_type=token
    //state na końcu jest opcjonalny
    // potem trzeba wpisać login i hasło







    public String getImageUrl(String imageId) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", imgurApiProperties.getClientId());

        HttpEntity<?> entity = new HttpEntity<>(headers);

        ResponseEntity<ImgurResponse> response = restTemplate.exchange(
                imgurApiUrl + "/image/" + imageId,
                HttpMethod.GET,
                entity,
                ImgurResponse.class
        );

        if (response.getStatusCode() == HttpStatus.OK) {
            ImgurResponse imgurResponse = response.getBody();
            if (imgurResponse != null) {
                ImgurData imgurData = imgurResponse.getData();
                if (imgurData != null) {
                    return imgurData.getUrl();
                }
            }
        }

        return null;
    }
}
