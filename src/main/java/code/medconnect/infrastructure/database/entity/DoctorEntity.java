package code.medconnect.infrastructure.database.entity;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
public class DoctorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "doctor_id")
    private Integer doctorId;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "specialization")
    private String specialization;

    // walidacja była na poziomie dtosów
    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Lob
    @Column(name = "photo")
    private byte[] photo;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "addressId")
    private AddressEntity addressId;

    @OneToMany(mappedBy = "doctorId", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DoctorAvailabilityEntity> availabilityEntitySet;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<VisitEntity> visits;
}
