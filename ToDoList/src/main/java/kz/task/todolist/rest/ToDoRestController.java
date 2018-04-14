/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.rest;

import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import kz.task.todolist.model.Task;
import kz.task.todolist.model.TaskList;
import kz.task.todolist.model.TaskStatus;
import kz.task.todolist.repository.TaskRepository;
import kz.task.todolist.repository.TaskStatusRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping("/task-rest")
public class ToDoRestController {

    Logger logger = LogManager.getLogger(ToDoRestController.class);
    @Autowired
    TaskRepository taskRepo;

    @Autowired
    TaskStatusRepository taskStatusRepo;

    @RequestMapping("/find/status/{statusCode}")
    public TaskStatus findStatusByCode(@PathVariable(value = "statusCode") String statusCode) {
        TaskStatus taskStatus = taskStatusRepo.findByStatusCode(statusCode);
        return taskStatus;
    }

    @RequestMapping("/save")
    public Task saveTask(@Valid @RequestBody Task task) {
        Task result = taskRepo.save(task);
        logger.debug("Сохранена задача " + task.getTaskName() + " пользователем " + task.getUser().getUserName());
        return result;
    }

    @RequestMapping("/find/byUser")
    public TaskList findByUser(@RequestBody Long userId) {
        TaskList result = new TaskList();
        List<Task> taskList = taskRepo.findByUser(userId);
        result.setTaskList(taskList);
        return result;
    }
    @RequestMapping("/find/byId")
    public Task findTaskById(@RequestBody Long taskId){
        Optional<Task> task =  taskRepo.findById(taskId);
        return task.get();
    }
    
}
