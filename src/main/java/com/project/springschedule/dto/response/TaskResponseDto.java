package com.project.springschedule.dto.response;

import com.project.springschedule.domain.Task;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class TaskResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String weather;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.content = task.getDescription();
        this.weather = task.getWeather();
        this.createdAt = task.getCreateAt();
        this.modifiedAt = task.getUpdateAt();
    }
}
