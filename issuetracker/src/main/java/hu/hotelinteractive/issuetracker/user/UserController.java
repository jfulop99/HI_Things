package hu.hotelinteractive.issuetracker.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/oldusers")
    @ResponseBody
    public String listEmployees() {
        List<User> users = userService.findAll();

        return users.stream().map(User::getUsername).collect(Collectors.joining(", "));
    }



    @GetMapping("/all")
    public String showAll(Model model) {
        model.addAttribute("users", userService.findAll());
        return "allUsers";
    }



}
