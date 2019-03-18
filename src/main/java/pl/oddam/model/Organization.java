package pl.oddam.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
public class Organization {
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
//    @JoinTable(name = "organization_type", joinColumns = @JoinColumn(name = "organization_id"),
//            inverseJoinColumns = @JoinColumn(name = "type_id"))
    private OrganizationType organizationType;
}
