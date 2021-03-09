package hu.hotelinteractive.issuetracker.issues;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issues")
public class Issue {

    @Id
    @Column(name = "issue_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "registration_id")
    private long regId;

    @Column(name = "open_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate openDate;

    @Column(name = "customer_name")
    private String customerName;

    @Column(name = "issue_text")
    private String issueText;

    @Column(name = "issue_group")
    private String issueGroup;

    @Column(name = "close_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @Column(name = "issue_resolution")
    private String issueResolution;

    @Column(name = "work_hours")
    private int workHours;

    private String worksheet;
}
