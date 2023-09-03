package code.medconnect.business;

import io.opencensus.resource.Resource;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
@RequiredArgsConstructor
public class GooglePhotosService {


    private final RestTemplate restTemplate;
    private final Resource credentialsResource;

    @Value("${google.photos.api.url}")
    private String googlePhotosApiUrl;

    @Value("${google.photos.album.id}")
    private String albumId;


    public void uploadPhoto(byte[] photoData, String photoName, String mimeType) {

    }

    public ResponseEntity<byte[]> getPhoto(String photoId) {

        return null;
    }

}
