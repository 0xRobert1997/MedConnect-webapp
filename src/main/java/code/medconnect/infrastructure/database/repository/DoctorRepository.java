package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityMapper;
import code.medconnect.infrastructure.database.repository.mapper.DoctorMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {

    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorMapper doctorMapper;

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email).map(doctorMapper::map);
    }

    @Override
    public void saveAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity doctorAvailabilityEntity = doctorAvailabilityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.save(doctorAvailabilityEntity);
    }

    @Override
    public void deleteAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity doctorAvailabilityEntity = doctorAvailabilityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.delete(doctorAvailabilityEntity);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        DoctorEntity toSave = doctorMapper.map(doctor);
        DoctorEntity saved = doctorJpaRepository.save(toSave);
        return doctorMapper.map(saved);
    }

    @Override
    public Set<Doctor> findAll() {
        return null;
    }
}
