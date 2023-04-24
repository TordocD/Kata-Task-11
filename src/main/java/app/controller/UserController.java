package app.controller;

import app.model.User;
import app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController (UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model, Principal principal) {
        model.addAttribute("currentUser", userService.getByUsername(principal.getName()));

        return "user";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute User currentUser) {
        userService.updateUser(currentUser);

        return "redirect:/user";
    }
}
