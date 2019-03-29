package pl.oddam.service;

import pl.oddam.model.City;
import pl.oddam.model.Organization;
import pl.oddam.model.OrganizationNeed;
import pl.oddam.model.OrganizationTarget;

import java.util.List;
import java.util.Set;

public interface OrganizationService {
    Set<Organization> findAllByNameCityNeedTarget(String name, Long cityId, Long[] needIdTab, Long[] targetIdTab);
}
