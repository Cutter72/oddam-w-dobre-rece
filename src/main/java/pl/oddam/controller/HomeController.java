package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("")
public class HomeController {

    @GetMapping("")
    public String home() {
        return "home";
    }

//    @GetMapping("/error")
//    public String error() {
//        return "error";
//    }
    @GetMapping("/test")
    public String testingViews() {
        return "user/formSuccess";
    }
}
