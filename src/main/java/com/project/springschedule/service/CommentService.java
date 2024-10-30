package com.project.springschedule.service;

import com.project.springschedule.domain.Comment;
import com.project.springschedule.domain.Task;
import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.CommentRequestDto;
import com.project.springschedule.dto.response.CommentResponseDto;
import com.project.springschedule.exception.CustomApiException;
import com.project.springschedule.exception.ErrorCode;
import com.project.springschedule.repository.CommentRepository;
import com.project.springschedule.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentResponseDto createComment(User user, Long taskId, CommentRequestDto commentRequestDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.TASK_NOT_FOUND));

        Comment comment = new Comment(commentRequestDto.getContent(), user, task);
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    public List<CommentResponseDto> findComments(Long taskId) {
        return commentRepository.findAllByTaskId(taskId).stream().map(CommentResponseDto::new).toList();
    }

    public CommentResponseDto updateComment(User user, Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        //해당 댓글의 작성자 인지
        comment.validUser(user);

        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CustomApiException(ErrorCode.COMMENT_NOT_FOUND));

        comment.validUser(user);

        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }
}