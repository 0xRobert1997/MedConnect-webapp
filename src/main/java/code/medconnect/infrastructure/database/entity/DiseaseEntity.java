package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "diseases_history")
public class DiseaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "diseasesHistory_id")
    private Long diseasesHistoryId;

    @Column(name = "disease_name")
    private String diseaseName;

    @Column(name = "diagnosis_date")
    private Date diagnosisDate;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;
}
