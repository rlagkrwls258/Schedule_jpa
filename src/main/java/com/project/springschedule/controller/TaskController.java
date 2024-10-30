package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.TaskRequestDto;
import com.project.springschedule.dto.response.TaskResponseDto;
import com.project.springschedule.service.TaskService;
import com.project.springschedule.util.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/write")
    public ResponseEntity<ApiResult<TaskResponseDto>> createSchedule(HttpServletRequest request, @RequestBody @Valid TaskRequestDto taskRequestDto) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(taskService.createTask(taskRequestDto, user)), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<ApiResult<List<TaskResponseDto>>> findAllSchedules() {
        return new ResponseEntity<>(ApiResult.success(taskService.findAllTask()), HttpStatus.OK);

    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResult<TaskResponseDto>> findTask(@PathVariable Long taskId) {
        return new ResponseEntity<>(ApiResult.success(taskService.findTaskById(taskId)), HttpStatus.OK);
    }

    @GetMapping("/page/{page}")
    public ResponseEntity<ApiResult<Page<TaskResponseDto>>> findAllSchedulesByPage(@PathVariable int page, @RequestParam(required = false, defaultValue = "10") int size) {
        return new ResponseEntity<>(ApiResult.success(taskService.findAllTaskPage(page - 1, size)), HttpStatus.OK);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResult<TaskResponseDto>> updateTask(HttpServletRequest request, @PathVariable Long taskId, @RequestBody @Valid TaskRequestDto taskRequestDto) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(taskService.updateTask(user, taskId, taskRequestDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResult<TaskResponseDto>> deleteTask(HttpServletRequest request, @PathVariable Long taskId) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(taskService.deleteTask(user, taskId)), HttpStatus.OK);
    }

}
