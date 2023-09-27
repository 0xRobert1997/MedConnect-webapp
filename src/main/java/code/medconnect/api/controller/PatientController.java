package code.medconnect.api.controller;

import code.medconnect.api.dto.DoctorAvailabilityDTO;
import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.DoctorAvailabilityMapper;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.api.dto.mapper.VisitMapper;
import code.medconnect.business.*;
import code.medconnect.domain.DoctorAvailability;
import code.medconnect.domain.Patient;
import code.medconnect.security.AppUser;
import code.medconnect.security.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.time.LocalTime;
import java.util.Base64;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class PatientController {

    public static final String PATIENT_BASE_PATH = "/patient";
    public static final String NEW_VISIT = "/new-visit";

    static final String UPLOAD_PHOTO = "/upload";

    private static final Integer PAGE_SIZE = 3;
    private final PatientService patientService;
    private final VisitService visitService;
    private final ImgurService imgurService;
    private final AppUserService appUserService;
    private final DoctorService doctorService;
    private final PaginationService paginationService;
    private final PatientMapper patientMapper;
    private final DoctorMapper doctorMapper;
    private final VisitMapper visitMapper;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;


    @GetMapping(value = PATIENT_BASE_PATH)
    public String patientPage(
            Model model,
            Principal principal
    ) {
        String username = principal.getName();
        AppUser appUser = appUserService.findByUsername(username);
        String userEmail = appUser.getEmail();
        Patient patient = patientService.findByEmail(userEmail);
        PatientDTO patientDTO = patientMapper.map(patient);

        List<VisitDTO> patientsVisits = visitService.getPatientsVisits(patientDTO.getPesel())
                .stream()
                .map(visitMapper::map)
                .toList();
        Set<DoctorDTO> doctors = doctorService.findAll()
                .stream()
                .map(doctorMapper::map)
                .collect(Collectors.toSet());

        byte[] photoData = imgurService.getPhoto(patient);
        String base64Image = Base64.getEncoder().encodeToString(photoData);

        model.addAttribute("photo", base64Image);
        model.addAttribute("patientDTO", patientDTO);
        model.addAttribute("visitHistory", patientsVisits);
        model.addAttribute("doctors", doctors);

        return "patient-portal";
    }

    @PostMapping(UPLOAD_PHOTO)
    public String updateUsersPhoto(
            @RequestParam("image") MultipartFile image,
            @ModelAttribute("patientId") Integer patientId
    ) {
        String uploadedPhotoId = imgurService.uploadPhoto(image);
        imgurService.saveUploadedPhotoId(patientId, uploadedPhotoId);

        return "redirect:/patient";
    }

    @GetMapping(NEW_VISIT)
    public String newVisitPage(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @ModelAttribute("patientId") Integer patientId,
            @ModelAttribute("doctorId") Integer doctorId
    ) {


        Page<DoctorAvailability> pageOfAvailabilities = paginationService.paginate(page, PAGE_SIZE, doctorId);
        List<DoctorAvailabilityDTO> doctorAvailabilityDTOS = pageOfAvailabilities.getContent().stream()
                .map(doctorAvailabilityMapper::map)
                .toList();

        List<LocalTime> availableTimes = getTimeFrames();
        visitService.getUnavailableTimeFrames();


        model.addAttribute("doctorAvailabilities", doctorAvailabilityDTOS);
        model.addAttribute("doctorAvailabilityPage", pageOfAvailabilities);
        model.addAttribute("patientId", patientId);
        model.addAttribute("doctorId", doctorId);
        model.addAttribute("availableTimes", availableTimes);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pageOfAvailabilities.getTotalPages());

        return "new-visit";
    }


    private List<LocalTime> getTimeFrames() {
        return List.of(
                LocalTime.of(8, 0),
                LocalTime.of(8, 30),
                LocalTime.of(9, 0),
                LocalTime.of(9, 30),
                LocalTime.of(10, 0),
                LocalTime.of(10, 30),
                LocalTime.of(11, 0),
                LocalTime.of(11, 30),
                LocalTime.of(12, 0),
                LocalTime.of(12, 30),
                LocalTime.of(13, 0),
                LocalTime.of(13, 30),
                LocalTime.of(14, 0),
                LocalTime.of(14, 30),
                LocalTime.of(15, 0),
                LocalTime.of(15, 30)
        );
    }
}
