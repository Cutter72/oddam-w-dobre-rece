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


    @Override
    public Map<Long, String> getValueLabel() {
        Map<Long, String> valueLabelMap = new HashMap<>();
        List<OrganizationType> organizationList = organizationTypeRepository.findAll();
        for (OrganizationType organizationType : organizationList) {
            valueLabelMap.put(organizationType.getId(), organizationType.getType());
        }
        return valueLabelMap;
    }
}



