package hu.hotelinteractive.issuetracker.issues;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssueGroupRepository extends JpaRepository<IssueGroup, Long> {

}
