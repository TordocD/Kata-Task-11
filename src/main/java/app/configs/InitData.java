package app.configs;

import app.model.Role;
import app.model.User;
import app.service.UserService;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Set;

@Component
public class InitData {

    private final UserService userService;

    public InitData(UserService userService) {
        this.userService = userService;
    }

    @Transactional
    @PostConstruct
    public void init() {
        Set<Role> roles = Set.of(new Role("ROLE_ADMIN"), new Role("ROLE_USER"));

        if (userService.getByUsername("admin") == null) {
            userService.saveUserWithNewAuthority(new User("admin", "admin"), roles);
        }
    }
}
