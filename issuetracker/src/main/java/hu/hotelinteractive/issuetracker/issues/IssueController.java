package hu.hotelinteractive.issuetracker.issues;

import hu.hotelinteractive.issuetracker.customer.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private CustomerService customerService;



    @GetMapping("/")
    public String viewAllIssues(Model model) {

        return findPaginated(1, "issue_id", "desc", "-- Minden partner --",
                LocalDate.of(2000, 1, 1).toString(), LocalDate.now().toString(), model);

    }

    @GetMapping("/showNewIssueForm")
    public String showNewIssueForm(Model model) {
        // create model attribute to bind form data
        Issue issue = new Issue();
        issue.setOpenDate(LocalDate.now());
        issue.setWorkHours(0);
        model.addAttribute("issue", issue);
        model.addAttribute("issueGroups", issueService.getAllIssueGroup());
        model.addAttribute("customers", customerService.getAllCustomerSortByName());
        return "issueForm";
    }

    @PostMapping("/saveIssue")
    public String saveIssue(@Valid Issue issue, BindingResult bindingResult, Model model) {
        // save employee to database
        if (bindingResult.hasErrors()) {
            model.addAttribute("issueGroups", issueService.getAllIssueGroup());
            model.addAttribute("customers", customerService.getAllCustomerSortByName());
            return "issueForm";
        }

        if (issue.getRegId() == 0) {
            issue.setRegId(issueService.getNewRegId());
        }
        issueService.saveIssue(issue);
        return "redirect:/issues/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Issue issue = issueService.getIssueById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("issue", issue);
        model.addAttribute("issueGroups", issueService.getAllIssueGroup());
        model.addAttribute("customers", customerService.getAllCustomerSortByName());
        return "issueForm";
    }

    @GetMapping("/deleteIssue/{id}")
    public String deleteIssue(@PathVariable (value = "id") long id) {

        // call delete issue method
        issueService.deleteIssueById(id);
        return "redirect:/issues/";
    }

    @GetMapping("/page/{pageNo}")
    public String findPaginated(@PathVariable(value = "pageNo") int pageNo,
                                @RequestParam("sortField") String sortField,
                                @RequestParam("sortDir") String sortDir,
                                @RequestParam("customerName") String customerName,
                                @RequestParam("dateStart") String dateStart,
                                @RequestParam("dateEnd") String dateEnd,
                                Model model) {
        int pageSize = 8;

        Page<Issue> page = issueService.findPaginated(pageNo, pageSize, sortField, sortDir, customerName,
                LocalDate.parse(dateStart), LocalDate.parse(dateEnd));
        List<Issue> listIssues = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listIssues", listIssues);

        model.addAttribute("customerName", customerName);

        model.addAttribute("dateStart", dateStart);

        model.addAttribute("dateEnd", dateEnd);

        model.addAttribute("customers", customerService.getAllCustomerSortByName());


        return "issues";
    }

    @GetMapping("/teszt")
    public void teszt() {

        Page<Issue> teszt = issueService.findByDateAndCustomer(LocalDate.of(2016, 1, 1), LocalDate.now(), "Boscolo", Pageable.unpaged());

    }
}
