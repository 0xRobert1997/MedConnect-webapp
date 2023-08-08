package code.medconnect.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Visit {


    Integer visitId;
    OffsetDateTime dateTime;
    boolean cancelled;
    Note notes;
    Patient patient;
    Doctor doctor;


}
