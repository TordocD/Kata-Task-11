package app.service;

import app.dao.UserDao;
import app.model.Role;
import app.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserDao userDao;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    private final RoleService roleService;




    public UserServiceImpl(UserDao userDao, RoleService roleService) {
        this.userDao = userDao;
        this.roleService = roleService;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional(readOnly = true)
    public User getById(int id) {
        return userDao.getById(id);
    }

    @Override
    @Transactional
    public void deleteById(Integer id) {
        userDao.deleteById(id);
    }

    @Override
    @Transactional
    public void add(User user) {
        userDao.add(user);
    }

    @Transactional
    @Override
    public boolean saveUser(User user) {
        try {
            if (getByUsername(user.getUsername()) != null) {
                return false;
            }
        } catch (Exception e) {
            // empty
        }
        user.setRoles(Collections.singleton(roleService.getByName("ROLE_USER")));
        user.setPassword(encoder.encode(user.getPassword()));
        add(user);
        return true;
    }

    @Transactional
    @Override
    public boolean saveUserWithNewAuthority(User user, Set<Role> roles) {
        try {
            if (getByUsername(user.getUsername()) != null) {
                return false;
            }
        } catch (Exception e) {
            // empty
        }
        user.setRoles(roles);
        user.setPassword(encoder.encode(user.getPassword()));
        add(user);
        return true;
    }

    @Override
    @Transactional
    public void updateUserByAdmin(User newUser) {
        userDao.updateUser(newUser);
    }

    @Override
    @Transactional
    public void updateUser(User newUser) {
        newUser.setRoles(Collections.singleton(roleService.getByName("ROLE_USER")));
        userDao.updateUser(newUser);
    }

    @Override
    @Transactional(readOnly = true)
    public User getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }
        return user;
    }
}
