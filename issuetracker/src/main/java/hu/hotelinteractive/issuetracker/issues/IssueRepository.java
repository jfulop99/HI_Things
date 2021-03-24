package hu.hotelinteractive.issuetracker.issues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query(value = "select * from issues order by issue_id desc limit 1", nativeQuery = true)
    Optional<Issue> findMaxId();

    Page<Issue> findByCustomerName(String customerNameIn, Pageable pageable);

    Page<Issue> findByOpenDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    @Query(value = "select i from Issue i where openDate between :dateStart and :dateEnd and customerName like :customerNameIn and issueGroup like :issueGroupName")
    Page<Issue> findByDateAndCustomer(LocalDate dateStart, LocalDate dateEnd, String customerNameIn, String issueGroupName, Pageable pageable);

    @Query(value = "select i from Issue i where openDate between :dateStart and :dateEnd and customerName like :customerNameIn and issueGroup like :issueGroupName order by :sortField")
    List<Issue> findForPrinting(LocalDate dateStart, LocalDate dateEnd, String customerNameIn, String issueGroupName, String sortField);
}
