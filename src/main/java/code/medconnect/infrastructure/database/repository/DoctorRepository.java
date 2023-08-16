package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorAvailabilityJpaRepository;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import code.medconnect.infrastructure.database.repository.mapper.DoctorEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
@AllArgsConstructor
public class DoctorRepository implements DoctorDAO {

    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorEntityMapper doctorEntityMapper;

    private final DoctorAvailabilityJpaRepository doctorAvailabilityJpaRepository;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;

    @Override
    public Doctor findByEmail(String email) {

        Optional<Doctor> doctorOpt = doctorJpaRepository.findByEmail(email).map(doctorEntityMapper::map);
        Doctor doctor = doctorOpt.orElseThrow(() -> new NotFoundException("Doctor with email: " + email + " does not exist"));
        return doctor;
    }

    @Override
    public void saveAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity doctorAvailabilityEntity = doctorAvailabilityEntityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.save(doctorAvailabilityEntity);
    }

    @Override
    public void deleteAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailabilityEntity doctorAvailabilityEntity = doctorAvailabilityEntityMapper.map(doctorAvailability);
        doctorAvailabilityJpaRepository.delete(doctorAvailabilityEntity);
    }

    @Override
    public Doctor saveDoctor(Doctor doctor) {
        DoctorEntity toSave = doctorEntityMapper.map(doctor);
        DoctorEntity saved = doctorJpaRepository.save(toSave);
        return doctorEntityMapper.map(saved);
    }

    @Override
    public Set<Doctor> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorEntityMapper::map)
                .collect(Collectors.toSet());
    }

    public Doctor findById(Integer id) {
         return doctorJpaRepository.findById(id)
                 .map(doctorEntityMapper::map)
                 .orElseThrow(() -> new NotFoundException("Doctor with id: " + id + " not found"));
    }
}
