/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.service;

import kz.task.todolist.model.Task;
import kz.task.todolist.model.TaskList;
import kz.task.todolist.model.TaskStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author User
 */
@Service
public class TaskService extends BaseService {

    public TaskStatus findStatusByCode(String statusCode) {
        String url = getServiceUrl("/find/status/" + statusCode);
        System.out.println("url****: " + url);
        TaskStatus taskStatus = new RestTemplate().postForObject(getServiceUrl("/find/status/" + statusCode), null, TaskStatus.class);
        return taskStatus;
    }

    public Task saveTask(Task task) {
        Task result = new RestTemplate().postForObject(getServiceUrl("/save"), task, Task.class);
        return result;
    }

    public TaskList findByUser(Long userId) {
        TaskList result = new RestTemplate().postForObject(getServiceUrl("/find/byUser"), userId, TaskList.class);
        return result;
    }
    public Task findTaskById(Long taskId) {
        Task result = new RestTemplate().postForObject(getServiceUrl("/find/byId"), taskId, Task.class);
        
        return result;
    }

    @Override
    public String getServiceUrl(String path) {
        return super.getServiceUrl("/task-rest" + path + "/");
    }

}
