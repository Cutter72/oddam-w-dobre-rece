package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.service.EmailServiceImpl;

@Controller
@RequestMapping("/contact")
public class ContactFormController {
    private final EmailServiceImpl emailService;

    public ContactFormController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/send")
    public String send() {
        emailService.sendSimpleMessage("cutter72@o2.pl","TESTTESTTEST","TESTTESTTEST");
//        emailService.sendSimpleMessage("oddam.w.dobre.rece@o2.pl","TESTTESTTEST","TESTTESTTEST ąęółźżćń");
        return "sendSuccess";
    }

    @PostMapping("/send")
    public String send(Model model) {
//        emailService.sendSimpleMessage("cutter72@o2.pl","TESTTESTTEST","TESTTESTTEST ąęółźżćń");
//        emailService.sendSimpleMessage("oddam.w.dobre.rece@o2.pl","TESTTESTTEST","TESTTESTTEST ąęółźżćń");
        return "sendSuccess";
    }
}
