/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.List;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import kz.task.todolist.model.Task;
import kz.task.todolist.model.TaskList;
import kz.task.todolist.model.TaskStatus;
import kz.task.todolist.model.User;
import kz.task.todolist.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author User
 */
@Controller
@RequestMapping("/user")
@SessionAttributes({"updTask"})
public class UserController {
    
    @Autowired
    private TaskService taskService;
    
    @RequestMapping("/home")
    public ModelAndView home(HttpSession session) {
        ModelAndView modelView = new ModelAndView();
        User user = (User) session.getAttribute("user");
        if (user != null) {
            TaskList taskList = taskService.findByUser(user.getUserId());
            List<Task> tasks = taskList != null ? taskList.getTaskList() : null;
            modelView.addObject("tasks", tasks);
        }

        modelView.setViewName("/client/home");
        return modelView;
    }

    @PostMapping("/task-creation")
    public ModelAndView taskCreation(@Valid @ModelAttribute Task task, BindingResult bindingResult, HttpSession session) {
        String view;
        ModelAndView modelAndView = new ModelAndView();
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("task", task);
            view = "/client/create_task";
        } else {
            User user = (User) session.getAttribute("user");
            TaskStatus status = taskService.findStatusByCode("inprocess");
            task.setUser(user);
            task.setTaskStatus(status);
            taskService.saveTask(task);
            view = "redirect:home";
        }
        modelAndView.setViewName(view);
        return modelAndView;
    }

    @RequestMapping("/task-creation")
    public ModelAndView taskCreation() {
        ModelAndView modelView = new ModelAndView();
        Task task = new Task();
        modelView.addObject("task", task);
        modelView.setViewName("/client/create_task");
        return modelView;
    }

    @PostMapping("/update")
    public ModelAndView update( @Valid @ModelAttribute("updTask") Task task, BindingResult bindingResult) {
        ModelAndView modelView = new ModelAndView();
        String view;
        if (bindingResult.hasErrors()) {
//            for (ObjectError err : bindingResult.getAllErrors()) {
//                System.out.println("error: " + err.toString());
//            }
            modelView.addObject("updTask", task);
            view = "/client/update_task";
        } else {
            TaskStatus status = taskService.findStatusByCode("finished");
            task.setTaskStatus(status);
            taskService.saveTask(task);
            view = "redirect:home";
        }
        modelView.setViewName(view);
        
        return modelView;
    }

    @RequestMapping("/update")
    public ModelAndView update(@RequestParam(value = "taskId", required = true) String taskId) {
        long taskIdL = new Long(taskId);
        ModelAndView modelView = new ModelAndView();
        Task task = taskService.findTaskById(taskIdL);
        modelView.addObject("updTask", task);
        modelView.setViewName("/client/update_task");
        return modelView;
    }

    @RequestMapping(value = "/remove-task", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Task removeTask(
            @RequestParam(value = "taskId", required = true) String taskId) throws JsonProcessingException {
        long taskIdL = new Long(taskId);
        Task task= taskService.findTaskById(taskIdL);
        TaskStatus status = taskService.findStatusByCode("deleted");
        task.setTaskStatus(status);
        task.setIsArch(1);
        taskService.saveTask(task);
        return task;
    }

}
