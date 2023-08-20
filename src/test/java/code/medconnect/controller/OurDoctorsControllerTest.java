package code.medconnect.controller;

import code.medconnect.api.controller.OurDoctorsController;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.business.DoctorService;
import code.medconnect.domain.Doctor;
import code.medconnect.util.DomainFixtures;
import code.medconnect.util.DtoFixtures;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.util.Set;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class OurDoctorsControllerTest {

    @Mock
    private DoctorService doctorService;
    @Mock
    private DoctorMapper doctorMapper;
    @InjectMocks
    private OurDoctorsController ourDoctorsController;

    @Test
    void thatOurDoctorsPageWorksCorrectly() {
        //given
        Set<Doctor> doctors = Set.of(DomainFixtures.someDoctor1(), DomainFixtures.someDoctor2());
        DoctorDTO doctorDTO1 = DtoFixtures.someDoctorDTO1().withPhotoData(new byte[]{1, 2, 3}).withBase64Image("AQID");
        DoctorDTO doctorDTO2 = DtoFixtures.someDoctorDTO2().withPhotoData(new byte[]{1, 2, 3}).withBase64Image("AQID");
        Set<DoctorDTO> doctorDTOs = Set.of(doctorDTO1, doctorDTO2);

        Mockito.when(doctorService.findAll()).thenReturn(doctors);
        Mockito.when(doctorMapper.map(Mockito.any(Doctor.class)))
                .thenReturn(doctorDTO1, doctorDTO2);

        //when
        String viewName = ourDoctorsController.ourDoctorsPage(mock(Model.class));

        // then
        verify(doctorService).findAll();
        Model model = mock(Model.class);
        ourDoctorsController.ourDoctorsPage(model);
        verify(model).addAttribute(ArgumentMatchers.eq("doctors"), ArgumentMatchers.anySet());

        Assertions.assertEquals("ourdoctors", viewName);
    }
}
