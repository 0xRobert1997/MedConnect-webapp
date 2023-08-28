package code.medconnect.business;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.mapper.DoctorAvailabilityMapper;
import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PaginationService {

    private final DoctorAvailabilityService doctorAvailabilityService;


    private final DoctorAvailabilityDAO doctorAvailabilityDAO;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;
    private final DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;


    @Transactional
    public Page<DoctorAvailabilityDTO> paginate(int pageNumber, int pageSize, Integer doctorId) {
        Sort sort = Sort.by("day").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<DoctorAvailabilityEntity> availabilityPage = doctorAvailabilityDAO.getDoctorAvailabilityPage(doctorId, pageable);

        List<DoctorAvailabilityDTO> doctorAvailabilityDTOs = availabilityPage.getContent()
                .stream()
                .map(doctorAvailabilityEntityMapper::map)
                .map(doctorAvailabilityMapper::map)
                .collect(Collectors.toList());

        return new PageImpl<>(doctorAvailabilityDTOs, pageable, availabilityPage.getTotalElements());
    }

}
