package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import pl.oddam.model.*;
import pl.oddam.model.dto.StepOneToThreeParameters;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationTargetRepository;
import pl.oddam.service.OrganizationServiceImpl;

import javax.servlet.http.HttpSession;
import java.util.Arrays;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrganizationNeedRepository organizationNeedRepository;
    private final OrganizationTargetRepository organizationTargetRepository;
    private final OrganizationServiceImpl organizationServiceImpl;
    private final CityRepository cityRepository;

    public UserController(OrganizationNeedRepository organizationNeedRepository, OrganizationTargetRepository organizationTargetRepository, OrganizationServiceImpl organizationServiceImpl, CityRepository cityRepository) {
        this.organizationNeedRepository = organizationNeedRepository;
        this.organizationTargetRepository = organizationTargetRepository;
        this.organizationServiceImpl = organizationServiceImpl;
        this.cityRepository = cityRepository;
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
        return "redirect:/user/form";
    }

    @GetMapping("/form")
    public String userForm(@AuthenticationPrincipal CurrentUser customUser, Model model, HttpSession sess) {
        StepOneToThreeParameters stepOneToThreeParameters = (StepOneToThreeParameters)sess.getAttribute("stepOneToThreeParameters");
        model.addAttribute("organizationList", organizationServiceImpl.findAllByNameCityNeedTarget(stepOneToThreeParameters.getOrganizationName(), stepOneToThreeParameters.getCityId(), stepOneToThreeParameters.getNeedIdTab(), stepOneToThreeParameters.getTargetIdTab()));
        model.addAttribute("bags", stepOneToThreeParameters.getBags());
        model.addAttribute("gift", new Gift());
        model.addAttribute("selectedNeedsToGive", organizationNeedRepository.findAllById(Arrays.asList(stepOneToThreeParameters.getNeedIdTab())));
        return "user/userStep4";
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
