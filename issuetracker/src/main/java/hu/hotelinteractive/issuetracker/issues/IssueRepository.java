package hu.hotelinteractive.issuetracker.issues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {

    @Query(value = "select * from issues order by issue_id desc limit 1", nativeQuery = true)
    Optional<Issue> findMaxId();


}
