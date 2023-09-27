package code.medconnect.api.controller;

import code.medconnect.business.ImgurService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class HomeController {

    public static final String HOME = "/";

    @RequestMapping(value = HOME, method = RequestMethod.GET)
    public String homePage() {
        return "home";
    }



}
