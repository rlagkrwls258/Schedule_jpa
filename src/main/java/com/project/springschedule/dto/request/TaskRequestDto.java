package com.project.springschedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;


@Getter
@Setter
public class TaskRequestDto {

    @NotBlank(message = "내용을 작성해야 합니다")
    @Size(min = 1, max = 30, message = "일정의 제목은 최소 1자 최대 30자로 작성해야 합니다")
    private String title;

    @NotBlank(message = "내용을 작성해야 합니다")
    @Size(min = 1, max = 100, message = "일은 최소 1자 최대 100자로 작성해야 합니다")
    private String description;
}
