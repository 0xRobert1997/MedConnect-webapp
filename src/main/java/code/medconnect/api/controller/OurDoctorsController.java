package code.medconnect.api.controller;

import code.medconnect.api.dto.DoctorDTO;
import code.medconnect.api.dto.mapper.DoctorMapper;
import code.medconnect.business.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Base64;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class OurDoctorsController {

    static final String OUR_DOCTORS_BASE_PATH = "/ourdoctors";
    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;


    @RequestMapping(value = OUR_DOCTORS_BASE_PATH, method = RequestMethod.GET)
    public String ourDoctorsPage(Model model) {

        Set<DoctorDTO> doctors = doctorService.findAll()
                .stream()
                .map(doctorMapper::map)
                .collect(Collectors.toSet());


        for (DoctorDTO doctor : doctors) {
            String base64Image = Base64.getEncoder().encodeToString(doctor.getPhotoData());
            doctor.setBase64Image(base64Image);
        }
        model.addAttribute("doctors", doctors);

        return "ourdoctors";
    }


}
