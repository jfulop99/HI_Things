package hu.hotelinteractive.issuetracker.issues;


import hu.hotelinteractive.issuetracker.IssueTrackerApplicationConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "issues")
@CloseDateConstraint
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
    @NotEmpty
    private String issueText;

    @Column(name = "issue_group")
    private String issueGroup;

    @Column(name = "close_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate closeDate;

    @Column(name = "issue_resolution")
    private String issueResolution;

    @Column(name = "work_hours")
    @Min(value = 0)
    @Max(value = 100)
    private int workHours;

    @WorkSheetNumberConstraint
    private String worksheet;

}
