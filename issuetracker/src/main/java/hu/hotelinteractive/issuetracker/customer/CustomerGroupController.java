package hu.hotelinteractive.issuetracker.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/groups")
public class CustomerGroupController {

    @Autowired
    private CustomerGroupRepository customerGroupRepository;

    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("groups", customerGroupRepository.findAll());
        return "allGroups";
    }

    @GetMapping(value = "/create")
    public String showCreateForm(Model model) {

        int numberOfNewEmployees = 1;
        CustomerGroupDto customerGroupDto = new CustomerGroupDto();

        for (int i = 1; i <= numberOfNewEmployees; i++) {
            customerGroupDto.addGroup(new CustomerGroup());
        }

        model.addAttribute("form", customerGroupDto);

        return "createGroupForm";
    }

    @PostMapping(value = "/save")
    public String saveBooks(@ModelAttribute CustomerGroupDto form, Model model) {
        customerGroupRepository.saveAll(form.getGroups());

        model.addAttribute("groups", customerGroupRepository.findAll());

        return "redirect:/groups/all";
    }

    @GetMapping(value = "/edit")
    public String showEditForm(Model model) {
        List<CustomerGroup> customerGroups = new ArrayList<>();
        customerGroupRepository.findAll()
                .iterator()
                .forEachRemaining(customerGroups::add);

        model.addAttribute("form", new CustomerGroupDto(customerGroups));

        return "editGroupsForm";
    }

    @GetMapping(value = "/delete")
    public String deleteGroup(@RequestParam(required = true) String id) {
        customerGroupRepository.deleteById(Long.parseLong(id));
        return "redirect:/groups/all";
    }


}
