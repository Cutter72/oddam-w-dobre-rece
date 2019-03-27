package pl.oddam.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.*;
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
    public String userFormStepOneSearchOrganizations(@RequestParam(required = false) Long[] needList,
                                                     @RequestParam(required = false) Long cityId,
                                                     @RequestParam(required = false) Long[] targetList,
                                                     @RequestParam(required = false) String organizationName) {

        //search organizations by name
        Set<Organization> organizationSetByName = new HashSet<>();
        if (organizationName.equals("")) {
            System.out.println("Nie wybrano nazwy organizacji");
        } else {
            Organization foundOrganization = organizationRepository.findByName(organizationName);
            if (foundOrganization == null) {
                System.out.println("Nie znaleziono organizacji po nazwie");
            } else {
                System.out.println(foundOrganization.getName());
                organizationSetByName.add(foundOrganization);
            }
        }

        //search organizations by city
        Set<Organization> organizationSetByCity;
        if (cityId == 0L) {
            System.out.println("Nie wybrano nazwy organizacji");
            organizationSetByCity = new HashSet<>(organizationRepository.findAll());
        } else {
            organizationSetByCity = new HashSet<>();
            Organization foundOrganization = organizationRepository.findByName(organizationName);
            if (foundOrganization == null) {
                System.out.println("Nie znaleziono organizacji po mieście");
            } else {
                System.out.println(foundOrganization.getName());
                organizationSetByCity.add(foundOrganization);
            }
        }

        //search organizations by needs
        Set<Organization> organizationSetByNeeds = new HashSet<>();
        if (needList == null) {
            System.out.println("Nie wybrano rzeczy");
        } else {
            for (Long id : needList) {
                Set<OrganizationNeed> needToSearch = new HashSet<>();
                needToSearch.add(organizationNeedRepository.findById(id).get());
                Set<Organization> foundOrganizationSet = organizationRepository.findAllByNeed(needToSearch);
                for (Organization organization : foundOrganizationSet) {
                    System.out.println(organization.getName());
                    organizationSetByNeeds.add(organization);
                }
            }
        }

        //search organizations by targets
        Set<Organization> organizationSetByTargets;
        if (targetList == null) {
            System.out.println("Nie wybrano grupy docelowej");
            organizationSetByTargets = new HashSet<>(organizationRepository.findAll());
        } else {
            for (Long id : targetList) {
                organizationSetByTargets = new HashSet<>();
                Set<OrganizationTarget> targetToSearch = new HashSet<>();
                targetToSearch.add(organizationTargetRepository.findById(id).get());
                Set<Organization> foundOrganizationSet = organizationRepository.findAllByTarget(targetToSearch);
                for (Organization organization : foundOrganizationSet) {
                    System.out.println(organization.getName());
                    organizationSetByTargets.add(organization);
                }
            }
        }

        System.out.println(organizationSetByName.retainAll(organizationSetByCity));
//TODO : final Set of organization to display

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
