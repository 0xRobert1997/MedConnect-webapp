package code.medconnect.controller.webMvc;

import code.medconnect.api.controller.VisitController;
import code.medconnect.business.VisitService;
import code.medconnect.domain.Visit;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = VisitController.class)
@AutoConfigureMockMvc(addFilters = false)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class VisitControllerWebMvcTest {

    private MockMvc mockMvc;

    @MockBean
    private VisitService visitService;

    @Test
    void confirmVisitTest() throws Exception {
        //given
        Integer patientId = 1;
        Integer doctorId = 1;
        LocalDate day = LocalDate.of(2023, 5, 5);
        LocalTime startTime = LocalTime.of(12, 0);
        LocalTime endTime = LocalTime.of(12, 30);
        String selectedTimeSlot = "12:00-12:30";


        Mockito.when(visitService.makeVisit(patientId, doctorId, day, startTime, endTime)).thenReturn(Visit.builder().build());

        // when then
        mockMvc.perform(post("/confirm-visit")
                        .param("patientId", patientId.toString())
                        .param("doctorId", doctorId.toString())
                        .param("selectedDay", day.toString())
                        .param("selectedTimeSlot", selectedTimeSlot))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient"));
        Mockito.verify(visitService).makeVisit(patientId, doctorId, day, startTime, endTime);
    }

    @Test
    void cancelVisitTest() throws Exception {
        //given
        Integer visitId = 1;
        //when
        Mockito.doNothing().when(visitService).cancelVisit(visitId);
        //then
        mockMvc.perform(post("/cancel-visit")
                        .param("visitId", visitId.toString()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/patient"));

        Mockito.verify(visitService).cancelVisit(visitId);
    }
}
