package com.project.springschedule.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequestDto {
    @NotBlank
    private String username;

    @NotBlank
    @Size(min = 8, max = 20, message = "패스워드 글자 수는 20자까지만 입력해주세요.")
    private String password;
}
