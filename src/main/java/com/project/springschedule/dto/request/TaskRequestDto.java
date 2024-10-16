package com.project.springschedule.dto.request;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TaskRequestDto {
    @Size(min = 1, max = 30)
    private String title;
    @Size(min = 1, max = 100)
    private String description;
}
