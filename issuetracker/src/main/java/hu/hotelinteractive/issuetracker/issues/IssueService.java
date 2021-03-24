package hu.hotelinteractive.issuetracker.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    @Autowired
    private IssueRepository issueRepository;

    @Autowired
    private IssueGroupRepository issueGroupRepository;

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

    public Page<Issue> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String customerName,
                                     LocalDate dateStart, LocalDate dateEnd, String issueGroupName) {

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortField).ascending() :
                Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);

        String queryCustomerName;
        String queryIssueGroupName;
        if (customerName.contains("--")) {
            queryCustomerName = "%";
        }
        else {
            queryCustomerName = customerName;
        }
        if (issueGroupName.contains("--")) {
            queryIssueGroupName = "%";
        }
        else {
            queryIssueGroupName = issueGroupName;
        }

        return issueRepository.findByDateAndCustomer(dateStart, dateEnd, queryCustomerName, queryIssueGroupName, pageable);


//        if (customerName.contains("--")) {
//            return issueRepository.findAll(pageable);
//        }
//        else
//        {
//            return issueRepository.findByCustomerName(customerName, pageable);
//        }


    }

    public long getNewRegId() {
        long regId = 0L;
        Optional<Issue> lastIssue = issueRepository.findMaxId();
        if (lastIssue.isPresent()) {
            regId = lastIssue.get().getRegId() +1;
        }

        return regId;
    }

    public List<IssueGroup> getAllIssueGroup() {
        Sort sort = Sort.by("name").ascending();
        return issueGroupRepository.findAll(sort);
    }

    public Page<Issue> findByDateAndCustomer(LocalDate dateStart, LocalDate dateEnd, String customerName, String issueGroupName, Pageable pageable) {
        return issueRepository.findByDateAndCustomer(dateStart, dateEnd, customerName, issueGroupName, pageable);
    }

    public List<Issue> findForPrinting (LocalDate dateStart, LocalDate dateEnd, String customerName, String issueGroupName, String sortField, String sortDir) {
        String queryCustomerName;
        String queryIssueGroupName;
        if (customerName.contains("--")) {
            queryCustomerName = "%";
        }
        else {
            queryCustomerName = customerName;
        }
        if (issueGroupName.contains("--")) {
            queryIssueGroupName = "%";
        }
        else {
            queryIssueGroupName = issueGroupName;
        }

        return issueRepository.findForPrinting(dateStart, dateEnd, queryCustomerName, queryIssueGroupName, sortField);
    }

}
