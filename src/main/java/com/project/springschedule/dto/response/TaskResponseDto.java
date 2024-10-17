package com.project.springschedule.dto.response;

import com.project.springschedule.domain.Task;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class TaskResponseDto {
    private final Long id;
    private final String title;
    private final String content;
    private final String weather;
    private List<CommentResponseDto> comments;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public TaskResponseDto(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.content = task.getDescription();
        this.weather = task.getWeather();

        this.comments = task.getComments().stream().map(CommentResponseDto::new).toList();
        this.createdAt = task.getCreateAt();
        this.modifiedAt = task.getUpdateAt();
    }
}
