package pl.oddam.service;

import org.springframework.stereotype.Service;
import pl.oddam.repository.OrganizationRepository;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;

    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }


}
