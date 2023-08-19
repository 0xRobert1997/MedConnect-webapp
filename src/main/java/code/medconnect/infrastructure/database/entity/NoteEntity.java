package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@With
@Builder
@ToString(of = {"dateTime", "noteContent"})
@EqualsAndHashCode(of = {"dateTime", "noteContent"})
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "note")
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id")
    private Integer noteId;

    @Column(name = "note_content")
    private String noteContent;

    @Column(name = "date_time")
    private OffsetDateTime dateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "visit_id")
    private VisitEntity visit;
}
