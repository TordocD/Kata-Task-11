package app.service;

import app.model.Role;
import app.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();
    void add(User user);
    User getById(int id);
    void deleteById(Integer id);
    void updateUser(User newUser);
    void updateUserByAdmin(User newUser);
    User getByUsername(String username);
    boolean saveUser(User user);

    boolean saveUserWithNewAuthority(User user, Set<Role> roles);
}
