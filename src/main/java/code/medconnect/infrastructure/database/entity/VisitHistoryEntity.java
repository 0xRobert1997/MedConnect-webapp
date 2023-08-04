/*package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "visit_history")
public class VisitHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "visitHistory_id")
    private Integer visitHistoryId;

    @OneToOne(mappedBy = "visitHistory")
    private PatientEntity patient;

    @OneToMany
    @JoinColumn(name="visit_history_id")
    private Set<VisitEntity> visits = new HashSet<>();


}*/
