package code.medconnect.business;

import code.medconnect.business.dao.DoctorAvailabilityDAO;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.infrastructure.database.entity.DoctorAvailabilityEntity;
import code.medconnect.infrastructure.database.repository.mapper.DoctorAvailabilityEntityMapper;
import code.medconnect.util.DomainFixtures;
import code.medconnect.util.EntityFixtures;
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
    @Mock
    DoctorAvailabilityEntityMapper doctorAvailabilityEntityMapper;

    @InjectMocks
    PaginationService paginationService;


    @Test
    void paginatingWorksCorrectly() {
        //given
        int pageNumber = 1;
        int pageSize = 1;
        Integer doctorId = 1;
        Sort sort = Sort.by("day").ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        List<DoctorAvailabilityEntity> doctorAvailabilityEntityList = new ArrayList<>();
        DoctorAvailabilityEntity doctorAvailabilityEntity = EntityFixtures.someDoctorAvailability();
        doctorAvailabilityEntityList.add(doctorAvailabilityEntity);

        List<DoctorAvailability> doctorAvailabilityList = new ArrayList<>();
        DoctorAvailability doctorAvailability = DomainFixtures.someDoctorAvailability();
        doctorAvailabilityList.add(doctorAvailability);

        Page<DoctorAvailabilityEntity> entityPage
                = new PageImpl<>(doctorAvailabilityEntityList, pageable, doctorAvailabilityEntityList.size());

        Mockito.when(doctorAvailabilityDAO.getDoctorAvailabilityPage(doctorId, pageable)).thenReturn(entityPage);
        Mockito.when(doctorAvailabilityEntityMapper.map(doctorAvailabilityEntity)).thenReturn(doctorAvailability);
        Page<DoctorAvailability> expectedPage
                = new PageImpl<>(doctorAvailabilityList, pageable, doctorAvailabilityList.size());


        // when
        Page<DoctorAvailability> resultPage = paginationService.paginate(pageNumber, pageSize, doctorId);
        // then
        Assertions.assertEquals(expectedPage, resultPage);
        Mockito.verify(doctorAvailabilityDAO).getDoctorAvailabilityPage(doctorId, pageable);
    }
}
