package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visit")
public class VisitEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visit_id")
    private Integer visitId;

    @Column(name = "patient_id")
    private Integer patientId;

    @Column(name = "day", nullable = false)
    private LocalDate day;

    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "start_time", nullable = false)
    private LocalTime startTime;

    @Column(name = "end_time", nullable = false)
    private LocalTime endTime;

    @Column(name = "canceled", nullable = false)
    private boolean canceled;


    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "visit_id")
    private List<NoteEntity> notes;


}
