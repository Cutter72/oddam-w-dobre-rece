package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.*;
import pl.oddam.model.dto.StepOneToThreeParameters;
import pl.oddam.repository.*;
import pl.oddam.service.OrganizationServiceImpl;

import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrganizationNeedRepository organizationNeedRepository;
    private final OrganizationTargetRepository organizationTargetRepository;
    private final OrganizationServiceImpl organizationServiceImpl;
    private final OrganizationRepository organizationRepository;
    private final CityRepository cityRepository;
    private final GiftRepository giftRepository;

    public UserController(OrganizationNeedRepository organizationNeedRepository, OrganizationTargetRepository organizationTargetRepository, OrganizationServiceImpl organizationServiceImpl, OrganizationRepository organizationRepository, CityRepository cityRepository, GiftRepository giftRepository) {
        this.organizationNeedRepository = organizationNeedRepository;
        this.organizationTargetRepository = organizationTargetRepository;
        this.organizationServiceImpl = organizationServiceImpl;
        this.organizationRepository = organizationRepository;
        this.cityRepository = cityRepository;
        this.giftRepository = giftRepository;
    }

    @GetMapping("")
    public String user(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess, @RequestParam(defaultValue = "1") int step) {
        if (step == 1) {
            if (sess.getAttribute("stepOneToThreeParameters") == null) {
                model.addAttribute("stepOneToThreeParameters", new StepOneToThreeParameters());
            } else {
                model.addAttribute("stepOneToThreeParameters", sess.getAttribute("stepOneToThreeParameters"));
            }
            model.addAttribute("user", customUser.getUser());
            model.addAttribute("organizationNeedList", organizationNeedRepository.findAll());
            model.addAttribute("organizationTargetList", organizationTargetRepository.findAll());
            model.addAttribute("cityList", cityRepository.findAll());
            if ((boolean) sess.getAttribute("isAdmin")) {
                model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
            }
            return "user";
        } else if (step == 4 && sess.getAttribute("stepOneToThreeParameters") != null) {
            StepOneToThreeParameters stepOneToThreeParameters = (StepOneToThreeParameters) sess.getAttribute("stepOneToThreeParameters");
            Set<Organization> organizationList = organizationServiceImpl.findAllByNameCityNeedTarget(stepOneToThreeParameters.getOrganizationName(), stepOneToThreeParameters.getCityId(), stepOneToThreeParameters.getNeedIdTab(), stepOneToThreeParameters.getTargetIdTab());
            if (organizationList.size() < 1) {
                organizationList = new HashSet<>(organizationRepository.findAll());
                model.addAttribute("noOrganizationFound", "Niestety żadna organizacja nie spełnia podanych kryteriów. Oto lista wszystkich organizacji: ");
            }
            model.addAttribute("organizationList", organizationList);
            model.addAttribute("bags", stepOneToThreeParameters.getBags());
            model.addAttribute("gift", new Gift());
            model.addAttribute("user", customUser.getUser());
            model.addAttribute("selectedNeedsToGive", organizationNeedRepository.findAllById(Arrays.asList(stepOneToThreeParameters.getNeedIdTab())));
            if ((boolean) sess.getAttribute("isAdmin")) {
                model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
            }
            return "user/userStep4";
        } else {
            return "redirect:/user";
        }
    }

    @PostMapping("")
    public String userFormStepOneSearchOrganizations(@AuthenticationPrincipal CurrentUser customUser, StepOneToThreeParameters stepOneToThreeParameters, HttpSession sess, Model model,
                                                     @RequestParam(defaultValue = "1") int step, Gift gift, @RequestParam(required = false) String date, @RequestParam(required = false) String time) {
        if (step == 1) {
            sess.setAttribute("stepOneToThreeParameters", stepOneToThreeParameters);
            Set<Organization> organizationList = organizationServiceImpl.findAllByNameCityNeedTarget(stepOneToThreeParameters.getOrganizationName(), stepOneToThreeParameters.getCityId(), stepOneToThreeParameters.getNeedIdTab(), stepOneToThreeParameters.getTargetIdTab());
            if (organizationList.size() < 1) {
                organizationList = new HashSet<>(organizationRepository.findAll());
                model.addAttribute("noOrganizationFound", "Niestety żadna organizacja nie spełnia podanych kryteriów. Oto lista wszystkich organizacji: ");
            }
            model.addAttribute("organizationList", organizationList);
            model.addAttribute("bags", stepOneToThreeParameters.getBags());
            model.addAttribute("gift", new Gift());
            model.addAttribute("user", customUser.getUser());
            model.addAttribute("selectedNeedsToGive", organizationNeedRepository.findAllById(Arrays.asList(stepOneToThreeParameters.getNeedIdTab())));
            if ((boolean)sess.getAttribute("isAdmin")) {
                model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
            }
            return "redirect:/user?step=4";
        } else if (step == 4) {
            User currentUser = customUser.getUser();
            gift.setUser(currentUser);
            if (!date.equalsIgnoreCase("")) {
                String[] dateInts = date.split("-");
                int year = Integer.parseInt(dateInts[0]);
                int month = Integer.parseInt(dateInts[1]);
                int day = Integer.parseInt(dateInts[2]);
                gift.setPreferredDateOfCollection(new Date(year - 1900, month - 1, day + 1));
            }
            if (!time.equalsIgnoreCase("")) {
                String[] timeInts = time.split(":");
                int hh = Integer.parseInt(timeInts[0]);
                int mm = Integer.parseInt(timeInts[1]);
                gift.setPreferredTimeOfCollection(new Time(hh + 1, mm, 0)); //why adding or substracting for database proper values?
            }
            gift.setBags(((StepOneToThreeParameters) sess.getAttribute("stepOneToThreeParameters")).getBags());
            giftRepository.save(gift);
            return "redirect:/user/form/success#Form";
        } else {
            return "redirect:/user";
        }

    }

    @GetMapping("/form/success")
    public String userFormSuccess(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        sess.removeAttribute("stepOneToThreeParameters");
        if ((boolean)sess.getAttribute("isAdmin")) {
            model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
        }
        return "user/formSuccess";
    }

    @GetMapping("/")
    public String userSlash() {
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String userProfile(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        model.addAttribute("user", customUser.getUser());
        if ((boolean)sess.getAttribute("isAdmin")) {
            model.addAttribute("adminPanel", "<li><a href=\"/admin\">Panel Admina</a></li>");
        }
        return "user/profile";
    }

    @GetMapping("/profile/")
    public String userProfileSlash() {
        return "redirect:/user/profile";
    }

}
