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
    @ResponseBody
    public String userFormStepOneSearchOrganizations(@RequestParam(required = false) Long[] needList,
                                                     @RequestParam(required = false) Long cityId,
                                                     @RequestParam(required = false) Long[] targetList,
                                                     @RequestParam(required = false) String organizationName) {

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

        Set<Organization> finalOrganizationSet;

        if (cityIsEmpty) {
            if (needListIsEmpty && targetListIsEmpty) {
                finalOrganizationSet = new HashSet<>(organizationRepository.findAll());
            } else if (needListIsEmpty) {
                finalOrganizationSet = organizationRepository.findAllByTargetIsIn(organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsEmpty) {
                List<OrganizationNeed> foundNeedSet = organizationNeedRepository.findAllById(needListIterable);
                for (OrganizationNeed organizationNeed : foundNeedSet) {
                    System.out.println(organizationNeed.getName());
                }
                finalOrganizationSet = organizationRepository.findAllByNeedIsIn(foundNeedSet);
            } else {
                finalOrganizationSet = organizationRepository.findAllByNeedIsInAndTargetIsIn(organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        } else {
            if (needListIsEmpty && targetListIsEmpty) {
                finalOrganizationSet = organizationRepository.findAllByCity(cityRepository.findById(cityId).get());
            } else if (needListIsEmpty) {
                finalOrganizationSet = organizationRepository.findAllByCityAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsEmpty) {
                finalOrganizationSet = organizationRepository.findAllByCityAndNeedIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable));
            } else {
                finalOrganizationSet = organizationRepository.findAllByCityAndNeedIsInAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        }

        for (Organization organization : finalOrganizationSet) {
            System.out.println(organization.getId());
            System.out.println(organization.getName());
        }

//        //search organizations by name
//        Set<Organization> organizationSetByName = new HashSet<>();
//        if (organizationName.equals("")) {
//            log.info("Nie wybrano nazwy organizacji");
//        } else {
//            Organization foundOrganization = organizationRepository.findByName(organizationName);
//            if (foundOrganization == null) {
//                log.info("Nie znaleziono organizacji po nazwie");
//            } else {
//                log.info(foundOrganization.getName());
//                organizationSetByName.add(foundOrganization);
//            }
//        }
//
//        //search organizations by city
//        Set<Organization> organizationSetByCity;
//        if (cityId == 0L) {
//            log.info("Nie wybrano nazwy organizacji");
//            organizationSetByCity = new HashSet<>(organizationRepository.findAll());
//        } else {
//            organizationSetByCity = new HashSet<>();
//            Organization foundOrganization = organizationRepository.findByName(organizationName);
//            if (foundOrganization == null) {
//                log.info("Nie znaleziono organizacji po mieście");
//            } else {
//                log.info(foundOrganization.getName());
//                organizationSetByCity.add(foundOrganization);
//            }
//        }
//
//        //search organizations by needs
//        Set<Organization> organizationSetByNeeds = new HashSet<>();
//        if (needList == null) {
//            log.info("Nie wybrano rzeczy");
//        } else {
//            for (Long id : needList) {
//                Set<OrganizationNeed> needToSearch = new HashSet<>();
//                needToSearch.add(organizationNeedRepository.findById(id).get());
//                Set<Organization> foundOrganizationSet = organizationRepository.findAllByNeed(needToSearch);
//                for (Organization organization : foundOrganizationSet) {
//                    log.info(organization.getName());
//                    organizationSetByNeeds.add(organization);
//                }
//            }
//        }
//
//        //search organizations by targets
//        Set<Organization> organizationSetByTargets;
//        if (targetList == null) {
//            log.info("Nie wybrano grupy docelowej");
//            organizationSetByTargets = new HashSet<>(organizationRepository.findAll());
//        } else {
//            for (Long id : targetList) {
//                organizationSetByTargets = new HashSet<>();
//                Set<OrganizationTarget> targetToSearch = new HashSet<>();
//                targetToSearch.add(organizationTargetRepository.findById(id).get());
//                Set<Organization> foundOrganizationSet = organizationRepository.findAllByTarget(targetToSearch);
//                for (Organization organization : foundOrganizationSet) {
//                    log.info(organization.getName());
//                    organizationSetByTargets.add(organization);
//                }
//            }
//        }
//
//        log.info(organizationSetByName.retainAll(organizationSetByCity));
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
