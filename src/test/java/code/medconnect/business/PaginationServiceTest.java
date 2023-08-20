package code.medconnect.business;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.business.PaginationService;
import code.medconnect.business.dao.DoctorAvailabilityDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class PaginationServiceTest {
    @Mock
    DoctorAvailabilityDAO doctorAvailabilityDAO;

    @InjectMocks
    PaginationService paginationService;


    @Test
    void paginatingWorksCorrectly() {
        //given
        int pageNumber = 1;
        int pageSize = 10;
        Integer doctorId = 1;
        Sort sort = Sort.by("day").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOList = new ArrayList<>();

        Page<DoctorAvailabilityDTO> expectedPage
                = new PageImpl<>(doctorAvailabilityDTOList, pageable, doctorAvailabilityDTOList.size());
        Mockito.when(doctorAvailabilityDAO.getDoctorAvailabilityPage(doctorId, pageable)).thenReturn(expectedPage);

        // when
        Page<DoctorAvailabilityDTO> resultPage = paginationService.paginate(pageNumber, pageSize, doctorId);
        // then
        Assertions.assertEquals(expectedPage, resultPage);
        Mockito.verify(doctorAvailabilityDAO).getDoctorAvailabilityPage(doctorId, pageable);
    }
}
