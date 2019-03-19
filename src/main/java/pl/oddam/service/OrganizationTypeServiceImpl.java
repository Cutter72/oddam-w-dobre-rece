package pl.oddam.service;

import org.springframework.stereotype.Service;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationType;
import pl.oddam.repository.OrganizationTypeRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationTypeServiceImpl implements OrganizationTypeService {
    private final OrganizationTypeRepository organizationTypeRepository;

    public OrganizationTypeServiceImpl(OrganizationTypeRepository organizationTypeRepository) {
        this.organizationTypeRepository = organizationTypeRepository;
    }
}



