package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.City;
import pl.oddam.model.CurrentUser;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationNeed;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationRepository;
import pl.oddam.repository.OrganizationTargetRepository;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final OrganizationNeedRepository organizationNeedRepository;
    private final OrganizationTargetRepository organizationTargetRepository;
    private final OrganizationRepository organizationRepository;
    private final CityRepository cityRepository;

    public UserController(OrganizationNeedRepository organizationNeedRepository, OrganizationTargetRepository organizationTargetRepository, OrganizationRepository organizationRepository, CityRepository cityRepository) {
        this.organizationNeedRepository = organizationNeedRepository;
        this.organizationTargetRepository = organizationTargetRepository;
        this.organizationRepository = organizationRepository;
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
    @ResponseBody
    public String userPost(@RequestParam(required = false) Long[] needList, @RequestParam City city) {
        Set<Organization> organizationSet = new HashSet<>();
        //search organizations by needs
        if (needList == null) {
            System.out.println("Nie wybrano rzeczy");
        } else {
            for (Long id : needList) {
                Set<OrganizationNeed> needToSearch = new HashSet<>();
                needToSearch.add(organizationNeedRepository.findById(id).get());
                Set<Organization> foundOrganizationSet = new HashSet<>();
                foundOrganizationSet = organizationRepository.findAllByNeed(needToSearch);
                for (Organization organization : foundOrganizationSet) {
                    organizationSet.add(organization);
                }
            }
        }


        return "wejszło";
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
