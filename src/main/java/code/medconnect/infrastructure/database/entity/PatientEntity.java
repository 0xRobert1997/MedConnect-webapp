package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient")
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patient_id")
    private Long patientId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "pesel")
    private String pesel;

    @Column(name = "date_of_birth", nullable = false)
    private Date dateOfBirth;

    @Column(name = "sex")
    private String sex;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true)
    private AddressEntity addressId;

    @OneToMany
    @JoinColumn(name="patient_id")
    private Set<VisitEntity> visits;

    @OneToOne
    private VisitHistoryEntity visitHistory;

    @OneToMany(mappedBy = "patient", fetch = FetchType.LAZY, orphanRemoval = true)
    private Set<DiseaseEntity> diseases;
}
