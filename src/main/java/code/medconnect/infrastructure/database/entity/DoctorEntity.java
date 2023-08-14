package code.medconnect.infrastructure.database.entity;

import code.medconnect.security.AppUserEntity;
import jakarta.persistence.*;
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

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;


    @Basic(fetch = FetchType.LAZY)
    @Column(name = "photo_data", columnDefinition = "bytea")
    private byte[] photoData;


    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private AddressEntity address;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "doctor_id")
    private Set<DoctorAvailabilityEntity> availabilities;


    @OneToMany(fetch = FetchType.EAGER, mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<VisitEntity> visits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private AppUserEntity appUser;
}
