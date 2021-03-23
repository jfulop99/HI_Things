package hu.hotelinteractive.issuetracker.issues;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query(value = "select * from issues order by issue_id desc limit 1", nativeQuery = true)
    Optional<Issue> findMaxId();

    Page<Issue> findByCustomerName(String customerName, Pageable pageable);

    Page<Issue> findByOpenDate(LocalDate dateStart, LocalDate dateEnd, Pageable pageable);

    @Query(value = "select * from issues where open_date between :dateStart and :dateEnd and customer_name like :customerName", nativeQuery = true)
    Page<Issue> findByDateAndCustomer(LocalDate dateStart, LocalDate dateEnd, String customerName, Pageable pageable);
}
