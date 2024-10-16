package com.project.springschedule.repository;

import com.project.springschedule.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    //Get current user tasks
    List<Task> findAllByUserId(Long userId);

    List<Task> findAllByOrderByIdDesc();
    

}