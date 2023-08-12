package code.medconnect.api.dto;

import code.medconnect.domain.Patient;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiseaseDTO {

    Integer diseaseId;
    String diseaseName;
    OffsetDateTime diagnosisDate;
    String description;
    Patient patient;
}
