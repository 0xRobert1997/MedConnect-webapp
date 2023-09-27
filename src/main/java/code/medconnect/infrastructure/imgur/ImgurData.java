package code.medconnect.infrastructure.imgur;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ImgurData {

    @JsonProperty("url")
    private String url;
}
