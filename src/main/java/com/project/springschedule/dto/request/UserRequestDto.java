package com.project.springschedule.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    @NotBlank(message = "아이디를 입력하지 않았습니다.")
    private String username;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$", // test : password1!
            message = "비밀번호는 대소문자 포함 영문 + 숫자 + 특수문자를 최소 1글자씩 최소 8글자 이상이어야 합니다.")
    private String password;

    @Email
    @NotBlank(message = "이메일을 입력하지 않았습니다.")
    private String email;

    private boolean admin = false;

    private String adminToken = "";

}