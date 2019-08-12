package pl.oddam.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private User user;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Organization organization;
    private Integer bags;
    private String street;
    private String city;
    private String postCode;
    private String callNumber;
    private LocalDateTime created;
    private LocalDateTime updated;
    private Date preferredDateOfCollection;
    private Time preferredTimeOfCollection;
    private String courierNote;
    private LocalDate dateCollected;
    private boolean collected;

    @PrePersist
    public void setCreationDate () {
        this.created = LocalDateTime.now();
    }

    @PreUpdate
    public void setUpdatedDate () {
        this.created = LocalDateTime.now();
    }
}
