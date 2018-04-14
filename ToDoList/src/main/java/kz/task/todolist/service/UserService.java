/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.service;

import java.util.HashMap;
import kz.task.todolist.model.User;
import kz.task.todolist.model.UserRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author User
 */
@Service
public class UserService extends BaseService {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public String saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        User result = new RestTemplate().postForObject(getServiceUrl("/create"),
                user, User.class);
        return "success";
    }

    public User login(String userName, String password) {
        HashMap<String, String> userInfo = new HashMap();
        userInfo.put("uname", userName);
        userInfo.put("pass", password);
        User user = new RestTemplate().postForObject(getServiceUrl("/login"), userInfo, User.class);
        return user;
    }

    public UserRoles getRoles() {
        return new RestTemplate().postForObject(getServiceUrl("/roles"), null, UserRoles.class);
    }

    public User findUserByUserName(String userName) {
        String url = getServiceUrl("/find/byUserName/" + userName);
        User user = new RestTemplate().postForObject(getServiceUrl("/find/byUserName/" + userName), null, User.class);
        return user;
    }

    @Override
    public String getServiceUrl(String path) {
        return super.getServiceUrl("/user-rest" + path + "/");
    }
}
