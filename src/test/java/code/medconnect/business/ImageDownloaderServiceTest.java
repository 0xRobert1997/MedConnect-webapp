package code.medconnect.business;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class ImageDownloaderServiceTest {

    @InjectMocks
    private ImageDownloaderService imageDownloaderService;

    @Test
    void downloadingWorksCorrectly() throws IOException {
        //given
        String imageUrl = "https://imgur.com/someId.jpg";

        //when
        byte[] actualPhotoBytes = imageDownloaderService.downloadImage(imageUrl);

        //then
        Assertions.assertNotNull(actualPhotoBytes);
        Assertions.assertTrue(actualPhotoBytes.length > 0);
    }
}
