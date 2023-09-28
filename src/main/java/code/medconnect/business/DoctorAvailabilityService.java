package code.medconnect.business;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.business.dao.DoctorDAO;
import code.medconnect.business.dao.VisitDAO;
import code.medconnect.domain.Doctor;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.TimeSlot;
import code.medconnect.domain.Visit;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DoctorAvailabilityService {

    private final VisitDAO visitDAO;
    private final DoctorDAO doctorDAO;


    @Transactional
    public DoctorAvailability getAvailabilityWithSlots(DoctorAvailability availability) {
        Map<Integer, TimeSlot> allSlots = getAllSlots(availability.getDay());
        Doctor doctor = doctorDAO.findById(availability.getDoctorId());
        List<Visit> byDoctorAndDay = visitDAO.findByDoctorAndDay(doctor, availability.getDay());

        for (Map.Entry<Integer, TimeSlot> slotEntry : allSlots.entrySet()) {
            TimeSlot timeSlot = slotEntry.getValue();
            boolean isTaken = isTimeSlotTaken(timeSlot, byDoctorAndDay);
            timeSlot.setTaken(isTaken);
        }

        availability.setTimeSlots(new ArrayList<>(allSlots.values()));
        return removeTakenSlots(availability);
    }



    private DoctorAvailability removeTakenSlots(DoctorAvailability availability) {
        List<TimeSlot> timeSlots = availability.getTimeSlots();

        timeSlots.removeIf(TimeSlot::isTaken);
        return availability;
    }


    private boolean isTimeSlotTaken(TimeSlot slot, List<Visit> visits) {
        LocalTime startTime = slot.getStartTime();
        LocalTime endTime = slot.getEndTime();

        return visits.stream()
                .anyMatch(visit -> !visit.isCanceled()
                        && visit.getStartTime().isBefore(endTime)
                        && visit.getEndTime().isAfter(startTime));
    }


    private Map<Integer, TimeSlot> getAllSlots(LocalDate date) {
        Map<Integer, TimeSlot> slots = new HashMap<>();

        slots.put(1, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(8, 0)).endTime(LocalTime.of(8, 30)).build());
        slots.put(2, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(8, 30)).endTime(LocalTime.of(9, 0)).build());
        slots.put(3, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(9, 0)).endTime(LocalTime.of(9, 30)).build());
        slots.put(4, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(9, 30)).endTime(LocalTime.of(10, 0)).build());
        slots.put(5, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(10, 0)).endTime(LocalTime.of(10, 30)).build());
        slots.put(6, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(10, 30)).endTime(LocalTime.of(11, 0)).build());
        slots.put(7, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(11, 0)).endTime(LocalTime.of(11, 30)).build());
        slots.put(8, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(11, 30)).endTime(LocalTime.of(12, 0)).build());
        slots.put(9, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(12, 0)).endTime(LocalTime.of(12, 30)).build());
        slots.put(10, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(12, 30)).endTime(LocalTime.of(13, 0)).build());
        slots.put(11, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(13, 0)).endTime(LocalTime.of(13, 30)).build());
        slots.put(12, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(13, 30)).endTime(LocalTime.of(14, 0)).build());
        slots.put(13, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(14, 0)).endTime(LocalTime.of(14, 30)).build());
        slots.put(14, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(14, 30)).endTime(LocalTime.of(15, 0)).build());
        slots.put(15, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(15, 0)).endTime(LocalTime.of(15, 30)).build());
        slots.put(16, TimeSlot.builder()
                .day(date).startTime(LocalTime.of(15, 30)).endTime(LocalTime.of(16, 0)).build());

        return slots;
    }

}
