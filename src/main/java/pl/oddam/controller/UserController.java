package pl.oddam.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.oddam.model.*;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationRepository;
import pl.oddam.repository.OrganizationTargetRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
public class UserController {
    private final Logger log = LogManager.getLogger(UserController.class);
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
    public String userFormStepOneSearchOrganizations(@RequestParam(required = false) Long[] needList,
                                                     @RequestParam(required = false) Long cityId,
                                                     @RequestParam(required = false) Long[] targetList,
                                                     @RequestParam(required = false) String organizationName,
                                                     Model model) {

        Iterable<Long> needListIterable = null;
        Iterable<Long> targetListIterable = null;

        boolean cityIsEmpty = true;
        if (cityId != 0) {
            cityIsEmpty = false;
        }
        boolean needListIsEmpty = true;
        if (needList != null) {
            needListIsEmpty = false;
            needListIterable = Arrays.asList(needList);
        }
        boolean targetListIsEmpty = true;
        if (targetList != null) {
            targetListIsEmpty = false;
            targetListIterable = Arrays.asList(targetList);
        }

        Set<Organization> finalOrganizationSet = new HashSet<>();
        Set<Organization> organizationSetToAdd;

        if (!organizationName.equalsIgnoreCase("")) {
            Set<Organization> organizationListNameLike = organizationRepository.findAllByNameIgnoreCaseContaining(organizationName);
            finalOrganizationSet.addAll(organizationListNameLike);


        }

        if (cityIsEmpty) {
            if (needListIsEmpty && targetListIsEmpty) {
                organizationSetToAdd = new HashSet<>(organizationRepository.findAll());
            } else if (needListIsEmpty) {
                organizationSetToAdd = organizationRepository.findAllByTargetIsIn(organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsEmpty) {
                List<OrganizationNeed> foundNeedSet = organizationNeedRepository.findAllById(needListIterable);
                organizationSetToAdd = organizationRepository.findAllByNeedIsIn(foundNeedSet);
            } else {
                organizationSetToAdd = organizationRepository.findAllByNeedIsInAndTargetIsIn(organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        } else {
            if (needListIsEmpty && targetListIsEmpty) {
                organizationSetToAdd = organizationRepository.findAllByCity(cityRepository.findById(cityId).get());
            } else if (needListIsEmpty) {
                organizationSetToAdd = organizationRepository.findAllByCityAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsEmpty) {
                organizationSetToAdd = organizationRepository.findAllByCityAndNeedIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable));
            } else {
                organizationSetToAdd = organizationRepository.findAllByCityAndNeedIsInAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        }
        finalOrganizationSet.addAll(organizationSetToAdd);

        for (Organization organization : finalOrganizationSet) {
            System.out.println(organization.getId());
            System.out.println(organization.getName());
        }

        model.addAttribute("organizationList", finalOrganizationSet);

//TODO : final Set of organization to display

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
