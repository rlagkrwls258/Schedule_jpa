package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.TaskRequestDto;
import com.project.springschedule.dto.response.TaskResponseDto;
import com.project.springschedule.service.TaskService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {
    
    private final TaskService taskService;

    @PostMapping("/write")
    public TaskResponseDto createSchedule(HttpServletRequest request,@RequestBody @Valid TaskRequestDto taskRequestDto) {
        User user = (User) request.getAttribute("user");
        log.info("ddd");
        return taskService.createTask(taskRequestDto,user);
    }

    @GetMapping("/")
    public List<TaskResponseDto> findAllSchedules() {
        return taskService.findAllTask();
    }

    @GetMapping("/{taskId}")
    public TaskResponseDto findTask(@PathVariable Long taskId) {
        return taskService.findTaskById(taskId);
    }

    @GetMapping("/page/{page}")
    public Page<TaskResponseDto> findAllSchedulesByPage(@PathVariable int page, @RequestParam(required = false, defaultValue = "10") int size) {
        return taskService.findAllTaskPage(page-1, size);
    }

    @PutMapping("/{taskId}")
    public TaskResponseDto updateTask(HttpServletRequest request, @PathVariable Long taskId,@RequestBody @Valid TaskRequestDto taskRequestDto) {
        User user = (User) request.getAttribute("user");
        return taskService.updateTask(user, taskId, taskRequestDto);
    }

    @DeleteMapping("/{taskId}")
    public TaskResponseDto deleteTask(HttpServletRequest request, @PathVariable Long taskId) {
        User user = (User) request.getAttribute("user");
        return taskService.deleteTask(user, taskId);
    }

}
