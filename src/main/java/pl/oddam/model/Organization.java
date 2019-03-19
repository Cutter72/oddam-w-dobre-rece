package pl.oddam.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
public class Organization {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String mission;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<OrganizationTarget> target;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private OrganizationType type;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<OrganizationNeed> need;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private City city;
}
