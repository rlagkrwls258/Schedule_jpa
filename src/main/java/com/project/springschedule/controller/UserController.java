package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.UserLoginRequestDto;
import com.project.springschedule.dto.request.UserRequestDto;
import com.project.springschedule.service.UserService;
import com.project.springschedule.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
    public String signup(@RequestBody @Valid UserRequestDto userRequestDto, BindingResult bindingResult) {
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        if(fieldErrors.size() > 0) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                log.error(fieldError.getField() + " 필드 : " + fieldError.getDefaultMessage());
            }
            return "validation error";
        }


        userService.signup(userRequestDto);

        return "signup success";
    }

    @PostMapping("/login")
    public String login(@RequestBody UserLoginRequestDto userRequestDto, HttpServletResponse response) {
        String token = userService.login(userRequestDto);
        jwtUtil.addJwtToCookie(token, response);
        return "Login Success";
    }


}