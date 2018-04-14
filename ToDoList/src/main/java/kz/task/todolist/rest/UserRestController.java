/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import kz.task.todolist.model.User;
import kz.task.todolist.model.UserRoles;
import kz.task.todolist.repository.UserRepository;
import kz.task.todolist.repository.UserRoleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/user-rest")
public class UserRestController {

    Logger logger = LogManager.getLogger(UserRestController.class);

    @Autowired
    UserRepository userRepo;

    @Autowired
    UserRoleRepository userRoleRepo;

    @PostMapping("/create")
    public User createUser(@Valid @RequestBody User user) {
        User result = userRepo.save(user);
        logger.debug("Создание пользователя : " + user.getUserName());
        return result;
    }

    @RequestMapping("/users")
    public List<User> getUsers() {
        return userRepo.findAll();
    }

    @RequestMapping("/find/byId/{userId}")
    public User getUser(@PathVariable(value = "userId") Long userId) {
        Optional<User> user = userRepo.findById(userId);

        return user.get();
    }

    @RequestMapping("/find/byUserName/{userName}")
    public User findByUserName(@PathVariable(value = "userName") String userName) {
        User user = userRepo.findByUserName(userName);
        return user;
    }

    @PostMapping("/login")
    public User login(@RequestBody HashMap<String, String> loginInfo) {
        String login = loginInfo.get("uname");
        String password = loginInfo.get("pass");
        User user = userRepo.userAuthorize(login, password);
        return user;
    }

    @PutMapping("/update/{userId}")
    public User updateUser(@PathVariable(value = "userId") Long userId, @RequestBody User user) {

        Optional<User> initUser = userRepo.findById(userId);
        if (initUser.get() != null) {
            initUser.get().setFirstName(user.getFirstName());
            logger.debug("Редактирование данных пользователя: " + user.getUserName());
            return userRepo.save(user);
        } else {
            return null;
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "userId") Long userId) {
        Optional<User> user = userRepo.findById(userId);
        logger.debug("Создание пользователя : " + user.get().getUserName());
        userRepo.delete(user.get());
        return ResponseEntity.ok().build();
    }

    @RequestMapping("/roles")
    public UserRoles getUserRoles() {
        UserRoles userRoles = new UserRoles();
        userRoles.setUserRoles(userRoleRepo.findAll());
        return userRoles;
    }
}
