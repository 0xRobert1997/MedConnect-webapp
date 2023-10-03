package code.medconnect.api.controller.rest;

import code.medconnect.api.dto.PatientDTO;
import code.medconnect.api.dto.VisitDTO;
import code.medconnect.api.dto.mapper.PatientMapper;
import code.medconnect.business.ImgurService;
import code.medconnect.business.PatientService;
import code.medconnect.domain.Patient;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping(PatientRestController.PATIENT_API_BASE_PATH)
public class PatientRestController {

    static final String PATIENT_API_BASE_PATH = "/api/patient";
    static final String PATIENT_EMAIL = "/{patientEmail}";

    static final String PATIENT_UPLOAD_PHOTO = "/upload/{patientId}";
    private final PatientService patientService;
    private final ImgurService imgurService;
    private final PatientMapper patientMapper;


    @GetMapping(PATIENT_EMAIL)
    public PatientDTO patientDetails(
            @PathVariable String patientEmail
    ) {
        Patient patient = patientService.findByEmail(patientEmail);
        return patientMapper.map(patient);
    }

    @PatchMapping(PATIENT_UPLOAD_PHOTO)
    public String updateUsersPhoto(
            @RequestParam("image") MultipartFile image,
            @PathVariable("patientId") Integer patientId
    ) {
        String uploadedPhotoId = imgurService.uploadPhoto(image);
        imgurService.saveUploadedPhotoId(patientId, uploadedPhotoId);


        return "Image uploaded successfully for patient with ID: " + patientId;
    }

}
