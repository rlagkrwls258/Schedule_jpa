package com.project.springschedule.service;


import com.project.springschedule.domain.Task;
import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.TaskRequestDto;
import com.project.springschedule.dto.response.TaskResponseDto;
import com.project.springschedule.exception.CustomApiException;
import com.project.springschedule.exception.ErrorCode;
import com.project.springschedule.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    public TaskResponseDto createTask(TaskRequestDto taskRequestDto, User user) {
        Task task = new Task(taskRequestDto, user);
        String url = "https://f-api.github.io/f-api/weather.json";
        List<Map<String, String>> weatherList = restTemplate.getForObject(url, List.class);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        for (Map<String, String> weatherData : weatherList) {
            if (today.equals(weatherData.get("date"))) {
                task.setWeather(weatherData.get("weather"));
            }
        }

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
                .orElseThrow(() -> new CustomApiException(ErrorCode.TASK_NOT_FOUND));

        // 현재 사용자가 해당 Task를 삭제할 권한이 없을 경우 예외 던짐
        task.validUser(user);

        task.setTitle(taskRequestDto.getTitle());
        task.setDescription(taskRequestDto.getDescription());
        taskRepository.save(task);

        return new TaskResponseDto(task);
    }

    public TaskResponseDto deleteTask(User user, Long taskId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() ->  new CustomApiException(ErrorCode.TASK_NOT_FOUND));

        // 현재 사용자가 해당 Task를 삭제할 권한이 없을 경우 예외 던짐
        task.validUser(user);

        taskRepository.delete(task);

        return new TaskResponseDto(task);
    }

    public String restApi(){
        String url = "https://f-api.github.io/f-api/weather.json";
        List<Map<String, String>> weatherList = restTemplate.getForObject(url, List.class);

        String today = LocalDate.now().format(DateTimeFormatter.ofPattern("MM-dd"));
        for (Map<String, String> weatherData : weatherList) {
            if (today.equals(weatherData.get("date"))) {
                return weatherData.get("weather"); // 오늘의 날씨 반환
            }
        }
        return "";
    }
}
