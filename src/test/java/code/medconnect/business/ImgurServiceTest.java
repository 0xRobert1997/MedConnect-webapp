package code.medconnect.business;

import code.medconnect.infrastructure.imgur.ImgurApiProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ImgurServiceTest {

    @Mock
    private ImgurApiProperties imgurApiProperties;

    @InjectMocks
    private ImgurService imgurService;

    @Test
    void thatUploadingPhotoWorksCorrectly() {

    }
}
