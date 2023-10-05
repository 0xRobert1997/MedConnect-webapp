package code.medconnect.business;

import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.client.RestTemplate;


import java.io.IOException;

@ExtendWith(MockitoExtension.class)
public class ImgurServiceTest {

    @InjectMocks
    private ImgurService imgurService;

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private ImgurApiProperties imgurApiProperties;
    @Mock
    private RestTemplate restTemplate;


    @Test
    void thatUploadingPhotoWorksCorrectly() throws IOException {
        String accessToken = "access token";
        String albumHash = "album hash";
        String imageFileContent = "mocked image";
        JsonNode mockJsonNode = Mockito.mock(JsonNode.class);
        ResponseEntity<String> successResponse
                = new ResponseEntity<>("{\"data\": {\"id\": \"imageId\"}}", HttpStatus.OK);

        Mockito.when(imgurApiProperties.getAccessToken()).thenReturn(accessToken);
        Mockito.when(imgurApiProperties.getAlbumHash()).thenReturn(albumHash);
        Mockito.when(objectMapper.readTree(successResponse.getBody())).thenReturn(mockJsonNode);
        Mockito.when(mockJsonNode.get("data")).thenReturn(mockJsonNode);
        Mockito.when(mockJsonNode.get("id")).thenReturn(mockJsonNode);
        Mockito.when(mockJsonNode.asText()).thenReturn("imageId");

        MockMultipartFile image = new MockMultipartFile(
                "image", "image.jpg", "image/jpeg", imageFileContent.getBytes());

        Mockito.when(restTemplate.exchange(
                Mockito.eq(ImgurService.UPLOAD_URL),
                Mockito.eq(HttpMethod.POST),
                Mockito.any(HttpEntity.class),
                Mockito.eq(String.class)
        )).thenReturn(successResponse);

        String imageId = imgurService.uploadPhoto(image);

        Assertions.assertEquals("imageId", imageId);
    }

    @Test
    void thatGettingPhotoWorksCorrectly() {

    }
}
