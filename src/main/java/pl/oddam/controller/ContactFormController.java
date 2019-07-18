package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.oddam.service.EmailServiceImpl;

@Controller
@RequestMapping("/contact")
public class ContactFormController {
    private final EmailServiceImpl emailService;

    public ContactFormController(EmailServiceImpl emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send")
    public String send(@RequestParam String name, @RequestParam String email, @RequestParam String text) {
        emailService.sendSimpleMessage("oddam.w.dobre.rece@interia.pl", "oddam.w.dobre.rece@interia.pl", "Kontakt poprzez formularz od " + name, name + " " + email + " napisał/a\n\n\"" + text + "\"");
        emailService.sendSimpleMessage("oddam.w.dobre.rece@interia.pl", email, "Dziękujęmy " + name + " za kontakt.", "Twoja widomosć jaką otrzymaliśmy to: \n\n\"" + text + "\"\n\n Odpowiemy tak szybko jak to możliwe.\nZ poważaniem zespół Oddam w dobre ręce :)");
        return "sendSuccess";
    }
}
