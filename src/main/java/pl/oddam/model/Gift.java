package pl.oddam.model;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

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
    @DateTimeFormat(pattern = "mm/dd/yyyy")
    private Date preferredDateOfCollection;
//    @DateTimeFormat(pattern = "hh:mm")
    private Time preferredTimeOfCollection;
    private String courierNote;
    private LocalDateTime timeCollected;
    private boolean collected;
}
