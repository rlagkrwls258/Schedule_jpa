package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.UserLoginRequestDto;
import com.project.springschedule.dto.request.UserRequestDto;
import com.project.springschedule.dto.response.TaskResponseDto;
import com.project.springschedule.service.UserService;
import com.project.springschedule.util.ApiResult;
import com.project.springschedule.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    private final JwtUtil jwtUtil;

    public UserController(UserService userService, JwtUtil jwtUtil) {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping("/signup")
    public ResponseEntity<ApiResult<String>> signup(@RequestBody @Valid UserRequestDto userRequestDto) {
        userService.signup(userRequestDto);

        return new ResponseEntity<>(ApiResult.success("회원가입 성공"), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResult<String>> login(@RequestBody @Valid UserLoginRequestDto userRequestDto, HttpServletResponse response) {
        String token = userService.login(userRequestDto);
        jwtUtil.addJwtToCookie(token, response);
        return new ResponseEntity<>(ApiResult.success("로그인 성공"), HttpStatus.OK);
    }


}