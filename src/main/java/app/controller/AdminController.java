package app.controller;



import app.model.Role;
import app.model.User;
import app.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import app.service.UserService;
import java.util.HashSet;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController (UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String getAllUsers(ModelMap model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("allRoles", roleService.getAllRoles());
        model.addAttribute("user", new User());

        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute User user,
                          @RequestParam("chosenRoles") HashSet<String> chosenRoles) {
        HashSet<Role> roles = new HashSet<>();
        for(String chosenRole : chosenRoles) {
            Role role = roleService.getByName(chosenRole);
            roles.add(role);
        }
        userService.saveUserWithNewAuthority(user, roles);

        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String deleteUser(@ModelAttribute User user) {
        if (!user.getUsername().equals("admin")) {
            userService.deleteById(user.getId());
        }

        return "redirect:/admin";
    }

    @GetMapping( "/get_updated_user")
    public String updateUser(@ModelAttribute User user, ModelMap model) {
        model.addAttribute(user);
        model.addAttribute("allRoles", roleService.getAllRoles());

        return "update_user";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute User user,
                             @RequestParam("chosenRoles") HashSet<String> chosenRoles) {
        HashSet<Role> roles = new HashSet<>();
        for(String chosenRole : chosenRoles) {
            Role role = roleService.getByName(chosenRole);
            roles.add(role);
        }
        user.setRoles(roles);
        userService.updateUserByAdmin(user);

        return "redirect:/admin";
    }

    @PostMapping("/add_new_role")
    public String addNewRole(@RequestParam String newRoleName) {
        roleService.add(new Role(newRoleName));

        return "redirect:/admin";
    }

    @DeleteMapping("/delete_role")
    public String deleteRole(@RequestParam String deletedRoleName) {
        roleService.deleteRoleByName(deletedRoleName);

        return "redirect:/admin";
    }
}
