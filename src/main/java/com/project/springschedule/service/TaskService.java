package com.project.springschedule.service;


import com.project.springschedule.domain.Task;
import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.TaskRequestDto;
import com.project.springschedule.dto.response.TaskResponseDto;
import com.project.springschedule.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, User user) {
        Task task = new Task(taskRequestDto, user);
        taskRepository.save(task);

        return new TaskResponseDto(task);
    }

    public List<TaskResponseDto> findAllTask() {
        return taskRepository.findAllByOrderByIdDesc().stream().map(TaskResponseDto::new).toList();
    }

    public TaskResponseDto findTaskById(Long id) {
        return taskRepository.findById(id).map(TaskResponseDto::new).orElse(null);
    }

    public Page<TaskResponseDto> findAllTaskPage(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "updateAt");
        Pageable pageable = PageRequest.of(page, size, sort);
        return taskRepository.findAll(pageable).map(TaskResponseDto::new);
    }

    public TaskResponseDto updateTask(User user, Long taskId, TaskRequestDto taskRequestDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " not found"));

        // 현재 사용자가 해당 Task를 삭제할 권한이 없을 경우 예외 던짐
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this task.");
        }

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        taskRepository.save(task);

        return new TaskResponseDto(task);
    }

    public TaskResponseDto deleteTask(User user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new NoSuchElementException("Task with ID " + taskId + " not found"));

        // 현재 사용자가 해당 Task를 삭제할 권한이 없을 경우 예외 던짐
        if (!task.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this task.");
        }


        taskRepository.delete(task);

        return new TaskResponseDto(task);
    }
}
