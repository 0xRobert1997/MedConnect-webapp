package code.medconnect.infrastructure.database.repository;

import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.exception.NotFoundException;
import code.medconnect.infrastructure.database.entity.DoctorEntity;
import code.medconnect.infrastructure.database.repository.jpa.DoctorJpaRepository;
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

    @Override
    public Doctor findByEmail(String email) {

        Optional<Doctor> doctorOpt = doctorJpaRepository.findByEmail(email)
                .map(doctorEntityMapper::map);
        return doctorOpt.orElseThrow(
                () -> new NotFoundException("Doctor with email: " + email + " does not exist"));
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
