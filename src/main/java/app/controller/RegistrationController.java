package app.controller;

import app.model.User;
import app.service.RoleService;
import app.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping( "/registration")
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUser(Model model) {
        model.addAttribute("user", new User());

        return "registration";
    }

    @PostMapping( "/register")
    public String register(@ModelAttribute User user, Model model) {
        if (!userService.saveUser(user)) {
            model.addAttribute("usernameError", "User is already exists");
            return "/registration";
        }

        return "redirect:/login";
    }

}
