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

        boolean cityIsZero = true;
        if (cityId != 0) {
            cityIsZero = false;
        }
        boolean needListIsNull = isNull(needIdTab);
        boolean targetListIsNull = isNull(targetIdTab);

        if (!needListIsNull) {
            needListIterable = Arrays.asList(needIdTab);
        }
        if (!targetListIsNull) {
            targetListIterable = Arrays.asList(targetIdTab);
        }
        Set<Organization> finalOrganizationSet = new HashSet<>();

        if (!organizationName.equalsIgnoreCase("")) {
            Set<Organization> organizationListNameLike = organizationRepository.findAllByNameIgnoreCaseContaining(organizationName);
            finalOrganizationSet.addAll(organizationListNameLike);
        }

        finalOrganizationSet.addAll(returnAllByConditions(cityIsZero, needListIsNull, targetListIsNull, needListIterable, targetListIterable, cityId));
//TODO : final Set of organization to display
        return finalOrganizationSet;
    }

    <T> boolean isNull(T itemToCheck) {
        if (itemToCheck == null) {
            return true;
        }
        return false;
    }

    Set<Organization> returnAllByConditions(boolean cityIsZero, boolean needListIsNull, boolean targetListIsNull,
                                            Iterable<Long> needListIterable, Iterable<Long> targetListIterable, Long cityId) {

        Set<Organization> finalOrganizationSet = new HashSet<>();
        Set<Organization> organizationSetToAdd;

        if (cityIsZero) {
            if (needListIsNull && targetListIsNull) {
                organizationSetToAdd = new HashSet<>(organizationRepository.findAll());
            } else if (needListIsNull) {
                organizationSetToAdd = organizationRepository.findAllByTargetIsIn(organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsNull) {
                List<OrganizationNeed> foundNeedSet = organizationNeedRepository.findAllById(needListIterable);
                organizationSetToAdd = organizationRepository.findAllByNeedIsIn(foundNeedSet);
            } else {
                organizationSetToAdd = organizationRepository.findAllByNeedIsInAndTargetIsIn(organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        } else {
            if (needListIsNull && targetListIsNull) {
                organizationSetToAdd = organizationRepository.findAllByCity(cityRepository.findById(cityId).get());
            } else if (needListIsNull) {
                organizationSetToAdd = organizationRepository.findAllByCityAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationTargetRepository.findAllById(targetListIterable));
            } else if (targetListIsNull) {
                organizationSetToAdd = organizationRepository.findAllByCityAndNeedIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable));
            } else {
                organizationSetToAdd = organizationRepository.findAllByCityAndNeedIsInAndTargetIsIn(cityRepository.findById(cityId).get(),
                        organizationNeedRepository.findAllById(needListIterable),
                        organizationTargetRepository.findAllById(targetListIterable));
            }
        }
        finalOrganizationSet.addAll(organizationSetToAdd);
        return finalOrganizationSet;
    }


}
