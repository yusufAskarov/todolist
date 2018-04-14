/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kz.task.todolist.repository;

import java.util.List;
import kz.task.todolist.model.Task;
import kz.task.todolist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author User
 */
public interface TaskRepository  extends JpaRepository<Task, Long>{
    
    @Query("SELECT t FROM Task t left join t.user u  WHERE u.userId = ?1 AND t.isArch = 0")
    public List<Task> findByUser(Long userId);
}
