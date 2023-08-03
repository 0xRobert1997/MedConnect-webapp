package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visit")
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Long visitId;


    @Column(name = "date_time", nullable = false)
    private OffsetDateTime dateTime;

    @Column(name = "cancelled", nullable = false)
    private boolean cancelled = false;


    @OneToOne
    private NotesEntity notes;

    @ManyToOne(fetch = FetchType.EAGER)
    private PatientEntity patient;

    @ManyToOne(fetch = FetchType.EAGER)
    private DoctorEntity doctor;


}
