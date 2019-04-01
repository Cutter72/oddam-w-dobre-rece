package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.*;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationTargetRepository;
import pl.oddam.service.OrganizationServiceImpl;

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
    public String user(@AuthenticationPrincipal CurrentUser customUser, Model model) {
        model.addAttribute("user", customUser.getUser());
        model.addAttribute("organizationNeedList", organizationNeedRepository.findAll());
        model.addAttribute("organizationTargetList", organizationTargetRepository.findAll());
        model.addAttribute("cityList", cityRepository.findAll());
        return "user";
    }

    @PostMapping("")
    public String userFormStepOneSearchOrganizations(@RequestParam(required = false) Long[] needIdTab,
                                                     @RequestParam(required = false) Long cityId,
                                                     @RequestParam(required = false) Long[] targetIdTab,
                                                     @RequestParam(required = false) String organizationName,
                                                     @RequestParam(required = false) Integer bags,
                                                     Model model) {

        model.addAttribute("organizationList", organizationServiceImpl.findAllByNameCityNeedTarget(organizationName, cityId, needIdTab, targetIdTab));
        model.addAttribute("bags", bags);
        model.addAttribute("selectedNeedsToGive", organizationNeedRepository.findAllById(Arrays.asList(needIdTab)));
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
