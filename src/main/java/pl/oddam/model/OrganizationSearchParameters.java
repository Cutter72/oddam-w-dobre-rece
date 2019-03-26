package pl.oddam.model;

import lombok.Data;

import java.util.Set;

@Data
public class OrganizationSearchParameters {

    private Set<OrganizationNeed> needSet;
    private Set<OrganizationTarget> targetSet;
    private City city;
    private String organizationName;
}
