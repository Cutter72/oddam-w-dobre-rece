package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.*;
import pl.oddam.model.dto.StepOneToThreeParameters;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.GiftRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationTargetRepository;
import pl.oddam.service.OrganizationServiceImpl;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrganizationNeedRepository organizationNeedRepository;
    private final OrganizationTargetRepository organizationTargetRepository;
    private final OrganizationServiceImpl organizationServiceImpl;
    private final CityRepository cityRepository;
    private final GiftRepository giftRepository;

    public UserController(OrganizationNeedRepository organizationNeedRepository, OrganizationTargetRepository organizationTargetRepository, OrganizationServiceImpl organizationServiceImpl, CityRepository cityRepository, GiftRepository giftRepository) {
        this.organizationNeedRepository = organizationNeedRepository;
        this.organizationTargetRepository = organizationTargetRepository;
        this.organizationServiceImpl = organizationServiceImpl;
        this.cityRepository = cityRepository;
        this.giftRepository = giftRepository;
    }

    @GetMapping("")
    public String user(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        if (sess.getAttribute("stepOneToThreeParameters") == null) {
            sess.setAttribute("stepOneToThreeParameters", new StepOneToThreeParameters());
        }
        model.addAttribute("stepOneToThreeParameters", sess.getAttribute("stepOneToThreeParameters"));
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("organizationNeedList", organizationNeedRepository.findAll());
        model.addAttribute("organizationTargetList", organizationTargetRepository.findAll());
        model.addAttribute("cityList", cityRepository.findAll());
        return "user";
    }

    @PostMapping("")
    public String userFormStepOneSearchOrganizations(StepOneToThreeParameters stepOneToThreeParameters, HttpSession sess) {
        sess.setAttribute("stepOneToThreeParameters", stepOneToThreeParameters);
        return "redirect:/user/form/step2#Form";
    }

    @GetMapping("/form/step2")
    public String userForm2(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        StepOneToThreeParameters stepOneToThreeParameters = (StepOneToThreeParameters)sess.getAttribute("stepOneToThreeParameters");
        if (stepOneToThreeParameters == null) {
            return "redirect:/user";
        }
        model.addAttribute("organizationList", organizationServiceImpl.findAllByNameCityNeedTarget(stepOneToThreeParameters.getOrganizationName(), stepOneToThreeParameters.getCityId(), stepOneToThreeParameters.getNeedIdTab(), stepOneToThreeParameters.getTargetIdTab()));
        model.addAttribute("bags", stepOneToThreeParameters.getBags());
        model.addAttribute("gift", new Gift());
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("selectedNeedsToGive", organizationNeedRepository.findAllById(Arrays.asList(stepOneToThreeParameters.getNeedIdTab())));
        return "user/userStep4";
    }

    @PostMapping("/form/step2")
    public String userFormSummary(Gift gift, @RequestParam(required = false) String date, @RequestParam(required = false) String time, @AuthenticationPrincipal CurrentUser customUser) {
        User currentUser = customUser.getUser();
        gift.setUser(currentUser);
        if (date.equalsIgnoreCase("")) {
            //do nothing
        } else {
            String[] dateInts = date.split("-");
            int year = Integer.parseInt(dateInts[0]);
            int month = Integer.parseInt(dateInts[1]);
            int day = Integer.parseInt(dateInts[2]);
            gift.setPreferredDateOfCollection(new Date(year-1900, month-1,day+1));
        }

        if (time.equalsIgnoreCase("")) {
            //do nothing
        } else {
            String[] timeInts = time.split(":");
            int hh = Integer.parseInt(timeInts[0]);
            int mm = Integer.parseInt(timeInts[1]);
            gift.setPreferredTimeOfCollection(new Time(hh + 1, mm, 0)); //why adding or substracting for database proper values?
        }

        giftRepository.save(gift);
        return "redirect:/user/form/success#Form";
    }

    @GetMapping("/form/success")
    public String userFormSuccess(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        sess.removeAttribute("stepOneToThreeParameters");
        return "user/formSuccess";
    }

    @GetMapping("/")
    public String userSlash() {
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        return "user/profile";
    }

    @GetMapping("/profile/")
    public String userProfileSlash() {
        return "redirect:/user/profile";
    }

}
