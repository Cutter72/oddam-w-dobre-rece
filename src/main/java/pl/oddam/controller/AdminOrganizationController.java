package pl.oddam.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.oddam.model.Organization;
import pl.oddam.repository.OrganizationTypeRepository;
import pl.oddam.service.OrganizationTypeServiceImpl;

@Controller
@RequestMapping("/admin/organization")
public class AdminOrganizationController {
    private final OrganizationTypeRepository organizationTypeRepository;
    private final OrganizationTypeServiceImpl organizationTypeServiceImpl;

    public AdminOrganizationController(OrganizationTypeRepository organizationTypeRepository, OrganizationTypeServiceImpl organizationTypeServiceImpl) {
        this.organizationTypeRepository = organizationTypeRepository;
        this.organizationTypeServiceImpl = organizationTypeServiceImpl;
    }

    @GetMapping("")
    public String adminOrganization(Model model) {
        model.addAttribute("newOrganization", new Organization());
        model.addAttribute("organizationTypeList", organizationTypeServiceImpl.getValueLabel());
        return "adminOrganization";
    }
    @GetMapping("/")
    public String adminOrganizationSlash() {
        return "redirect:/admin/organization";
    }


}
