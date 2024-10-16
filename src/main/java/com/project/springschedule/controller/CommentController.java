package com.project.springschedule.controller;

import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.CommentRequestDto;
import com.project.springschedule.dto.response.CommentResponseDto;
import com.project.springschedule.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
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
    public CommentResponseDto createComment(HttpServletRequest request, @PathVariable Long taskId,@RequestBody @Valid CommentRequestDto commentRequestDto) {
        User user = (User) request.getAttribute("user");
        return commentService.createComment(user, taskId, commentRequestDto);
    }

    @GetMapping("/{taskId}")
    public List<CommentResponseDto> findComments(@PathVariable Long taskId) {
        return commentService.findComments(taskId);
    }

    @PutMapping("/{commentId}")
    public CommentResponseDto updateComment(HttpServletRequest request, @PathVariable Long commentId,@RequestBody @Valid CommentRequestDto commentRequestDto) {
        User user = (User) request.getAttribute("user");
        return commentService.updateComment(user, commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    public CommentResponseDto deleteComment(HttpServletRequest request, @PathVariable Long commentId) {
        User user = (User) request.getAttribute("user");
        return commentService.deleteComment(user, commentId);
    }

}