package code.medconnect.business;

import code.medconnect.business.dao.PatientDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Patient;
import code.medconnect.domain.Visit;
import code.medconnect.domain.exception.ImgurUploadException;
import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
@AllArgsConstructor
public class ImgurService {

    public static final String UPLOAD_URL = "https://api.imgur.com/3/upload";
    private static final String IMAGE_URL_BASE = "https://imgur.com/";
    private static final String DEFAULT_PHOTO_PATH = "static/images/default-user-photo.jpg";
    private final ImgurApiProperties imgurApiProperties;
    private final ImageDownloaderService imageDownloaderService;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final PatientDAO patientDAO;
    private final VisitDAO visitDAO;


    @Transactional
    public String uploadPhoto(MultipartFile image) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + imgurApiProperties.getAccessToken());
        headers.add("Connection", "keep-alive");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        try {
            body.add("image", new FileSystemResource(convertMultipartFileToFile(image)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        body.add("album", imgurApiProperties.getAlbumHash());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                UPLOAD_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

        if (responseEntity.getStatusCode().equals(HttpStatus.OK)) {
            return parseId(responseEntity);
        } else {
            throw new ImgurUploadException("Failed to upload photo to Imgur");
        }

    }

    @Transactional
    public void saveUploadedPhotoId(Integer patientId, String uploadedPhotoId) {
        Optional<Patient> patientOpt = patientDAO.findById(patientId);
        patientOpt.ifPresent(patient -> patient.setImgurPhotoId(uploadedPhotoId));

        if (patientOpt.isPresent()) {
            Patient patient = patientOpt.get();
            patient.setImgurPhotoId(uploadedPhotoId);

            Set<Visit> patientsVisits = visitDAO.findByPatientId(patientId);
            patient.setVisits(patientsVisits);

            patientDAO.savePatient(patient);
        }

    }

    @Transactional
    public byte[] getPhoto(Patient patient) {
        String photoId = patient.getImgurPhotoId();
        if (photoId != null) {
            String imageUrl = (IMAGE_URL_BASE + photoId + ".jpg");
            try {
                return imageDownloaderService.downloadImage(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return getDefaultPhotoBytes();
        }
    }

    private static byte[] getDefaultPhotoBytes() {
        try {
            ClassPathResource resource = new ClassPathResource(ImgurService.DEFAULT_PHOTO_PATH);
            return IOUtils.toByteArray(resource.getInputStream());
        } catch (IOException e) {
            log.warn("Cannot load photo correctly");
            e.printStackTrace();
            return new byte[0];
        }
    }


    private String parseId(ResponseEntity<String> responseEntity) {

        try {
            JsonNode jsonNode = objectMapper.readTree(responseEntity.getBody());
            return jsonNode.get("data").get("id").asText();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

    }


    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
