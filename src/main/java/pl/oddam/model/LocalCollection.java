package pl.oddam.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Data
public class LocalCollection {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String mission;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<OrganizationTarget> organizationTarget;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private OrganizationType organizationType;
    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Set<OrganizationNeed> organizationNeed;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private City city;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private User createdByUser;
    private LocalDate dateEnd;

}
