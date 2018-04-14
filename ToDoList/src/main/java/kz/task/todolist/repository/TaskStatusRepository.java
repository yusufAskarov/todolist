/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.repository;

import kz.task.todolist.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author User
 */
public interface TaskStatusRepository extends JpaRepository<TaskStatus, Integer>{
    
    TaskStatus findByStatusCode(String statusCode);
}
