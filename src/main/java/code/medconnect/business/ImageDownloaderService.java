package code.medconnect.business;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

@Slf4j
@Service
@AllArgsConstructor
public class ImageDownloaderService {

    public byte[] downloadImage(String imageUrl) throws IOException {
        URL url = new URL(imageUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        try (InputStream inputStream = connection.getInputStream()) {
            return IOUtils.toByteArray(inputStream);
        } finally {
            connection.disconnect();
        }
    }
}
