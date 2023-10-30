package code.medconnect.business;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Patient;
import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import code.medconnect.util.DomainFixtures;
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
import java.util.Optional;

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
    @Mock
    private ImageDownloaderService imageDownloaderService;

    @Mock
    private PatientDAO patientDAO;

    @Mock
    private VisitDAO visitDAO;


    @Test
    void thatUploadingPhotoWorksCorrectly() throws IOException {
        //given
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

        // when, then
        String imageId = imgurService.uploadPhoto(image);

        Assertions.assertEquals("imageId", imageId);
    }

    @Test
    void thatSavingUploadedPhotoIdWorksCorrectly() {
        //given
        Integer patientId = 1;
        Patient patient = DomainFixtures.somePatient().withPatientId(patientId);
        String photoId = "somePhotoId";

        Mockito.when(patientDAO.findById(patientId)).thenReturn(Optional.of(patient));

        //when
        imgurService.saveUploadedPhotoId(patient.getPatientId(), photoId);
        //then
        Mockito.verify(visitDAO).findByPatientId(patientId);
        Mockito.verify(patientDAO).savePatient(patient);
    }

    @Test
    void testGetPhotoWithoutImageId() {
        //given
        Patient patient = DomainFixtures.somePatient();

        //when
        byte[] actualPhotoBytes = imgurService.getPhoto(patient);
        //then
        Assertions.assertNotNull(actualPhotoBytes);
    }

    @Test
    void testGetPhotoWithImageId() throws IOException {
        //given
        String photoId = "someFakeId";
        Patient patient = DomainFixtures.somePatient().withImgurPhotoId(photoId);
        String imageUrl = "http://example.com/image.jpg";
        byte[] expected = "photo-content".getBytes();
        Mockito.when(imageDownloaderService
                .downloadImage("https://imgur.com/" + photoId + ".jpg")).thenReturn(expected);
        //when
        byte[] result = imgurService.getPhoto(patient);

        //then
        Assertions.assertNotNull(result);
    }
}
