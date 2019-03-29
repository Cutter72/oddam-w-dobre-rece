package pl.oddam.service;

import org.springframework.stereotype.Service;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationNeed;
import pl.oddam.repository.CityRepository;
import pl.oddam.repository.OrganizationNeedRepository;
import pl.oddam.repository.OrganizationRepository;
import pl.oddam.repository.OrganizationTargetRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationNeedRepository organizationNeedRepository;
    private final OrganizationTargetRepository organizationTargetRepository;
    private final OrganizationRepository organizationRepository;
    private final CityRepository cityRepository;

    public OrganizationServiceImpl(OrganizationNeedRepository organizationNeedRepository, OrganizationTargetRepository organizationTargetRepository, OrganizationRepository organizationRepository, CityRepository cityRepository) {
        this.organizationNeedRepository = organizationNeedRepository;
        this.organizationTargetRepository = organizationTargetRepository;
        this.organizationRepository = organizationRepository;
        this.cityRepository = cityRepository;
    }

    @Override
    public Set<Organization> findAllByNameCityNeedTarget(String organizationName, Long cityId, Long[] needIdTab, Long[] targetIdTab) {

        Iterable<Long> needListIterable = null;
        Iterable<Long> targetListIterable = null;

        boolean cityIsEmpty = true;
        if (cityId != 0) {
            cityIsEmpty = false;
        }
        boolean needListIsEmpty = true;
        if (needIdTab != null) {
            needListIsEmpty = false;
            needListIterable = Arrays.asList(needIdTab);
        }
        boolean targetListIsEmpty = true;
        if (targetIdTab != null) {
            targetListIsEmpty = false;
            targetListIterable = Arrays.asList(targetIdTab);
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


//TODO : final Set of organization to display

        return finalOrganizationSet;
    }
}
