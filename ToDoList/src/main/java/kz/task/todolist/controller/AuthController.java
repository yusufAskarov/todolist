/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.controller;

import java.util.List;
import javax.validation.Valid;
import kz.task.todolist.model.User;
import kz.task.todolist.model.UserRole;
import kz.task.todolist.model.UserRoles;
import kz.task.todolist.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
public class AuthController {

    @Autowired
    private UserService userService;
    
    @Value("${user.validation.userName}")
    private String validUserName;

    @RequestMapping("/login")
    public String toLogin(Model model) {
        return "login";
    }

    @RequestMapping("/registration")
    public ModelAndView registration() {
        ModelAndView modelAndView = new ModelAndView();
        UserRoles userRoleList = userService.getRoles();
        List<UserRole> userRoles = userRoleList != null ? userRoleList.getUserRoles() : null;
        modelAndView.addObject("roles", userRoles);
        User user = new User();
        modelAndView.addObject("user", user);

        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @RequestMapping("/access-denied")
    public String accessDenied() {
        return "access_denied";
    }

    @PostMapping("/registration")
    public ModelAndView registration(@Valid @ModelAttribute User user, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        User existUser = userService.findUserByUserName(user.getUserName());
        if (existUser != null) {
            bindingResult
                    .rejectValue("userName", "error.user",
                            validUserName);
        }
        
        String view = "redirect:login";
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("roles", user.getUserRole());
            modelAndView.addObject("user", user);
            view = "registration";
        } else {
            userService.saveUser(user);
        }

        modelAndView.setViewName(view);
        return modelAndView;
    }
    @RequestMapping("/welcome")
    public ModelAndView welcome(){
        ModelAndView modelView = new ModelAndView();
        modelView.setViewName("welcome");
        return modelView;
    }
}
