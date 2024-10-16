package com.project.springschedule.domain;

import com.project.springschedule.dto.request.TaskRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false, length = 30)
    private String title;

    @Column(name = "description", nullable = false, length = 100)
    private String description;

    @Column(name = "weather", nullable = false, length = 50)
    private String weather;

    @Column(nullable = false)
    @CreatedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createAt;

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Task(TaskRequestDto taskRequestDto, User user){
        this.title = taskRequestDto.getTitle();
        this.description = taskRequestDto.getDescription();
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
        this.user = user;
    }
}
