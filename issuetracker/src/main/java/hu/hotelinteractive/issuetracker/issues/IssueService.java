package hu.hotelinteractive.issuetracker.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    public List<Issue> getAllIssue() {
        return issueRepository.findAll();
    }

    public void saveIssue(Issue issue) {
        issueRepository.save(issue);
    }

    public Issue getIssueById(long id) {

        Optional<Issue> issue = issueRepository.findById(id);

        return issue.orElseThrow(() -> new IllegalArgumentException("Issue not found for Id : " + id));
    }

    public void deleteIssueById(long id) {
        issueRepository.deleteById(id);
    }

    public Page<Issue> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        return issueRepository.findAll(pageable);

    }

}
