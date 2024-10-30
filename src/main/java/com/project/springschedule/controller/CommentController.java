package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.CommentRequestDto;
import com.project.springschedule.dto.response.CommentResponseDto;
import com.project.springschedule.service.CommentService;
import com.project.springschedule.util.ApiResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "commentController")
@RestController
@RequestMapping("/api/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/{taskId}")
    public ResponseEntity<ApiResult<CommentResponseDto>> createComment(HttpServletRequest request, @PathVariable Long taskId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(commentService.createComment(user, taskId, commentRequestDto)), HttpStatus.CREATED);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResult<List<CommentResponseDto>>> findComments(@PathVariable Long taskId) {
        return new ResponseEntity<>(ApiResult.success(commentService.findComments(taskId)), HttpStatus.OK);
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResult<CommentResponseDto>> updateComment(HttpServletRequest request, @PathVariable Long commentId, @RequestBody @Valid CommentRequestDto commentRequestDto) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(commentService.updateComment(user, commentId, commentRequestDto)), HttpStatus.OK);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResult<CommentResponseDto>> deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        User user = (User) request.getAttribute("user");
        return new ResponseEntity<>(ApiResult.success(commentService.deleteComment(user, commentId)), HttpStatus.OK);
    }

}