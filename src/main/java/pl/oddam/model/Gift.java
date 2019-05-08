package pl.oddam.model;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Data
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Organization organization;
    private Integer bags;
    private String street;
    private String city;
    private String postCode;
    private String callNumber;
    private LocalDateTime created;
    private Date preferredDateOfCollection;
    private Time preferredTimeOfCollection;
    private String courierNote;
    private LocalDateTime timeCollected;
    private boolean collected;

    @PrePersist
    public void setCreationDate () {
        this.created = LocalDateTime.now();
    }
}
