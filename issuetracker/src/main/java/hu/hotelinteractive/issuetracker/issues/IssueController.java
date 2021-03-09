package hu.hotelinteractive.issuetracker.issues;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/issues")
public class IssueController {

    @Autowired
    private IssueService issueService;


    @GetMapping("/")
    public String viewAllIssues(Model model) {

        return findPaginated(1, "id", "desc", model);

    }

    @GetMapping("/showNewIssueForm")
    public String showNewIssueForm(Model model) {
        // create model attribute to bind form data
        Issue issue = new Issue();
        model.addAttribute("issue", issue);
        model.addAttribute("localDate", LocalDate.now());
        return "new_issue";
    }

    @PostMapping("/saveIssue")
    public String saveIssue(@ModelAttribute("issue") Issue issue) {
        // save employee to database
        issueService.saveIssue(issue);
        return "redirect:/issues/";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable ( value = "id") long id, Model model) {

        // get employee from the service
        Issue issue = issueService.getIssueById(id);

        // set employee as a model attribute to pre-populate the form
        model.addAttribute("issue", issue);
        return "update_issue";
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
                                Model model) {
        int pageSize = 8;

        Page<Issue> page = issueService.findPaginated(pageNo, pageSize, sortField, sortDir);
        List<Issue> listIssues = page.getContent();

        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("totalItems", page.getTotalElements());

        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        model.addAttribute("reverseSortDir", sortDir.equals("asc") ? "desc" : "asc");

        model.addAttribute("listIssues", listIssues);
        return "issues";
    }
}
