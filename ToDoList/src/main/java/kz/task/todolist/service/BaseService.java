/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author User
 */
public class BaseService {
    
    @Autowired
    private String serviceUrl;
    @Autowired
    private RestTemplate restTemplate;

    public String getServiceUrl(String path) {
        return serviceUrl + path;
    }

    public RestTemplate getRestTemplate() {
        return restTemplate;
    }
}
