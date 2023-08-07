package code.medconnect.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Disease {

    Integer diseaseId;
    String diseaseName;
    OffsetDateTime diagnosisDate;
    String description;
    Patient patient;

}
