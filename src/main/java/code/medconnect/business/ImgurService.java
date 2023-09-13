package code.medconnect.business;

import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@Service
@AllArgsConstructor
public class ImgurService {

    private static final String UPLOAD_URL = "https://api.imgur.com/3/upload";
    private final ImgurApiProperties imgurApiProperties;
    private final RestTemplate restTemplate;





    public String uploadPhoto(MultipartFile image) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + imgurApiProperties.getAccessToken());
        headers.add("Connection", "keep-alive");

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", new FileSystemResource(convertMultipartFileToFile(image)));
        body.add("album", imgurApiProperties.getAlbumHash());

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);


        ResponseEntity<String> responseEntity = restTemplate.exchange(
                UPLOAD_URL,
                HttpMethod.POST,
                requestEntity,
                String.class
        );

    // dodać obsługę odpowiedzi

        return null;
    }


    // response

    /*
    {
    "status": 200,
    "success": true,
    "data": {
        "id": "01bqtFb",
        "deletehash": "ZNK8fhImHb5HRal",
        "account_id": 174096701,
        "account_url": "0xRobert",
        "ad_type": null,
        "ad_url": null,
        "title": null,
        "description": null,
        "name": "",
        "type": "image/png",
        "width": 742,
        "height": 791,
        "size": 26163,
        "views": 0,
        "section": null,
        "vote": null,
        "bandwidth": 0,
        "animated": false,
        "favorite": false,
        "in_gallery": false,
        "in_most_viral": false,
        "has_sound": false,
        "is_ad": false,
        "nsfw": null,
        "link": "https://i.imgur.com/01bqtFb.png",
        "tags": [],
        "datetime": 1694019471,
        "mp4": "",
        "hls": ""
    }
}
     */
    private File convertMultipartFileToFile(MultipartFile multipartFile) throws IOException {
        File file = new File(multipartFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(multipartFile.getBytes());
        }
        return file;
    }
}
