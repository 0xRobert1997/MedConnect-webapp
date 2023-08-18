package code.medconnect.business;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.business.dao.DoctorAvailabilityDAO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaginationService {


    private final DoctorAvailabilityDAO doctorAvailabilityDAO;


    public Page<DoctorAvailabilityDTO> paginate(int pageNumber, int pageSize, Integer doctorId) {

        Sort sort = Sort.by("day").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        return doctorAvailabilityDAO.getDoctorAvailabilityPage(doctorId, pageable);
    }

}
