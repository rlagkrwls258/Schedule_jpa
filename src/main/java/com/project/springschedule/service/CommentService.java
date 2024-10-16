package com.project.springschedule.service;

import com.project.springschedule.domain.Comment;
import com.project.springschedule.domain.Task;
import com.project.springschedule.domain.User;
import com.project.springschedule.dto.request.CommentRequestDto;
import com.project.springschedule.dto.response.CommentResponseDto;
import com.project.springschedule.repository.CommentRepository;
import com.project.springschedule.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskRepository taskRepository;

    public CommentResponseDto createComment(User user, Long taskId, CommentRequestDto commentRequestDto) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

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

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this task.");
        }

        comment.setContent(commentRequestDto.getContent());
        commentRepository.save(comment);
        return new CommentResponseDto(comment);
    }

    public CommentResponseDto deleteComment(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));

        if (!comment.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("You do not have permission to delete this task.");
        }
        commentRepository.delete(comment);
        return new CommentResponseDto(comment);
    }
}